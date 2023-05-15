package com.example.firstgame1.Activities;

import static com.example.firstgame1.Models.GameControl.COLUMNS;
import static com.example.firstgame1.Models.GameControl.DROP;
import static com.example.firstgame1.Models.GameControl.LOG;
import static com.example.firstgame1.Models.GameControl.ROWS;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstgame1.Location.GpsManager;
import com.example.firstgame1.Models.GameControl;
import com.example.firstgame1.R;
import com.example.firstgame1.Utilities.SignalGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MenuActivity extends AppCompatActivity {

    Button menu_BTN_fast, menu_BTN_slow, menu_BTN_sensor, menu_BTN_records;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        GpsManager.init(this);
        findViews();
        menu_BTN_slow.setOnClickListener(v -> openGameScreen(700,400));
        menu_BTN_fast.setOnClickListener(v -> openGameScreen(400, 200));
        menu_BTN_sensor.setOnClickListener(v -> openSensorGameScreen());
        menu_BTN_records.setOnClickListener(v -> openRecordsScreen());
    }
    private void findViews() {
        menu_BTN_fast = findViewById(R.id.menu_BTN_fast);
        menu_BTN_slow = findViewById(R.id.menu_BTN_slow);
        menu_BTN_sensor = findViewById(R.id.menu_BTN_sensor);
        menu_BTN_records = findViewById(R.id.menu_BTN_records);
    }

    private void openGameScreen(int delayStartDrops, int delaySpeedDrops) {
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.putExtra("delayStartDrops", delayStartDrops);
        gameIntent.putExtra("delaySpeedDrops", delaySpeedDrops);
        gameIntent.putExtra("IsSensor", false);
        startActivity(gameIntent);
        finish();
    }
    private void openSensorGameScreen() {
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.putExtra("IsSensor", true);
        startActivity(gameIntent);
        finish();
    }
    private void openRecordsScreen() {
        Intent gameIntent = new Intent(this, LeaderboardsActivity.class);
        startActivity(gameIntent);
        finish();
    }
}