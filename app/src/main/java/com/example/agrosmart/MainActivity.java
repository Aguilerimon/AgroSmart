package com.example.agrosmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.agrosmart.NavigationDrawer.AccountFragment;
import com.example.agrosmart.NavigationDrawer.ConnectionsFragment;
import com.example.agrosmart.NavigationDrawer.ContactFragment;
import com.example.agrosmart.NavigationDrawer.HomeFragment;
import com.example.agrosmart.NavigationDrawer.PolicyFragment;
import com.example.agrosmart.NavigationDrawer.SensorsFragment;
import com.example.agrosmart.NavigationDrawer.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;

import org.json.JSONException;
import org.json.JSONObject;


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

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICATION";
    public final static int NOTIFICATION_ID = 0;

    String dht11, mq135, fc28, date_now, notification_text = "";

    Bundle status = FingerprintActivity.bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        dht11 = status.getString("status_dht11");
        mq135 = status.getString("status_mq135");
        fc28 = status.getString("status_fc28");
        date_now = status.getString("date_now");


        //Toast.makeText(MainActivity.this, "Datos: " + dht11 + " - " + mq135 + " - " + fc28 + " - " + date_now, Toast.LENGTH_LONG).show();

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

        String sensors_combination = dht11 + mq135 + fc28;

        switch(sensors_combination)
        {
            case "000":
                notification_text = getString(R.string.s000);
                notification(notification_text);
            break;
            case "001":
                notification_text = getString(R.string.s001);
                notification(notification_text);
            break;
            case "010":
                notification_text = getString(R.string.s010);
                notification(notification_text);
            break;
            case "011":
                notification_text = getString(R.string.s011);
                notification(notification_text);
            break;
            case "100":
                notification_text = getString(R.string.s100);
                notification(notification_text);
            break;
            case "101":
                notification_text = getString(R.string.s101);
                notification(notification_text);
            break;
            case "110":
                notification_text = getString(R.string.s110);
                notification(notification_text);
            break;
            case "111":
                notification_text = getString(R.string.s111);
                notification(notification_text);
            break;
            default:return;
        }
    }

    private void setPendingIntent()
    {
        Intent intent = new Intent(this, NotificationsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void notification(String not)
    {
        setPendingIntent();
        createNotificationChannel();
        createNotification(not);
        notificationsResponse();
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence channel_Name = "notification_channel";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channel_Name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo_as_icon);
        builder.setContentTitle("AgroSmart");
        builder.setContentText(text);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.GREEN, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
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
                Intent intent = new Intent(this, NotificationsActivity.class);
                startActivity(intent);
                homeStatus = false;
                break;
            default: return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void notificationsResponse()
    {
        try {
            Response.Listener<String> responseListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean successResponse = jsonResponse.getBoolean("success");

                        if(successResponse == true)
                        {
                            Toast.makeText(MainActivity.this, "Notificacion cargada", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setMessage(R.string.register_error).setNegativeButton(R.string.retry, null).create().show();
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, R.string.register_error + ": " + e, Toast.LENGTH_LONG).show();
                    }
                }

            };

            NotificationRequest notificationResponse = new NotificationRequest(notification_text, user_id,responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(notificationResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}