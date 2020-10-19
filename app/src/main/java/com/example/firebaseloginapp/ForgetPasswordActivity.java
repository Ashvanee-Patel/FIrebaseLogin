package com.example.firebaseloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText Email;
    private Button Reset;
    private FirebaseAuth firebaseAuth;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        uiInterface();
        firebaseAuth = FirebaseAuth.getInstance();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    email = Email.getText().toString().trim();
                    if(email.isEmpty()){
                        Toast.makeText(ForgetPasswordActivity.this, "Please enter your registered email id", Toast.LENGTH_SHORT).show();
                    }else {
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Please check your registered email id", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                    finish();
                                }else {
                                   Toast.makeText(ForgetPasswordActivity.this, "Please make sure that you are entering registered email id", Toast.LENGTH_SHORT).show();
                               }
                            }
                        });
                    }
            }
        });
    }

    private void uiInterface() {
        Email = findViewById(R.id.etEmail);
        Reset = findViewById(R.id.btResetP);
    }
}