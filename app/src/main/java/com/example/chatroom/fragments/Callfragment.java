package com.example.chatroom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.chatroom.databinding.FragmentCallfragmentBinding;

public class Callfragment extends Fragment {

    public Callfragment() {}

    FragmentCallfragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCallfragmentBinding.inflate(inflater, container, false);

        binding.tel.setOnClickListener(v -> {
            Intent in = new Intent();
            in.setAction(Intent.ACTION_DIAL);
            startActivity(in);
        });

        return binding.getRoot();

    }
}