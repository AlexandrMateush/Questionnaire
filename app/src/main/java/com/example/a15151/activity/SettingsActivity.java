package com.example.a15151.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a15151.MainActivity;
import com.example.a15151.R;
import com.example.a15151.db.ResultDataSource;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ResultDataSource resultDataSource = new ResultDataSource(this);

    private void setAppLocale(String languageCode) {
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        Button buttonLanguage1 = findViewById(R.id.button_language_1);
        Button buttonLanguage2 = findViewById(R.id.button_language_2);
        Button buttonLanguage3 = findViewById(R.id.button_language_3);
        Button buttonBack = findViewById(R.id.button_back);

        Button buttonResetScores = findViewById(R.id.button_reset_progress);


        buttonResetScores.setOnClickListener(v -> resultDataSource.resetAllScores());


        buttonLanguage1.setOnClickListener(v -> {
            setAppLocale("uk");
            recreate();
        });

        buttonLanguage2.setOnClickListener(v -> {
            setAppLocale("en");
            recreate();

        });


        buttonLanguage3.setOnClickListener(v -> {
            setAppLocale("pl");
            recreate();
        });

        buttonBack.setOnClickListener(v -> {
            Intent levelSelectIntent = new Intent(SettingsActivity.this, MainActivity.class);
            levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(levelSelectIntent);
            finishAffinity();
        });
    }



    @Override
    public void onBackPressed() {
        Intent levelSelectIntent = new Intent(SettingsActivity.this, MainActivity.class);
        levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(levelSelectIntent);
        finishAffinity();
    }

}
