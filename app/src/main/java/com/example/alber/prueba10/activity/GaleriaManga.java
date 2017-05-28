package com.example.alber.prueba10.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.alber.prueba10.R;
import com.example.alber.prueba10.clases.GaleriaAdaptador;
import com.example.alber.prueba10.clases.MangaImagen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GaleriaManga extends AppCompatActivity {

    String url_consulta;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<MangaImagen> imagenes;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private GaleriaAdaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_manga);

        usarToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        imagenes = new ArrayList<>();
        getImagenesFromDB(0);

        gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);

        adapter = new GaleriaAdaptador(this, imagenes);
        recyclerView.setAdapter(adapter);

        String nombre = getIntent().getStringExtra("nombre");
        url_consulta = "http://192.168.1.41:8080/prueba/consulusu.php?ins_sql=SELECT%20*%20FROM%20galeriamanga%20WHERE%20Nombre%20=%20'"+nombre+"'";
    }

    private void usarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void getImagenesFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url_consulta)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MangaImagen imagen = new MangaImagen(object.getInt("Id_relacion"), object.getString("Nombre"),
                                object.getString("Imagen"));

                        GaleriaManga.this.imagenes.add(imagen);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }
}