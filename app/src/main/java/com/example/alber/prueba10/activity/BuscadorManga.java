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
import com.example.alber.prueba10.clases.Manga;
import com.example.alber.prueba10.clases.SearchMangaAdapter;

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

public class BuscadorManga extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private SearchMangaAdapter mAdapter;

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador_manga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // adds item to action bar
        getMenuInflater().inflate(R.menu.menu_buscador, menu);

        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) BuscadorManga.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(BuscadorManga.this.getComponentName()));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query and create object of class AsyncFetch
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new AsyncFetch(query).execute();
        }
    }

    // Create class AsyncFetch
    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(BuscadorManga.this);
        HttpURLConnection conn;
        URL url = null;
        String searchQuery;

        public AsyncFetch(String searchQuery){
            this.searchQuery=searchQuery;
            Toast.makeText(BuscadorManga.this, searchQuery, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {


            // Enter URL address where your php file resides
            try {
                url = new URL("http://192.168.1.41:8080/prueba/consulusu3.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // add parameter to our above url
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

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
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

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<Manga> data=new ArrayList<>();

            pdLoading.dismiss();
            if(result.equals("no rows")) {
                Toast.makeText(BuscadorManga.this, "No Results found for entered query", Toast.LENGTH_LONG).show();
            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        Manga manga = new Manga();

                        manga.setNombre(json_data.getString("Nombre"));
                        manga.setCapitulos(json_data.getString("Capitulos"));
                        manga.setVolumenes(json_data.getString("Volumenes"));
                        manga.setTipo(json_data.getString("Tipo"));
                        manga.setEstado(json_data.getString("Estado"));
                        manga.setNota(json_data.getDouble("Nota"));
                        manga.setImagen(json_data.getString("Imagen"));
                        manga.setFechaComienzo(json_data.getString("FechaComienzo"));
                        manga.setFechaFin(json_data.getString("FechaFin"));
                        manga.setGenero(json_data.getString("Genero"));
                        manga.setAutor(json_data.getString("Autor"));
                        manga.setSerializacion(json_data.getString("Serialización"));
                        manga.setSinopsis(json_data.getString("Sinopsis"));
                        manga.setNombreOriginal(json_data.getString("NombreOriginal"));
                        manga.setLink(json_data.getString("Link"));

                        data.add(manga);
                    }

                    // Setup and Handover data to recyclerview
                    mRVFish = (RecyclerView) findViewById(R.id.fishPriceList);
                    mAdapter = new SearchMangaAdapter(BuscadorManga.this, (ArrayList<Manga>) data);
                    mRVFish.setAdapter(mAdapter);
                    mRVFish.setLayoutManager(new LinearLayoutManager(BuscadorManga.this));

                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Toast.makeText(BuscadorManga.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(BuscadorManga.this, result.toString(), Toast.LENGTH_LONG).show();
                }

            }

        }

    }
}
