package com.example.zissu.noche_1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mSendData;
    private SearchFragment fragment = null;
    private android.app.FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(0);
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

        if (id == R.id.nav_bars) {
            BarsFragment barsFragment = new BarsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main_layout ,
                    barsFragment ,barsFragment.getTag() ).commit();

        } else if (id == R.id.nav_restaurants) {
            RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main_layout ,
                    restaurantsFragment , restaurantsFragment.getTag()).commit();

        } else if (id == R.id.nav_clubs) {
            ClubsFragment clubsFragment = new ClubsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_main_layout ,
                    clubsFragment , clubsFragment.getTag()).commit();


        }
        displayView(0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickBar (View view){
        BarsFragment barsFragment = new BarsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main_layout ,
                barsFragment ,barsFragment.getTag() ).commit();
        try {
            URL url = new URL("http://193.106.55.242:8080/getAllPlaces");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
             response.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onClickClub (View view){
        ClubsFragment clubsFragment = new ClubsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main_layout ,
                clubsFragment ,clubsFragment.getTag() ).commit();

    }
    public void onClickRes (View view){
        RestaurantsFragment restaurantsFragment = new RestaurantsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_main_layout ,
                restaurantsFragment ,restaurantsFragment.getTag() ).commit();

    }
    private void displayView(int position) {
        fragment = null;
        String fragmentTags = "";
        switch (position) {
            case 0:
                fragment = new SearchFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTags).commit();
        }
    }

}
