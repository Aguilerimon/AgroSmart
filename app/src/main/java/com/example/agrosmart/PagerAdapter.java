package com.example.agrosmart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.agrosmart.Tab.GroundFragment;
import com.example.agrosmart.Tab.WaterFragment;
import com.example.agrosmart.Tab.WindFragment;

public class  PagerAdapter extends FragmentPagerAdapter
{
    private int tabsNumber;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs)
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
                WaterFragment waterFragment = new WaterFragment();
                return waterFragment;
            case 1:
                WindFragment windFragment = new WindFragment();
                return windFragment;
            case 2 :
                GroundFragment groundFragment = new GroundFragment();
                return groundFragment;
                default: return null;
        }

    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
