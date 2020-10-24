package com.example.agrosmart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.agrosmart.Tab.GroundFragment;
import com.example.agrosmart.Tab.WaterFragment;
import com.example.agrosmart.Tab.WindFragment;

public class PagerAdapter extends FragmentPagerAdapter
{
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior,int tabs)
    {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new WaterFragment();
            case 1:
                return new WindFragment();
            case 2 :
                return new GroundFragment();
                default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
