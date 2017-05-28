package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alber.prueba10.R;
import com.example.alber.prueba10.clases.HttpParse;
import com.example.alber.prueba10.helper.SQLiteHandler;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AnimeItem extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyCi3RRs6z5ZlGkkCLOxDaaI_Ful0tD8WIg";
    private static final String VIDEO_ID = "50jqqsOxIoE";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment youTubePlayerFragment;

    ImageView imagen;
    Integer id;
    TextView nombre, visitas, episodios, nota;
    String titulo;
    Button añadir;

    RequestQueue requestQueue;
    HttpParse httpParse = new HttpParse();

    String url_consulta = "http://192.168.1.41:8080/prueba/insertuser.php";
    String deleteRecord = "http://192.168.1.41:8080/prueba/delete.php";

    Button deletebtn;

    String finalResult ;

    HashMap<String,String> hashMap = new HashMap<>();

    ProgressDialog progressDialog2;

    private SQLiteHandler db;

    String text, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_item);

        añadir = (Button)findViewById(R.id.fav);
        deletebtn = (Button)findViewById(R.id.eliminar);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        final String name = user.get("name");

        nombre = (TextView)findViewById(R.id.nombre);
        imagen = (ImageView)findViewById(R.id.imagen);
        episodios = (TextView)findViewById(R.id.capitulos);

        nombre.setText(getIntent().getStringExtra("nombre"));
        Picasso.with(this)
                .load(getIntent().getStringExtra("imagen"))
                .into(imagen);
        episodios.setText(getIntent().getStringExtra("episodios"));

        titulo = getIntent().getStringExtra("nombre");

        text = "https://myanimelist.net/animelist/AlbertShutter";
        subject = "Echa un vistazo a esto (enviado desde Wikitaku)";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        youTubePlayerFragment.initialize(DEVELOPER_KEY, this);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, url_consulta, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("Nombre", titulo);
                        parameters.put("Usuario", name);

                        return parameters;
                    }
                };
            requestQueue.add(request);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            serieDelete(titulo, name);
            }
        });
    }

    public void serieDelete(final String serie, final String usuario) {

        class serieDeleteClass extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(AnimeItem.this, "Deleting Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(AnimeItem.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                db = new SQLiteHandler(getApplicationContext());
                HashMap<String, String> user = db.getUserDetails();
                final String name = user.get("name");
                hashMap.put("Nombre", titulo);
                hashMap.put("Usuario", name);

                finalResult = httpParse.postRequest(hashMap, deleteRecord);
                return finalResult;
            }
        }

        serieDeleteClass seriedelete = new serieDeleteClass();
        seriedelete.execute(serie,usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_buscar) {
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(Intent.createChooser(intent,  "Compartir en" ));

            Toast.makeText(this, "Compartir", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("Hubo un error al iniciar YoutubePlayer", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
    }
}