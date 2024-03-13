package com.example.chatroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatroom.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth auth;
    ProgressDialog pd;
    GoogleSignInClient cli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(SignIn.this);
        pd.setTitle("Login");
        pd.setMessage("Login to your account");

        binding.b1signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                if(binding.etmail1.getText().toString().isEmpty()) {
                    binding.etmail1.setError("Enter your mail");
                    return;
                }
                if(binding.etpass1.getText().toString().isEmpty()) {
                    binding.etpass1.setError("Enter your mail");
                    return;
                }

                auth.signInWithEmailAndPassword
                        (binding.etmail1.getText().toString(), binding.etpass1.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if(task.isSuccessful()) {
                            Intent in = new Intent(SignIn.this, MainActivity.class);
                            startActivity(in);
                        }
                        else {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignIn.this, SignUp.class);
                startActivity(in);
            }
        });
        if(auth.getCurrentUser() != null) {
            Intent in = new Intent(SignIn.this, MainActivity.class);
            startActivity(in);
        }
    }


}