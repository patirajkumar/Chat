package com.example.chatroom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.chatroom.databinding.FragmentStatusfragmentBinding;

public class Statusfragment extends Fragment {

    public Statusfragment() {}

    FragmentStatusfragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusfragmentBinding.inflate(inflater, container,false);
        binding.add.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivity(i);
        });

        return binding.getRoot();

    }
}