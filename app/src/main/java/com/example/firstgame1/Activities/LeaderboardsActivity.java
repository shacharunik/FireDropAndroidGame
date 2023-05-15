package com.example.firstgame1.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.firstgame1.Fragments.LeaderboardFragment;
import com.example.firstgame1.Fragments.GMapFragment;
import com.example.firstgame1.Interfaces.CallbackMark;
import com.example.firstgame1.R;

public class LeaderboardsActivity extends AppCompatActivity {

    private LeaderboardFragment leaderboardFragment;
    private GMapFragment GMapFragment;
    private Button score_BTN_menu;
    private CallbackMark callbackMark = new CallbackMark() {
        @Override
        public void markPoint(double lat, double lon) {
            GMapFragment.markOnMap(lat, lon);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
        findViews();
        initFragments();
        beginTransactions();

        score_BTN_menu.setOnClickListener(v -> openMenuScreen());

    }

    private void findViews() {
        score_BTN_menu = findViewById(R.id.leaderboard_BTN_menu);
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.leaderboard_FRM_list, leaderboardFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.leaderboard_FRM_gmap, GMapFragment).commit();
    }

    private void initFragments() {
        leaderboardFragment = new LeaderboardFragment();
        leaderboardFragment.setCallbackMark(callbackMark);
        GMapFragment = new GMapFragment();
    }

    private void openMenuScreen() {
        Intent gameIntent = new Intent(this, MenuActivity.class);
        startActivity(gameIntent);
        finish();
    }
}