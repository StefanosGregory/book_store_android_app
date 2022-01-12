package com.projects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;

import me.ibrahimsn.lib.SmoothBottomBar;

public class UserMenuActivity extends AppCompatActivity {
    private NavController navController;
    private SmoothBottomBar bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        // Sign items
        bottomNavigationView= findViewById(R.id.bottomBar);
        navController= Navigation.findNavController(this, R.id.user_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        // Call setupSmoothBottomBar method
        setupSmoothBottomBar();
        
    }


    private void setupSmoothBottomBar() {
        // Create bottomNavigationView
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.menu);
        Menu menu = popupMenu.getMenu();
        bottomNavigationView.setElevation(4);
        bottomNavigationView.setupWithNavController(menu, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

}