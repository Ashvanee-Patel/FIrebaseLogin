package com.example.firebaseloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText Email, Password;
    private TextView Register, ForgetPassword;
    private Button Login;
    private String email, password;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uiInterface();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            finish();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldValidation()){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){
                                checkEmailVerification();
                            }else {
                                Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                finish();
            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                finish();
            }
        });
    }
    private void uiInterface(){
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Register = findViewById(R.id.tvRegister);
        Login = findViewById(R.id.btLogin);
        progressBar = findViewById(R.id.progress);
        ForgetPassword = findViewById(R.id.tvForget);

    }
    private Boolean fieldValidation(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag =firebaseUser.isEmailVerified();
        if (emailFlag){
            Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
            finish();
        }
        else {
            Toast.makeText(LoginActivity.this,"Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}