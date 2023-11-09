package com.mishal.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView numbers, family, colors, phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = findViewById(R.id.numbers);
        family = findViewById(R.id.family);
        colors = findViewById(R.id.colors);
        phrases = findViewById(R.id.phrases);

        numbers.setOnClickListener(v -> {

            Intent intent = new
                    Intent(MainActivity.this, Numbers_Actvity.class);

            startActivity(intent);
        });

        family.setOnClickListener(v -> {

            Intent intent = new
                    Intent(MainActivity.this, Family_Activity.class);

            startActivity(intent);
        });

        colors.setOnClickListener(v -> {

            Intent intent = new
                    Intent(MainActivity.this, Colors_Activity.class);

            startActivity(intent);
        });

        phrases.setOnClickListener(v -> {

            Intent intent = new
                    Intent(MainActivity.this, Phrases_Activity.class);

            startActivity(intent);
        });


    }
}