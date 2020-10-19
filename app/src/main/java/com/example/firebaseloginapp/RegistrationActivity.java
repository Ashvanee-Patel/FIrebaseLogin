package com.example.firebaseloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private Button Register;
    private TextView Login;
    private EditText Name, Email, Password, Age;
    private ProgressBar progressBar;
    private String name, email, password, age;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        uiInterface();
        firebaseAuth = FirebaseAuth.getInstance();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldValidation()){
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    sendEmailVerification();
                                }   else {
                                    Toast.makeText(RegistrationActivity.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    private void uiInterface(){
        Register = findViewById(R.id.btRegistration);
        Name = findViewById(R.id.etName);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.tvLogin);
        Age = findViewById(R.id.etAge);
        progressBar = findViewById(R.id.progress);
    }
    private Boolean fieldValidation(){
        name = Name.getText().toString().trim();
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        age = Age.getText().toString().trim();
        if(name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendDataInDatabase();
                        Toast.makeText(RegistrationActivity.this,"Successfully Registered, Verification mail sent! please check your mail ", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                        finish();
                    }else {
                        Toast.makeText(RegistrationActivity.this,"Verification mail has not been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendDataInDatabase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserData userData = new UserData(name,email,age);
        myRef.setValue(userData);
    }
}