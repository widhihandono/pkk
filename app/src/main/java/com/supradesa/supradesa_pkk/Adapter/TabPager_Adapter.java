package com.supradesa.supradesa_pkk.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.supradesa.supradesa_pkk.Fragment.Fg_belum_upload;
import com.supradesa.supradesa_pkk.Fragment.Fg_sudah_upload;

public class TabPager_Adapter extends FragmentPagerAdapter {

    public TabPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0: return new Fg_belum_upload();
            case 1: return new Fg_sudah_upload();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "Belum Upload";
            case 1: return "Sudah Upload";
            default: return null;
        }
    }

}
