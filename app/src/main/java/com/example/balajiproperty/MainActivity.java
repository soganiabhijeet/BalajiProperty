package com.example.balajiproperty;

import android.os.Bundle;

import com.example.balajiproperty.sqlite.helper.BalajiPropertyDbHelper;
import com.example.balajiproperty.sqlite.model.MessageSentDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {
    SmsManager smsManager = SmsManager.getDefault();
    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        // enabling action bar app icon and behaving it as toggle button

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);





    }


}