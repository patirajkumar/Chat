package com.example.chatroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatroom.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class setting extends AppCompatActivity {

    ActivitySettingBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        binding.backarrow.setOnClickListener(v -> {
            Intent in = new Intent(setting.this, MainActivity.class);
            startActivity(in);
        });

        binding.save.setOnClickListener(v -> {
            String user1 = binding.setuser.getText().toString();
            String status = binding.setstatus.getText().toString();
            HashMap<String, Object> obj = new HashMap<>();
            obj.put("username", user1);
            obj.put("status",status);
            db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
            Toast.makeText(setting.this, "Profile Updated", Toast.LENGTH_SHORT).show();
        });

        db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users usr = snapshot.getValue(Users.class);
                        Picasso.get().load(usr.getProfpic()).placeholder(R.drawable.ic_avatar_user_svgrepo_com)
                                .into(binding.pic);
                        binding.setstatus.setText(usr.getStatus());
                        binding.setuser.setText(usr.getUsername());
                        binding.setphone.setText(usr.getPhone());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });

        binding.plus.setOnClickListener(v -> {
            Intent in2 = new Intent();
            in2.setAction(Intent.ACTION_GET_CONTENT);
            in2.setType("image/*");
            startActivityForResult(in2, 33);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null) {
            Uri sfile= data.getData();
            binding.pic.setImageURI(sfile);

            final StorageReference ref = storage.getReference().child("Profile")
                    .child(FirebaseAuth.getInstance().getUid());
            ref.putFile(sfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            db.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profpic").setValue(uri.toString());
                            Toast.makeText(setting.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}