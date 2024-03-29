package com.example.chatroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatroom.Users;
import com.example.chatroom.adapter.useradapter;
import com.example.chatroom.databinding.FragmentChatsfragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Chatsfragment extends Fragment {


    public Chatsfragment() {}

    FragmentChatsfragmentBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsfragmentBinding.inflate(inflater, container, false);
        db = FirebaseDatabase.getInstance();

        useradapter adp = new useradapter(list, getContext());
        binding.rec.setAdapter(adp);

        LinearLayoutManager lay = new LinearLayoutManager(getContext());
        binding.rec.setLayoutManager(lay);

        db.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUid(dataSnapshot.getKey());
                    if(!users.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        list.add(users);
                    }
                }
                  adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}