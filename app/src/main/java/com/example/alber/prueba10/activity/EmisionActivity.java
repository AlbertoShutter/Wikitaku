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
import com.example.alber.prueba10.clases.Anime;
import com.example.alber.prueba10.clases.AnimeEmisionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EmisionActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private String url_consulta;

    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;

    ArrayList<Anime> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emision);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url_consulta = "http://192.168.1.41:8080/prueba/consulusu.php?ins_sql=SELECT%20*%20FROM%20series%20WHERE%20Estado%20=%20%27En%20emision%27";

        devuelveJSON = new DevuelveJSON();

        probarConexion();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recycler_airing);
        //recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new AnimeEmisionAdapter(items, this);
        recycler.setAdapter(adapter);
    }

    public void card(View view) {
        Intent intent = new Intent(EmisionActivity.this, AnimeItem.class);
        startActivity(intent);
    }

    public void onClick(View v){
        card(v);
    }

    class ObtenerDatos extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(EmisionActivity.this);
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
                        Anime anime = new Anime();
                        anime.setImagen(jsonObject.getString("Imagen"));
                        anime.setNombre(jsonObject.getString("Nombre"));
                        anime.setEpisodios(jsonObject.getString("Episodios"));
                        anime.setTipo(jsonObject.getString("Tipo"));
                        anime.setEstado(jsonObject.getString("Estado"));
                        anime.setNota(jsonObject.getInt("Nota"));
                        anime.setFecha(jsonObject.getString("FechaComienzo"));
                        items.add(new Anime(jsonObject.getString("Imagen"), jsonObject.getInt("Nota"), jsonObject.getString("Nombre"), jsonObject.getString("Tipo"), jsonObject.getString("Estado"), jsonObject.getString("Episodios"), jsonObject.getString("FechaComienzo")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recycler.setHasFixedSize(true);
                    recycler.setLayoutManager(lManager);
                    recycler.setAdapter(adapter);
                }
            } else {
                Toast.makeText(EmisionActivity.this, "JSON Array nulo", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void probarConexion() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new EmisionActivity.ObtenerDatos().execute();
        }
    }
}
