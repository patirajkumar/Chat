package com.example.chatroom.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.chatroom.fragments.Callfragment;
import com.example.chatroom.fragments.Chatsfragment;
import com.example.chatroom.fragments.Statusfragment;

import io.reactivex.rxjava3.annotations.NonNull;

public class fragmentadapter extends FragmentPagerAdapter {
    public fragmentadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new Chatsfragment();
            case 1: return new Statusfragment();
            case 2: return new Callfragment();
            default: return new Chatsfragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0 ){
            title = " Chats";
        }
        if(position == 1 ){
            title = " Status";
        }
        if(position == 2 ){
            title = " Calls";
        }
        return title;
    }
}
