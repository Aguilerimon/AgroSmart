package com.example.agrosmart.NavigationDrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agrosmart.NavigationDrawer.Settings.SettingsAccountFragment;
import com.example.agrosmart.NavigationDrawer.Settings.SettingsBackupFragment;
import com.example.agrosmart.NavigationDrawer.Settings.SettingsInformationFragment;
import com.example.agrosmart.NavigationDrawer.Settings.SettingsNotificationsFragment;
import com.example.agrosmart.NavigationDrawer.Settings.SettingsSecurityFragment;
import com.example.agrosmart.R;

public class SettingsFragment extends Fragment
{

    LinearLayout settingsAccount, settingsNotifications, settingsSecurity, settingsInformation, settingsBackup;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String  name, email, phoneNumber, id;

    public SettingsFragment(String id, String nombre, String correo, String phone)
    {
        this.id = id;
        this.name = nombre;
        this.email = correo;
        this.phoneNumber = phone;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        settingsAccount = root.findViewById(R.id.settings_account);
        settingsNotifications = root.findViewById(R.id.settings_notifications);
        settingsSecurity = root.findViewById(R.id.settings_security);
        settingsInformation = root.findViewById(R.id.settings_information);
        settingsBackup = root.findViewById(R.id.settings_backup);


        settingsAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.nav_host_fragment)).commit();

                SettingsAccountFragment settingsAccountFragment = new SettingsAccountFragment(id, name, email, phoneNumber);
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,settingsAccountFragment).addToBackStack(null);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsNotifications.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.nav_host_fragment)).commit();

                SettingsNotificationsFragment settingsNotificationsFragment = new SettingsNotificationsFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,settingsNotificationsFragment).addToBackStack(null);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsBackup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.nav_host_fragment)).commit();

                SettingsBackupFragment settingsBackupFragment = new SettingsBackupFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,settingsBackupFragment).addToBackStack(null);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsSecurity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.nav_host_fragment)).commit();

                SettingsSecurityFragment settingsSecurityFragment = new SettingsSecurityFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,settingsSecurityFragment).addToBackStack(null);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsInformation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.nav_host_fragment)).commit();

                SettingsInformationFragment settingsInformationFragment = new SettingsInformationFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment,settingsInformationFragment).addToBackStack(null);
                fragmentTransaction.commit();// add the fragment
            }
        });

        return root;
    }

}