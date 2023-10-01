package com.example.a15151;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a15151.activity.LevelSelectActivity;
import com.example.a15151.activity.SettingsActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Button buttonPlay = findViewById(R.id.button_play);
        Button buttonSettings = findViewById(R.id.button_settings);
        Button buttonExit = findViewById(R.id.button_exit);


        buttonPlay.setOnClickListener(v -> {
            Intent gameIntent = new Intent(MainActivity.this, LevelSelectActivity.class);
            startActivity(gameIntent);
        });

        buttonSettings.setOnClickListener(v -> {
            Intent settingsIntent;
            settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        buttonExit.setOnClickListener(v -> finishAffinity());
    }


}
