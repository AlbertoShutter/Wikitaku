package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AnimeItems extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {

    public static final String DEVELOPER_KEY = "AIzaSyCi3RRs6z5ZlGkkCLOxDaaI_Ful0tD8WIg";
    private static final String VIDEO_ID = "50jqqsOxIoE";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment youTubePlayerFragment;

    ImageView imagen;
    Integer id;
    TextView nombre, visitas, episodios, nota, idtx;
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

    ImageView ivBackButton;
    TextView tvTitleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_item);

        añadir = (Button)findViewById(R.id.fav);
        deletebtn = (Button)findViewById(R.id.eliminar);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        final String name = user.get("name");

        //idtx = (TextView)findViewById(R.id.idtx);
        nombre = (TextView)findViewById(R.id.nombre);
        imagen = (ImageView)findViewById(R.id.imagen);
        episodios = (TextView)findViewById(R.id.capitulos);

        //idtx.setText(getIntent().getStringExtra("id_relacion"));
        nombre.setText(getIntent().getStringExtra("nombre"));
        Picasso.with(this)
                .load(getIntent().getStringExtra("imagen"))
                .into(imagen);
        episodios.setText(getIntent().getStringExtra("episodios"));

        titulo = getIntent().getStringExtra("nombre");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //ivBackButton = (ImageView) findViewById(R.id.ivBackButton);
        //tvTitleHeader = (TextView) findViewById(R.id.tvTitleHeader);
        /// tvDoneHeader = (TextView) findViewById(R.id.tvRightSideHeader);
        //  tvDoneHeader.setText("Save");
        //tvTitleHeader.setText("Ficha");

        /*ivBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                finish();

            }
        });*/

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

                        progressDialog2.dismiss();

                        Toast.makeText(AnimeItems.this, "Añadido a favoritos", Toast.LENGTH_LONG).show();

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

                progressDialog2 = ProgressDialog.show(AnimeItems.this, "Deleting Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(AnimeItems.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

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
