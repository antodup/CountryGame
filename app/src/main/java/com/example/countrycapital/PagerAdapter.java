package com.example.countrycapital;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;
    Context context;

    String namePlayer;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, Context nContext, String nNamePLayer) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = nContext;
        this.namePlayer = nNamePLayer;
        //Log.i("Name player", this.namePlayer);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("game", "From Activity");
                bundle.putString("namePlayer", this.namePlayer);
                // set Fragmentclass Arguments
                GameFragment fragobj = new GameFragment();
                fragobj.setArguments(bundle);

                return fragobj;
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.tab_label1);
            case 1:
                return context.getResources().getString(R.string.tab_label2);
        }
        return null;
    }
}
