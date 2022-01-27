package com.example.mytext;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class FragmentPagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> fragments;

    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment(R.layout.fragment_one);
            case 1:
                return new Fragment(R.layout.fragment_two);
            default:
                return new Fragment(R.layout.fragment_three);
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
