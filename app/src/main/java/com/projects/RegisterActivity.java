package com.projects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText fullname, email, password;
    private ImageView iv_mic;
    private TextView tv_Speech_to_text_fullname;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iv_mic = findViewById(R.id.voice_full_name);
        tv_Speech_to_text_fullname = findViewById(R.id.editTextEmail);

        // When mic image clicked start voice recognition to fill full name
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
                try {
                    startActivityIfNeeded(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        // If everything is okay load text to tv_Speech_to_text_fullname
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                tv_Speech_to_text_fullname.setText(Objects.requireNonNull(result).get(0));
            }
        }
    }

    public void back(View view){
        // Close and load login activity
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void register(View view){


        fullname = findViewById(R.id.editTextFullName);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.edit_password);

        // Firebase instance
        mAuth = FirebaseAuth.getInstance();
        // Register using email and password user if him infos are correct to firebase and direct him to home
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
        // Update users profile to inster also his full name
        UserProfileChangeRequest profileChangeRequest  = new UserProfileChangeRequest
                .Builder().setDisplayName(fullname)
                .build();
        user.updateProfile(profileChangeRequest);
    }

    public void ShowHidePass(View view){
        // When clicked eye image show or hide password
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