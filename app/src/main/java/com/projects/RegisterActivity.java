package com.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText fullname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void back(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void register(View view){


        fullname = findViewById(R.id.editTextFullName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.edit_password);

        // Firebase instance
        mAuth = FirebaseAuth.getInstance();

        CheckBox termConditions = findViewById(R.id.checkBox);
        if (termConditions.isChecked()){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                changeUserProfile(fullname.getText().toString(),mAuth.getCurrentUser());
                                startActivity(new Intent(getApplicationContext(),UserMenuActivity.class));
                                finish();
                            }
                        }
                    });


        }
    }

    private void changeUserProfile(String fullname, FirebaseUser user){
        UserProfileChangeRequest profileChangeRequest  = new UserProfileChangeRequest
                .Builder().setDisplayName(fullname)
                .build();
        user.updateProfile(profileChangeRequest);
    }

    public void ShowHidePass(View view){
        EditText passwordText = findViewById(R.id.edit_password);

        if(view.getId()==R.id.show_pass_btn){

            if(passwordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24_open);

                //Show Password
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                //Hide Password
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}