package me.anky.coolchineseidioms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Anky An on 15/02/2017.
 * anky25@gmail.com
 */

public class MainScreenFragmentAdapter extends FragmentPagerAdapter{

    // Total number of pages on the Main screen
    static final int NUM_ITEMS = 3;

    public MainScreenFragmentAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            final HomeFragment homeFragment = new HomeFragment();
            return homeFragment;
        } else if (position == 1) {
            final CategoriesFragment categoriesFragment = new CategoriesFragment();
            return categoriesFragment;
        } else {
            final IdiomBankFragment idiomBankFragment = new IdiomBankFragment();
            return idiomBankFragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
