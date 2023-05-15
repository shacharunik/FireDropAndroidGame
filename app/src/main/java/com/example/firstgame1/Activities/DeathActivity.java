package com.example.firstgame1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstgame1.R;

public class DeathActivity extends AppCompatActivity {

    Button dead_BTN_menu, dead_BTN_scores;
    TextView dead_TXT_meters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead);
        findViews();
        dead_BTN_menu.setOnClickListener(v -> openMenuScreen());
        dead_BTN_scores.setOnClickListener(v -> openScoresScreen());
        Intent prevIntent = getIntent();
        int score = prevIntent.getIntExtra("Score", 0);
        dead_TXT_meters.setText("You Lose with " + score + " meters");
    }
    private void findViews() {
        dead_BTN_menu = findViewById(R.id.dead_BTN_menu);
        dead_BTN_scores = findViewById(R.id.dead_BTN_scores);
        dead_TXT_meters = findViewById(R.id.dead_TXT_meters);
    }

    private void openScoresScreen() {
        Intent gameIntent = new Intent(this, LeaderboardsActivity.class);
        startActivity(gameIntent);
        finish();
    }
    private void openMenuScreen() {
        Intent gameIntent = new Intent(this, MenuActivity.class);
        startActivity(gameIntent);
        finish();
    }
}