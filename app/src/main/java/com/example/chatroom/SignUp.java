package com.example.chatroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatroom.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        pd = new ProgressDialog(SignUp.this);
        pd.setTitle("Creating Account");
        pd.setMessage("We are creating account");

        binding.b1signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                auth.createUserWithEmailAndPassword
                        (binding.etmail.getText().toString(), binding.etpass.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if(task.isSuccessful()) {
                                    Users usr = new Users(binding.etperson.getText().toString(), binding.etmail.getText().toString(), binding.etpass.getText().toString(), binding.etphone.getText().toString());
                                    String id= task.getResult().getUser().getUid();
                                    db.getReference().child("Users").child(id).setValue(usr);
                                    Toast.makeText(SignUp.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                                    Intent in = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(in);
                                }
                                else {
                                    Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUp.this, SignIn.class);
                startActivity(in);
            }
        });
    }
}