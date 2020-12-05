package com.example.agrosmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrosmart.NavigationDrawer.AccountFragment;
import com.example.agrosmart.NavigationDrawer.ConnectionsFragment;
import com.example.agrosmart.NavigationDrawer.ContactFragment;
import com.example.agrosmart.NavigationDrawer.HomeFragment;
import com.example.agrosmart.NavigationDrawer.NotificationsFragment;
import com.example.agrosmart.NavigationDrawer.PolicyFragment;
import com.example.agrosmart.NavigationDrawer.SensorsFragment;
import com.example.agrosmart.NavigationDrawer.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.Inflater;



public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    TabItem waterTab,windTab,groundTab;
    HomeFragment homeFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frameLayout;
    String nombre, correo, phone, password, user_id;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Obtener JSON de la DB
        getJSON("http://agrosmartamm.000webhostapp.com/agrosmart/getSensor.php");

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View mHeaderView =  mNavigationView.getHeaderView(0);

        TextView txtNameLogin = mHeaderView.findViewById(R.id.txt_username);
        TextView txtEmailLogin = mHeaderView.findViewById(R.id.txt_user_mail);
        searchView = findViewById(R.id.search_bar);

        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("Name");
        correo = datos.getString("Email");
        phone = datos.getString("PhoneNumber");
        password = datos.getString("Password");
        user_id = datos.getString("User_Id");


        txtNameLogin.setText(nombre);
        txtEmailLogin.setText(correo);

        frameLayout = findViewById(R.id.nav_host_fragment);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment,homeFragment);
        fragmentTransaction.commit();// add the fragment
        showHome();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "ChatBot", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void showHome()
    {
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,homeFragment, homeFragment.getTag());
        fragmentTransaction.commit();// add the fragment
        homeStatus = true;
    }

    boolean homeStatus;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch(item.getItemId())
        {
            case R.id.nav_home:
                loadFragment(new HomeFragment());
                break;
            case R.id.nav_sensors:
                loadFragment(new SensorsFragment());
                homeStatus = false;
                break;
            case R.id.nav_connections:
                loadFragment(new ConnectionsFragment());
                homeStatus = false;
                break;
            case R.id.nav_account:
                loadFragment(new AccountFragment(nombre, correo, phone));
                homeStatus = false;
                break;
            case R.id.nav_policy:
                loadFragment(new PolicyFragment());
                homeStatus = false;
                break;
            case R.id.nav_contact:
                loadFragment(new ContactFragment(correo));
                homeStatus = false;
                break;
            case R.id.nav_settings:
                loadFragment(new SettingsFragment(user_id, nombre, correo, phone));
                homeStatus = false;
                break;
            default: return false;
        }

        return false;
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(homeStatus)
            {
                finishAffinity();
            }
            else
            {
                showHome();
            }

        }
    }


    private void loadFragment(Fragment newFragment)
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment,newFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    boolean band = true;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_settings:
                loadFragment(new SettingsFragment(user_id, nombre, correo, phone));
                homeStatus = false;
                break;
            case R.id.action_notifications:
                loadFragment(new NotificationsFragment());
                homeStatus = false;
                break;
            default: return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


}