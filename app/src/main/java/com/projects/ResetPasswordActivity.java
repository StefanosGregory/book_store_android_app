package com.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.editTextEmailReset);
        btnReset = (Button) findViewById(R.id.buttonReset);
        mAuth = FirebaseAuth.getInstance();

        String msg1,msg2,msg3;
        // If  local language is greek load greek msg values
        if(Locale.getDefault().getDisplayLanguage().equals("Ελληνικά")){
            //Greek
            msg1 = "Εισαγάγετε τη διεύθυνση email σας";
            msg2 = "Η επαναφορά του email στάλθηκε!";
            msg3 = "Σφάλμα";
        }
        // Else load english msg values
        else
        {
            //English
            msg1 = "Enter your mail address";
            msg2 = "Email reset sent!";
            msg3 = "Error";
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            // Sent reset email to change password
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), msg1, Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this, msg2, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this, msg3, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
    }

    public void login(View view){
        finish();
    }
}