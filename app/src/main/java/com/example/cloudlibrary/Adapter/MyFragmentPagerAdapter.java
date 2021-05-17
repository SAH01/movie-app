package com.example.cloudlibrary.Adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cloudlibrary.Fragment.CloudPageFragment;
import com.example.cloudlibrary.Fragment.FirstPageFragment;
import com.example.cloudlibrary.Fragment.MyPageFragment;
import com.example.cloudlibrary.MainActivity;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 3;
    private CloudPageFragment cloudPageFragment = null;
    private FirstPageFragment firstPageFragment = null;
    private MyPageFragment myPageFragment = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        cloudPageFragment = new CloudPageFragment();
        firstPageFragment = new FirstPageFragment();
        myPageFragment = new MyPageFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = firstPageFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = cloudPageFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myPageFragment;
                break;
        }
        return fragment;
    }

}
