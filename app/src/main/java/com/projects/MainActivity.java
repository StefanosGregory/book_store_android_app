package com.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Firebase instance
        mAuth = FirebaseAuth.getInstance();
        // Text Inputs email and password
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
    }

    // Login method
    public  void login(View view){
//        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
        mAuth.signInWithEmailAndPassword("test@mail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showMessage("Success", "login successfully");
                            startActivity(new Intent(getApplicationContext(),UserMenuActivity.class));
                            finish();

                        }else {
                            showMessage("Error", task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    // Register method
    public  void register(View view){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        finish();
//        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            showMessage("Success", "register successfully uid: " + mAuth.getUid());
//                        }else {
//                            showMessage("Error", task.getException().getLocalizedMessage());
//                        }
//                    }
//                });
        //auth.signOut();
    }

    void showMessage(String title, String message){
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).setCancelable(true).show();
    }
}