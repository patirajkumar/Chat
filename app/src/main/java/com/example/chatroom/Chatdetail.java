package com.example.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatroom.adapter.Chatadapter;
import com.example.chatroom.databinding.ActivityChatdetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class Chatdetail extends AppCompatActivity {

    ActivityChatdetailBinding binding;
    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderid = auth.getUid();
        String receiveid = getIntent().getStringExtra("userid");
        String username = getIntent().getStringExtra("username");
        String profpic = getIntent().getStringExtra("profpic");
        String ph = getIntent().getStringExtra("phone");

        binding.username.setText(username);
        Picasso.get().load(profpic).placeholder(R.drawable.ic_avatar_user_svgrepo_com).into(binding.profileImage);

        binding.back.setOnClickListener(v -> {
            Intent in = new Intent(Chatdetail.this, MainActivity.class);
            startActivity(in);
        });

        final ArrayList<Message> message1 = new ArrayList<>();

        final Chatadapter chatadapter = new Chatadapter(message1, this);
        binding.chatrecycle.setAdapter(chatadapter);

        LinearLayoutManager lin = new LinearLayoutManager(this);
        binding.chatrecycle.setLayoutManager(lin);

        final String snd = senderid + receiveid;
        final String rec = receiveid + senderid;

        db.getReference().child("chats").child(snd)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                message1.clear();
                for(DataSnapshot snapshot2 : snapshot.getChildren()) {
                    Message mes2 = snapshot2.getValue(Message.class);
                    message1.add(mes2);
                }
                chatadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.call.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+ph));
            startActivity(i);
        });

        binding.send.setOnClickListener(v -> {
            if(binding.Emsg.getText().toString().isEmpty()) {
                binding.Emsg.setError("Empty");
                return;
            }
            String msg1 = binding.Emsg.getText().toString();
            final Message mes1 = new Message(senderid, msg1);
            mes1.setTimestamp(new Date().getTime());
            binding.Emsg.setText("");
            db.getReference().child("chats").child(snd).push().setValue(mes1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    db.getReference().child("chats").child(rec).push().setValue(mes1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {}
                            });
                }
            });
        });

    }
}