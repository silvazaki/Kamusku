package com.example.kamusku.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamusku.R;
import com.example.kamusku.adapter.KamusAdapter;
import com.example.kamusku.data.database.KamusHelper;
import com.example.kamusku.data.model.Data;
import com.example.kamusku.util.InterfaceOnItemClick;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String TAG = "hasil";

    RecyclerView recyclerView;
    KamusAdapter kamusAdapter;
    KamusHelper kamusHelper;
    MaterialSearchView materialSearchView;
    TextView txtInfo;
    ArrayList<Data> allData;
    boolean indo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        materialSearchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycleView);
        txtInfo = findViewById(R.id.txtInfo);

        kamusHelper = new KamusHelper(this);
        kamusAdapter = new KamusAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(kamusAdapter);
        setAllData(indo);
        setSubtitle(R.string.indo_to_english);

        setSearchView();
        setOnClick();
    }

    public void setSubtitle(int title){
        if (getSupportActionBar()!=null){
            getSupportActionBar().setSubtitle(title);
        }
    }

    public void setAllData(boolean data){
        kamusHelper.open();
        allData = kamusHelper.getAllData(data);
        kamusHelper.close();
        kamusAdapter.addItem(allData);
    }

    public void setData(String data){
        kamusHelper.open();
        allData = kamusHelper.getDataByName(indo,data);
        kamusHelper.close();
        kamusAdapter.addItem(allData);
    }

    private void setSearchView() {
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText!=null || newText.equals("")){
                    setData(newText);
                }
                Log.d("hasil", "onQueryTextChange: "+newText);

                return false;
            }
        });
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                txtInfo.setVisibility(View.VISIBLE);

            }

            @Override
            public void onSearchViewClosed() {
                txtInfo.setVisibility(View.GONE);
                setAllData(indo);
            }
        });
    }

    public void setOnClick(){
        kamusAdapter.setCallback(new InterfaceOnItemClick.KamusItemCallback() {
            @Override
            public void onItemClick(int position, View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.DETAIL, kamusAdapter.getItem(position));
                    startActivity(intent);
                }catch (Exception e){
                    Log.e(TAG, "onItemClick: "+e+" i="+position );
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_indoenglish) {
            if (!indo){
                setAllData(indo);
                txtInfo.setText(R.string.indo_to_english);
                setSubtitle(R.string.indo_to_english);
                indo = true;
            }
        } else if (id == R.id.nav_englishindo) {
            if (indo){
                setAllData(!indo);
                txtInfo.setText(R.string.english_to_indo);
                setSubtitle(R.string.english_to_indo);
                indo= false;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
