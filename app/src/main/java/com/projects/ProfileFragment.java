package com.projects;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.lang.reflect.Method;


public class ProfileFragment extends Fragment  {
    private FirebaseAuth mAuth;
    private View view;
    private FirebaseUser user;
    // Firebase instance

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button button = view.findViewById(R.id.logout);
        TextView usernameTextView =  view.findViewById(R.id.textViewUsername);
        TextView emailTextView =  view.findViewById(R.id.textViewEmail);
//        TextView phoneTextView =  view.findViewById(R.id.textViewUsername);

        usernameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
//        phoneTextView.setText(user.getDisplayName());

        view.findViewById(R.id.profile_edit_username).setOnClickListener(view -> {
            EditText inputEditTextField = new EditText(getContext());
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("New username")
                    .setView(inputEditTextField)
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newUsername = inputEditTextField.getText().toString();
                            if (!newUsername.isEmpty() && !newUsername.equals(user.getDisplayName())){
                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(newUsername).build();
                                user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Username change to "+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                                            usernameTextView.setText(user.getDisplayName());
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });

        view.findViewById(R.id.profile_edit_email).setOnClickListener(view -> {
            EditText inputEditTextField = new EditText(getContext());
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("New email")
                    .setView(inputEditTextField)
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newEmail = inputEditTextField.getText().toString();
                            if (!newEmail.isEmpty() && !newEmail.equals(user.getEmail())){
                                user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Email change to "+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                                            usernameTextView.setText(user.getEmail());
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });



        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

}