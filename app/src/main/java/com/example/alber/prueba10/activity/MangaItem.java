package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MangaItem extends AppCompatActivity {

    ImageView imagen;
    Integer id;
    TextView nombre, capitulos, nota, volumenes;
    TextView nombreoriginal, tipo, chapters, volumen, state, puntuacion, publicado, autores, serializacion, genero, sinopsis;
    String titulo;
    Button añadir, galeria;
    RatingBar rb;
    Button link;

    double cal = 0;

    RequestQueue requestQueue;
    HttpParse httpParse = new HttpParse();

    String url_consulta = "http://192.168.1.41:8080/prueba/insertmanga.php";
    String deleteRecord = "http://192.168.1.41:8080/prueba/deletemanga.php";

    Button deletebtn;

    String finalResult ;

    HashMap<String,String> hashMap = new HashMap<>();

    ProgressDialog progressDialog2;

    private SQLiteHandler db;

    String text, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_item);

        añadir = (Button)findViewById(R.id.fav);
        deletebtn = (Button)findViewById(R.id.eliminar);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();

        final String name = user.get("name");
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MMM-yyyy");

        nombre = (TextView)findViewById(R.id.nombre);
        imagen = (ImageView)findViewById(R.id.imagen);
        rb = (RatingBar)findViewById(R.id.ratingBar);
        capitulos = (TextView)findViewById(R.id.capitulos);
        volumenes = (TextView)findViewById(R.id.volumenes);
        galeria = (Button)findViewById(R.id.galeria);
        nombreoriginal = (TextView)findViewById(R.id.tvNombreOriginal);
        tipo = (TextView)findViewById(R.id.tvTipo);
        chapters = (TextView)findViewById(R.id.tvCapitulos);
        volumen = (TextView)findViewById(R.id.tvVolumen);
        state = (TextView)findViewById(R.id.tvEstado);
        puntuacion = (TextView)findViewById(R.id.tvPuntuacion);
        publicado = (TextView)findViewById(R.id.tvPublicado);
        autores = (TextView)findViewById(R.id.tvAutores);
        serializacion = (TextView)findViewById(R.id.tvSerializacion);
        genero = (TextView)findViewById(R.id.tvGenero);
        sinopsis = (TextView)findViewById(R.id.sinopsis);
        link = (Button)findViewById(R.id.link);

        //poner los datos obtenidos del activity anterior en los campos de texto para que se visualicen
        nombre.setText(getIntent().getStringExtra("nombre"));
        nombreoriginal.setText(getIntent().getStringExtra("nombreoriginal"));
        Picasso.with(this)
                .load(getIntent().getStringExtra("imagen"))
                .into(imagen);
        capitulos.setText(getIntent().getStringExtra("capitulos"));
        volumenes.setText(getIntent().getStringExtra("volumenes"));
        rb.setStepSize((float) 0.1);
        rb.setRating((float)getIntent().getDoubleExtra("nota", cal)/2);
        tipo.setText(getIntent().getStringExtra("tipo"));
        chapters.setText(getIntent().getStringExtra("capitulos"));
        volumen.setText(getIntent().getStringExtra("volumenes"));
        state.setText(getIntent().getStringExtra("estado"));
        puntuacion.setText(Double.toString(getIntent().getDoubleExtra("nota", cal)));
        try {
            Date comienzo = parseador.parse(getIntent().getStringExtra("fechacomienzo"));
            Date fin = parseador.parse(getIntent().getStringExtra("fechafin"));
            publicado.setText(formateador.format(comienzo) + " — " + formateador.format(fin));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        autores.setText(getIntent().getStringExtra("autor"));
        serializacion.setText(getIntent().getStringExtra("serializacion"));
        genero.setText(getIntent().getStringExtra("genero"));
        sinopsis.setText(getIntent().getStringExtra("sinopsis"));

        titulo = getIntent().getStringExtra("nombre");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://myanimelist.net/animelist/AlbertShutter");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

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

                        Map<String,String> parameters = new HashMap <String, String>();
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
                mangaDelete(titulo, name);
            }
        });

        text = "https://myanimelist.net/animelist/AlbertShutter";
        subject = "Echa un vistazo a esto (enviado desde Wikitaku)";
    }

    public void mangaDelete(final String serie, final String usuario) {

        class serieDeleteClass extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(MangaItem.this, "Deleting Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(MangaItem.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

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

    public void abrir (View v) {
        Intent i = new Intent(this, GaleriaManga.class);
        i.putExtra("nombre", nombre.getText());
        startActivity(i);
    }
}
