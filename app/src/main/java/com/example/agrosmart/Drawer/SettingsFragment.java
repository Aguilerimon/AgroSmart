package com.example.agrosmart.Drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agrosmart.Drawer.Settings.SettingsAccountFragment;
import com.example.agrosmart.Drawer.Settings.SettingsBackupFragment;
import com.example.agrosmart.Drawer.Settings.SettingsInformationFragment;
import com.example.agrosmart.Drawer.Settings.SettingsNotificationsFragment;
import com.example.agrosmart.Drawer.Settings.SettingsSecurityFragment;
import com.example.agrosmart.R;

public class SettingsFragment extends Fragment
{
    LinearLayout settingsAccount, settingsNotifications, settingsSecurity, settingsInformation, settingsBackup;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        settingsAccount = view.findViewById(R.id.settings_account);
        settingsNotifications = view.findViewById(R.id.settings_notifications);
        settingsSecurity = view.findViewById(R.id.settings_security);
        settingsInformation = view.findViewById(R.id.settings_information);
        settingsBackup = view.findViewById(R.id.settings_backup);

        settingsAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

                SettingsAccountFragment settingsAccountFragment = new SettingsAccountFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,settingsAccountFragment);
                fragmentTransaction.commit();// add the fragment




            }
        });

        settingsNotifications.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

                SettingsNotificationsFragment settingsNotificationsFragment = new SettingsNotificationsFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,settingsNotificationsFragment);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsBackup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

                SettingsBackupFragment settingsBackupFragment = new SettingsBackupFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,settingsBackupFragment);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsSecurity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

                SettingsSecurityFragment settingsSecurityFragment = new SettingsSecurityFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,settingsSecurityFragment);
                fragmentTransaction.commit();// add the fragment
            }
        });

        settingsInformation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().
                        remove(getFragmentManager().findFragmentById(R.id.fragmentContainer)).commit();

                SettingsInformationFragment settingsInformationFragment = new SettingsInformationFragment();
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,settingsInformationFragment);
                fragmentTransaction.commit();// add the fragment
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }

}
