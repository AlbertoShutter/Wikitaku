package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alber.prueba10.R;
import com.example.alber.prueba10.clases.Anime;
import com.example.alber.prueba10.clases.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Buscador extends AppCompatActivity {

    // Variables para conexión con MySql
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private SearchAdapter mAdapter;
    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Añadir item a la action bar
        getMenuInflater().inflate(R.menu.menu_buscador, menu);

        // Obtener el objeto a buscar desde el campo de texto de la action bar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) Buscador.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Buscador.this.getComponentName()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    // Cada vez que se busque una cadena escrita se ejecutará esta función
    @Override
    protected void onNewIntent(Intent intent) {
        // Obtener la cadena escrita y la pasará a la función AsyncFetch que buscará esa cadena en MySql
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
        }
    }

    // Clase AsyncFetch que se le pasará el php con la instrucción para buscar en la tabla correspondiente
    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Buscador.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery){
            this.searchQuery=searchQuery;
            Toast.makeText(Buscador.this, searchQuery, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // Introducir la URL donde se encuentra el archivo php
            try {
                url = new URL("http://192.168.1.41:8080/prueba/consulusu2.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                // Clase para recibir y enviar datos a través de php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // Permitir que se envien y reciban datos
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Añadir los parametros a nuestra url
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("buscar", searchQuery);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Comprobar que halla conexión
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Leer los datos recibidos desde php
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pasar los datos al metodo PostExecute
                    return (result.toString());

                } else {
                    return("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            List<Anime> data=new ArrayList<>();

            pdLoading.dismiss();
            if(result.equals("no rows")) {
                Toast.makeText(Buscador.this, "No Results found for entered query", Toast.LENGTH_LONG).show();
            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    // Extraer los datos del json e introducirlos en un JSONArray
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        Anime anime = new Anime();
                        anime.setImagen(json_data.getString("Imagen"));
                        anime.setNombre(json_data.getString("Nombre"));
                        anime.setEpisodios(json_data.getString("Episodios"));
                        anime.setTipo(json_data.getString("Tipo"));
                        anime.setEstado(json_data.getString("Estado"));
                        anime.setNota(json_data.getDouble("Nota"));
                        anime.setFechaComienzo(json_data.getString("FechaComienzo"));
                        anime.setNombreOriginal(json_data.getString("NombreOriginal"));
                        anime.setFechaFin(json_data.getString("FechaFin"));
                        anime.setPopularidad(json_data.getString("Transmitido"));
                        anime.setDuracion(json_data.getString("Duracion"));
                        anime.setPegi(json_data.getString("Pegi"));
                        anime.setProductores(json_data.getString("Productores"));
                        anime.setEstudio(json_data.getString("Estudio"));
                        anime.setGenero(json_data.getString("Genero"));
                        anime.setSinopsis(json_data.getString("Sinopsis"));
                        anime.setEnlaceTrailer(json_data.getString("EnlaceTrailer"));
                        anime.setTemporada(json_data.getString("Temporada"));
                        anime.setFuente(json_data.getString("Fuente"));
                        anime.setLink(json_data.getString("Link"));
                        data.add(anime);
                    }

                    // Visualizar los datos en el recyclerView
                    mRVFish = (RecyclerView) findViewById(R.id.fishPriceList);
                    mAdapter = new SearchAdapter(Buscador.this, (ArrayList<Anime>) data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(Buscador.this));

                } catch (JSONException e) {
                    // Si ocurre algun error estos mensajes harán más fácil entender porque ha ocurrido
                    Toast.makeText(Buscador.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Buscador.this, result.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
