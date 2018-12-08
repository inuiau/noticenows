package com.devdeloop.noticenows.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.devdeloop.noticenows.Model.Pengumuman;
import com.devdeloop.noticenows.R;
import com.devdeloop.noticenows.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PengumumanDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private Pengumuman pengumuman;
    private TextView pengumumanJudul;
    private TextView pengumumanKategori;
    private TextView pengumumanTanggal;
    private TextView pengumumanKonten;
    private WebView webKon;
    private String pengumumanId;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengumuman_detail_main_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        queue = Volley.newRequestQueue(this);

        pengumumanId = getIntent().getStringExtra("id_artikel");
        setUpUI();
        getPengumumanDetail(pengumumanId);
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
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "1");
            intent.putExtra("bartitle", "Pengumuman BAAK");
            this.startActivity(intent);


        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "2");
            intent.putExtra("bartitle", "Pengumuman BAUK");
            this.startActivity(intent);


        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, KategoriActivity.class);
            intent.putExtra("kategoriId", "3");
            intent.putExtra("bartitle", "Pengumuman Prodi");
            this.startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getPengumumanDetail(String id) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                Constants.URLDET + id, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length();i++){
                    try{
                        JSONObject pengumumanObject = response.getJSONObject(i);

                        pengumumanJudul.setText(pengumumanObject.getString("judul"));
                        pengumumanKategori.setText(pengumumanObject.getString("kategori"));
                        pengumumanTanggal.setText(pengumumanObject.getString("tanggal_dibuat"));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            pengumumanKonten.setText(Html.fromHtml(pengumumanObject.getString("konten"), Html.FROM_HTML_MODE_COMPACT));
                            webKon.setVisibility(View.INVISIBLE);
                        } else {
                            pengumumanKonten.setVisibility(View.INVISIBLE);
                            webKon.setVisibility(View.VISIBLE);
                            webKon.getSettings().setUserAgentString("Mozilla/5.0 {Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36");
                            String wk = pengumumanObject.getString("konten");
                            webKon.loadData(wk,"text/html","UTF-8");
                        }

                        //Log.d("Judul: ", pengumuman.getJudul());

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void setUpUI() {
        pengumumanJudul = (TextView) findViewById(R.id.pengumumanJudulDet);
        pengumumanKategori = (TextView) findViewById(R.id.pengumumanKategoriDet);
        pengumumanTanggal = (TextView) findViewById(R.id.pengumumanDateDet);
        pengumumanKonten = (TextView) findViewById(R.id.pengumumanKonten);
        webKon = (WebView) findViewById(R.id.webpeng);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
