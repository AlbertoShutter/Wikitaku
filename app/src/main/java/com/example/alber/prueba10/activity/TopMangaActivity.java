package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.alber.prueba10.R;
import com.example.alber.prueba10.app.DevuelveJSON;
import com.example.alber.prueba10.clases.Manga;
import com.example.alber.prueba10.clases.TopMangaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TopMangaActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private String url_consulta;

    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;

    ArrayList<Manga> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_manga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nosferatu --> 192.168.1.87
        url_consulta = "http://192.168.1.41:8080/prueba/consulusu.php?ins_sql=SELECT%20*%20FROM%20manga%20ORDER%20BY%20Nota%20DESC";

        devuelveJSON = new DevuelveJSON();

        probarConexion();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.fishPriceList);
        //recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new TopMangaAdapter(items, this);
        recycler.setAdapter(adapter);
    }

    public void card(View view) {
        Intent intent = new Intent(TopMangaActivity.this, MangaItem.class);
        startActivity(intent);
    }

    public void onClick(View v){
        card(v);
    }

    class ObtenerDatos extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(TopMangaActivity.this);
            pDialog.setMessage("Cargando datos...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... strings) {
            try {
                HashMap<String, String> parametrosPost = new HashMap<>();
                jSONArray = devuelveJSON.sendRequest(url_consulta, parametrosPost);
                System.out.println(jSONArray);

                if (jSONArray != null) {
                    return jSONArray;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONArray json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        Manga manga = new Manga();

                        manga.setNombre(jsonObject.getString("Nombre"));
                        manga.setCapitulos(jsonObject.getString("Capitulos"));
                        manga.setVolumenes(jsonObject.getString("Volumenes"));
                        manga.setTipo(jsonObject.getString("Tipo"));
                        manga.setEstado(jsonObject.getString("Estado"));
                        manga.setNota(jsonObject.getDouble("Nota"));
                        manga.setImagen(jsonObject.getString("Imagen"));
                        manga.setFechaComienzo(jsonObject.getString("FechaComienzo"));
                        manga.setFechaFin(jsonObject.getString("FechaFin"));
                        manga.setGenero(jsonObject.getString("Genero"));
                        manga.setAutor(jsonObject.getString("Autor"));
                        manga.setSerializacion(jsonObject.getString("Serialización"));
                        manga.setSinopsis(jsonObject.getString("Sinopsis"));
                        manga.setNombreOriginal(jsonObject.getString("NombreOriginal"));

                        items.add(new Manga(jsonObject.getString("Nombre"), jsonObject.getString("Capitulos"), jsonObject.getString("Volumenes"),
                                jsonObject.getString("Tipo"), jsonObject.getString("Estado"), jsonObject.getDouble("Nota"),
                                jsonObject.getString("Imagen"), jsonObject.getString("FechaComienzo"), jsonObject.getString("FechaFin"),
                                jsonObject.getString("Genero"), jsonObject.getString("Autor"), jsonObject.getString("Serialización"),
                                jsonObject.getString("Sinopsis"), jsonObject.getString("NombreOriginal")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recycler.setHasFixedSize(true);
                    recycler.setLayoutManager(lManager);
                    recycler.setAdapter(adapter);
                }
            } else {
                Toast.makeText(TopMangaActivity.this, "JSON Array nulo", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void probarConexion() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new ObtenerDatos().execute();
        }
    }
}
