package com.devdeloop.noticenows.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.devdeloop.noticenows.Data.PengumumanRecViewAdapter;
import com.devdeloop.noticenows.Model.Pengumuman;
import com.devdeloop.noticenows.R;
import com.devdeloop.noticenows.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KategoriActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private PengumumanRecViewAdapter pengumumanRecViewAdapter;
    private List<Pengumuman> pengumumanList;
    private RequestQueue queue;

    private String kategoriId;
    private String barTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategori_main);

        barTitle = getIntent().getStringExtra("bartitle");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(barTitle);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        queue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kategoriId = getIntent().getStringExtra("kategoriId");

        pengumumanList = new ArrayList<>();

        pengumumanList = getPengumumanByKategori(kategoriId);

        pengumumanRecViewAdapter = new PengumumanRecViewAdapter(this, pengumumanList);
        recyclerView.setAdapter(pengumumanRecViewAdapter);
        pengumumanRecViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            refreshActivity();
            super.onBackPressed();
        }
    }

    public void refreshActivity(){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "1");
            intent.putExtra("bartitle", "Pengumuman BAAK");
            this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "2");
            intent.putExtra("bartitle", "Pengumuman BAUK");
            this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "3");
            intent.putExtra("bartitle", "Pengumuman Prodi");
            this.startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Pengumuman> getPengumumanByKategori(String kategoriId) {
        pengumumanList.clear();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Constants.URLKAT + kategoriId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length();i++){
                    try{
                        JSONObject pengumumanObject = response.getJSONObject(i);

                        Pengumuman pengumuman = new Pengumuman();

                        pengumuman.setId_artikel(pengumumanObject.getString("id"));
                        pengumuman.setJudul(pengumumanObject.getString("judul"));
                        pengumuman.setKategori(pengumumanObject.getString("kategori"));
                        pengumuman.setTanggal_dibuat(pengumumanObject.getString("tanggal_dibuat"));

                        //Log.d("id_Artikel: ", pengumuman.getId_artikel());

                        pengumumanList.add(pengumuman);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                pengumumanRecViewAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(arrayRequest);

        return pengumumanList;
    }

}
