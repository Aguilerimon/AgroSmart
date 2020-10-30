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

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrosmart.Drawer.AccountFragment;
import com.example.agrosmart.Drawer.ConnectionsFragment;
import com.example.agrosmart.Drawer.ContactFragment;
import com.example.agrosmart.Drawer.HomeFragment;
import com.example.agrosmart.Drawer.NotificationsFragment;
import com.example.agrosmart.Drawer.PolicyFragment;
import com.example.agrosmart.Drawer.SettingsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem waterTab,windTab,groundTab;
    PagerAdapter adapter;
    HomeFragment homeFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frameLayout;
    String nombre, correo, phone, password;

    Fragment fragment = null;

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

        Bundle datos = getIntent().getExtras();
        nombre = datos.getString("Name");
        correo = datos.getString("Email");
        phone = datos.getString("PhoneNumber");
        password = datos.getString("Password");

        txtNameLogin.setText(nombre);
        txtEmailLogin.setText(correo);

        pager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);
        frameLayout = findViewById(R.id.fragmentContainer);

        waterTab = findViewById(R.id.tab_water_sensor);
        windTab = findViewById(R.id.tab_wind_sensor);
        groundTab = findViewById(R.id.tab_ground_sensor);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        pager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);

        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer,homeFragment);
        fragmentTransaction.commit();// add the fragment

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTabLayout.getTabCount());
        pager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        showHome();
    }

    private void showHome()
    {
        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,homeFragment, homeFragment.getTag());
        fragmentTransaction.commit();// add the fragment
        showDrawerFragments();
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
                showDrawerFragments();
                break;
            case R.id.nav_notifications:
                loadFragment(new NotificationsFragment());
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_connections:
                loadFragment(new ConnectionsFragment());
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_account:
                loadFragment(new AccountFragment(nombre, correo, phone, password));
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_policy:
                loadFragment(new PolicyFragment());
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_contact:
                loadFragment(new ContactFragment());
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_settings:
                loadFragment(new SettingsFragment());
                showDrawerFragments();
                homeStatus = false;
                break;
            case R.id.nav_sensors:
                showTabFragments();
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

    private void showDrawerFragments()
    {
        pager.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    private void showTabFragments()
    {
        pager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment newFragment)
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,newFragment);
        fragmentTransaction.commit();
    }

}
