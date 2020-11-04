package com.example.agrosmart.NavigationDrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.agrosmart.PagerAdapter;
import com.example.agrosmart.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class SensorsFragment extends Fragment
{
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabLayout mTabLayout;
    TabItem waterTab,windTab,groundTab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_sensors, container, false);

        viewPager = root.findViewById(R.id.viewpager);
        mTabLayout = root.findViewById(R.id.tablayout);
        waterTab = root.findViewById(R.id.tab_water_sensor);
        windTab = root.findViewById(R.id.tab_wind_sensor);
        groundTab = root.findViewById(R.id.tab_ground_sensor);

        pagerAdapter = new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        return root;
    }

}