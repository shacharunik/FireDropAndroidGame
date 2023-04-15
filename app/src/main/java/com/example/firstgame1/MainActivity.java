package com.example.firstgame1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton main_BTN_left;
    private FloatingActionButton main_BTN_right;
    private ShapeableImageView[] main_IMG_flames;
    private ShapeableImageView[][] main_IMG_drops;
    private ShapeableImageView[] main_IMG_life;
    private boolean[] nextDropIndex = {false, false, false};
    private int flameLocation = 1;
    private final Handler handler = new Handler();
    private Random rnd = new Random();
    private int nextDrop;
    private int i=4;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        main_BTN_left.setOnClickListener(v -> moveFlame(-1));
        main_BTN_right.setOnClickListener(v -> moveFlame(1));
    }
    protected void onResume() {
        super.onResume();
        handler.postDelayed(movingDrops, 0);
        handler.postDelayed(generateRandomDrops, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(movingDrops);
        handler.removeCallbacks(generateRandomDrops);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.cancel();
        }
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_IMG_flames = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_flame1),
                findViewById(R.id.main_IMG_flame2),
                findViewById(R.id.main_IMG_flame3)
        };
        main_IMG_life = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_life1),
                findViewById(R.id.main_IMG_life2),
                findViewById(R.id.main_IMG_life3)
        };
        main_IMG_drops = new ShapeableImageView[][] {
                {findViewById(R.id.main_IMG_drop00), findViewById(R.id.main_IMG_drop01), findViewById(R.id.main_IMG_drop02)},
                {findViewById(R.id.main_IMG_drop10), findViewById(R.id.main_IMG_drop11), findViewById(R.id.main_IMG_drop12)},
                {findViewById(R.id.main_IMG_drop20), findViewById(R.id.main_IMG_drop21), findViewById(R.id.main_IMG_drop22)},
                {findViewById(R.id.main_IMG_drop30), findViewById(R.id.main_IMG_drop31), findViewById(R.id.main_IMG_drop32)},
                {findViewById(R.id.main_IMG_drop40), findViewById(R.id.main_IMG_drop41), findViewById(R.id.main_IMG_drop42)}
        };
    }
    private void moveFlame(int pos) {
        int newPos = pos+flameLocation;
        if (newPos<0 || newPos>2) return;
        main_IMG_flames[newPos].setVisibility(View.VISIBLE);
        main_IMG_flames[flameLocation].setVisibility(View.INVISIBLE);
        flameLocation = newPos;
    }

    private Runnable movingDrops = new Runnable() {
        @Override
        public void run() {
            for (i = 4; i >= 0; i--) {
                for (int j = 0; j < 3; j++) {
                    if (main_IMG_drops[i][j].getVisibility() == View.VISIBLE && i == 4) {
                        crashCheck(j);
                        main_IMG_drops[4][j].setVisibility(View.INVISIBLE);
                    }else if(main_IMG_drops[i][j].getVisibility() == View.VISIBLE) {
                        main_IMG_drops[i][j].setVisibility(View.INVISIBLE);
                        main_IMG_drops[i+1][j].setVisibility(View.VISIBLE);
                    }
                    if (nextDropIndex[j] && i==0) {
                        main_IMG_drops[0][j].setVisibility(View.VISIBLE);
                        nextDropIndex[j] = false;
                    }

                }
            }
            handler.postDelayed(this, 400);
        }
    };
    private Runnable generateRandomDrops = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(generateRandomDrops, 700);
            nextDrop = rnd.nextInt(3);
            nextDropIndex[nextDrop] = true;

        }

    };

    private void crashCheck(int j) {
        if (count > 2) {
            count = 0;
            for (int i = 0; i < 3; i++) {
                main_IMG_life[i].setVisibility(View.VISIBLE);
            }
        }
        if (main_IMG_flames[j].getVisibility() == View.VISIBLE) {
            Toast.makeText(getApplicationContext(),"Oopsi :(", Toast.LENGTH_SHORT).show();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
            main_IMG_life[count].setVisibility(View.INVISIBLE);
            count++;
        }
    }
}