package com.example.alber.prueba10.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alber.prueba10.R;
import com.example.alber.prueba10.app.DevuelveJSON;
import com.example.alber.prueba10.clases.Manga;
import com.example.alber.prueba10.clases.MangaAdapter;
import com.example.alber.prueba10.helper.SQLiteHandler;
import com.example.alber.prueba10.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MangaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtUsuario;
    private TextView txtAnime;

    private SQLiteHandler db;
    private SessionManager session;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private String url_consulta;

    private JSONArray jSONArray;
    private DevuelveJSON devuelveJSON;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<Manga> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");

        // Nosferatu --> 192.168.1.87
        url_consulta = "http://192.168.1.41:8080/prueba/consulusu.php?ins_sql=SELECT%20*%20FROM%20manga%20WHERE%20Nombre%20IN" +
                "%20(SELECT%20Nombre%20FROM%20mangauser%20WHERE%20Usuario='"+name+"')";

        devuelveJSON = new DevuelveJSON();

        probarConexion();

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        //recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MangaAdapter(items, this);
        recycler.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        txtUsuario = (TextView) header.findViewById(R.id.usuarioAnime);
        txtAnime = (TextView) header.findViewById(R.id.tAnime);
        txtUsuario.setText(name);
        txtAnime.setText("@"+name);
        //Lo mismo para contar todos los animes y mangas que se hallan visto y leido

        mSwipeRefreshLayout.setColorSchemeResources(R.color.dark, R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent refresh = new Intent(MangaActivity.this, MangaActivity.class);
                        startActivity(refresh);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.logout) {
            session.setLogin(false);
            db.deleteUsers();
            // Launching the login activity
            Intent intent = new Intent(MangaActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.support) {
            i = new Intent(this, CorreoActivity.class);
            startActivity(i);
        } else if (id == R.id.action_search) {
            i = new Intent(this, BuscadorManga.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;

        if (id == R.id.anime_view) {
            i = new Intent(this, MainActivity.class);
            startActivity(i);
        }  else if (id == R.id.top_view) {
            i = new Intent(this, TopMangaActivity.class);
            startActivity(i);
        } else if (id == R.id.proximamente_view) {
            i = new Intent(this, ProxMangaActivity.class);
            startActivity(i);
        } else if (id == R.id.pub_view) {
            i = new Intent(this, PubliActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MangaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    class ObtenerDatos extends AsyncTask<String, String, JSONArray> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MangaActivity.this);
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
                        manga.setImagen(jsonObject.getString("Imagen"));
                        manga.setNombre(jsonObject.getString("Nombre"));
                        manga.setCapitulos(jsonObject.getString("Capitulos"));
                        manga.setVolumenes(jsonObject.getString("Volumenes"));
                        manga.setTipo(jsonObject.getString("Tipo"));
                        manga.setEstado(jsonObject.getString("Estado"));
                        manga.setNota(jsonObject.getInt("Nota"));
                        manga.setFecha(jsonObject.getString("FechaComienzo"));
                        items.add(new Manga(jsonObject.getString("Imagen"), jsonObject.getInt("Nota"), jsonObject.getString("Nombre"), jsonObject.getString("Capitulos"), jsonObject.getString("Volumenes"), jsonObject.getString("Tipo"), jsonObject.getString("Estado"), jsonObject.getString("FechaComienzo")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recycler.setHasFixedSize(true);
                    recycler.setLayoutManager(lManager);
                    recycler.setAdapter(adapter);
                }
            } else {
                Toast.makeText(MangaActivity.this, "JSON Array nulo", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void probarConexion() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new MangaActivity.ObtenerDatos().execute();
        }
    }

    public void onRestart() {
        super.onRestart();
        this.finish();
        Intent refresh = new Intent(this, MangaActivity.class);
        startActivity(refresh);
    }
}
