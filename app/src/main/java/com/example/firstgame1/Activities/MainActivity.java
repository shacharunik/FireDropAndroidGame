package com.example.firstgame1.Activities;

import static android.view.View.INVISIBLE;
import static com.example.firstgame1.Models.GameControl.COLUMNS;
import static com.example.firstgame1.Models.GameControl.DROP;
import static com.example.firstgame1.Models.GameControl.LOG;
import static com.example.firstgame1.Models.GameControl.ROWS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.firstgame1.Interfaces.StepCallback;
import com.example.firstgame1.Location.GpsManager;
import com.example.firstgame1.Models.Flame;
import com.example.firstgame1.Models.GameControl;
import com.example.firstgame1.R;
import com.example.firstgame1.Utilities.AudioManager;
import com.example.firstgame1.Utilities.SignalGenerator;
import com.example.firstgame1.Utilities.StepDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton main_BTN_left;
    private FloatingActionButton main_BTN_right;
    private ShapeableImageView[] main_IMG_flames;
    private ShapeableImageView[][] main_IMG_drops;
    private ShapeableImageView[] main_IMG_life;
    private TextView main_TXT_odemeter;
    private boolean[] nextDropIndex = {false, false, false, false, false};
    private Flame flame;
    private final Handler handler = new Handler();
    private Random rnd = new Random();
    private int nextDrop;
    private boolean sensorGame;
    private boolean gotoDeathScreen = true;
    private GameControl gameControl;
    private StepDetector stepDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        getVarFromMenuScreen();
        if (!sensorGame) {
            main_BTN_left.setOnClickListener(v -> moveFlame(-1));
            main_BTN_right.setOnClickListener(v -> moveFlame(1));
        } else {
            main_BTN_left.setVisibility(INVISIBLE);
            main_BTN_right.setVisibility(INVISIBLE);

            stepDetector = new StepDetector(this, new StepCallback() {

                @Override
                public void stepX() {
                    int x = stepDetector.getX();
                    if (x > -5 && x < 5) return;
                    if (x < -5) {
                        moveFlame(1);
                    }
                    else if (x > 5) {
                        moveFlame(-1);
                    }
                }

                @Override
                public void stepY() {

                }

                @Override
                public void stepZ() {

                }
            });
        }

        flame = new Flame(2);
    }

    private void getVarFromMenuScreen() {
        Intent prevIntent = getIntent();
        int delaySpeedDrops = prevIntent.getIntExtra("delaySpeedDrops", 400);
        int delayStartDrops = prevIntent.getIntExtra("delayStartDrops", 700);
        sensorGame = prevIntent.getBooleanExtra("IsSensor", false);
        gameControl = new GameControl(delaySpeedDrops, delayStartDrops);
    }

    protected void onResume() {
        super.onResume();
        handler.postDelayed(movingDrops, 0);
        handler.postDelayed(generateRandomDrops, 0);
        if (sensorGame)
            stepDetector.start();
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
        if (sensorGame)
            stepDetector.stop();
    }

    private void findViews() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_TXT_odemeter = findViewById(R.id.main_TXT_odemeter);

        main_IMG_flames = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_flame1),
                findViewById(R.id.main_IMG_flame2),
                findViewById(R.id.main_IMG_flame3),
                findViewById(R.id.main_IMG_flame4),
                findViewById(R.id.main_IMG_flame5)
        };
        main_IMG_life = new ShapeableImageView[] {
                findViewById(R.id.main_IMG_life1),
                findViewById(R.id.main_IMG_life2),
                findViewById(R.id.main_IMG_life3)
        };
        main_IMG_drops = new ShapeableImageView[][] {
                {findViewById(R.id.main_IMG_drop00), findViewById(R.id.main_IMG_drop01), findViewById(R.id.main_IMG_drop02), findViewById(R.id.main_IMG_drop03), findViewById(R.id.main_IMG_drop04)},
                {findViewById(R.id.main_IMG_drop10), findViewById(R.id.main_IMG_drop11), findViewById(R.id.main_IMG_drop12), findViewById(R.id.main_IMG_drop13), findViewById(R.id.main_IMG_drop14)},
                {findViewById(R.id.main_IMG_drop20), findViewById(R.id.main_IMG_drop21), findViewById(R.id.main_IMG_drop22), findViewById(R.id.main_IMG_drop23), findViewById(R.id.main_IMG_drop24)},
                {findViewById(R.id.main_IMG_drop30), findViewById(R.id.main_IMG_drop31), findViewById(R.id.main_IMG_drop32), findViewById(R.id.main_IMG_drop33), findViewById(R.id.main_IMG_drop34)},
                {findViewById(R.id.main_IMG_drop40), findViewById(R.id.main_IMG_drop41), findViewById(R.id.main_IMG_drop42), findViewById(R.id.main_IMG_drop43), findViewById(R.id.main_IMG_drop44)},
                {findViewById(R.id.main_IMG_drop50), findViewById(R.id.main_IMG_drop51), findViewById(R.id.main_IMG_drop52), findViewById(R.id.main_IMG_drop53), findViewById(R.id.main_IMG_drop54)},
                {findViewById(R.id.main_IMG_drop60), findViewById(R.id.main_IMG_drop61), findViewById(R.id.main_IMG_drop62), findViewById(R.id.main_IMG_drop63), findViewById(R.id.main_IMG_drop64)},
                {findViewById(R.id.main_IMG_drop70), findViewById(R.id.main_IMG_drop71), findViewById(R.id.main_IMG_drop72), findViewById(R.id.main_IMG_drop73), findViewById(R.id.main_IMG_drop74)},

        };
    }
    private void moveFlame(int pos) {
        int newPos = pos+flame.getFlameLocation();
        if (newPos<0 || newPos>COLUMNS-1) return;
        main_IMG_flames[newPos].setVisibility(View.VISIBLE);
        main_IMG_flames[flame.getFlameLocation()].setVisibility(View.INVISIBLE);
        flame.setFlameLocation(newPos);
        crashCheck();
    }

    private Runnable movingDrops = new Runnable() {
        @Override
        public void run() {
            main_TXT_odemeter.setText("" + gameControl.addOdemeter(1));
            for (int i = ROWS-1; i >= 0; i--) {
                for (int j = 0; j < COLUMNS; j++) {
                    ShapeableImageView obj = main_IMG_drops[i][j];
                    if (obj.getVisibility() == View.VISIBLE && i == ROWS-1) {
                        obj.setVisibility(View.INVISIBLE);
                    }
                    else if(obj.getVisibility() == View.VISIBLE) {
                        obj.setVisibility(View.INVISIBLE);
                        obj = main_IMG_drops[i+1][j];
                        Drawable lastDraw = main_IMG_drops[i][j].getDrawable();
                        String lastTag = (String) main_IMG_drops[i][j].getTag();
                        obj.setImageDrawable(lastDraw);
                        obj.setTag(lastTag);
                        obj.setVisibility(View.VISIBLE);
                    }
                    if (nextDropIndex[j] && i==0) {
                        if (rnd.nextInt(COLUMNS) == 0) {
                            main_IMG_drops[0][j].setImageResource(R.drawable.log);
                            obj.setTag(LOG);
                        } else {
                            main_IMG_drops[0][j].setImageResource(R.drawable.drop1);
                            obj.setTag(DROP);
                        }
                        main_IMG_drops[0][j].setVisibility(View.VISIBLE);
                        nextDropIndex[j] = false;
                    }

                }
            }
            crashCheck();
            handler.postDelayed(this, gameControl.getDelaySpeedDrops());
        }
    };
    private Runnable generateRandomDrops = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(generateRandomDrops, gameControl.getDelayStartDrops());
            nextDrop = rnd.nextInt(COLUMNS);
            nextDropIndex[nextDrop] = true;
        }

    };

    private void crashCheck() {
        ShapeableImageView obj = main_IMG_drops[ROWS-1][flame.getFlameLocation()];
        if (obj.getTag() != null && obj.getVisibility() == View.VISIBLE) {
            obj.setVisibility(View.INVISIBLE);
            if (obj.getTag().equals(DROP)) {
                crashDrop();
            }
            else if (obj.getTag().equals(LOG)){
                earnLog();
            }
        }
    }

    private void crashDrop() {
        SignalGenerator.getInstance().showToast("oopsi", 500);
        SignalGenerator.getInstance().vibrate(50);
        AudioManager.getInstance().PlayDrop();
        if (gameControl.getHearts() - 1 <= 0)
            death();
        else {
            gameControl.hurt();
            main_IMG_life[2 - gameControl.getHearts()].setVisibility(INVISIBLE);
        }
    }
    private void earnLog() {
        gameControl.addOdemeter(5);
        AudioManager.getInstance().PlayFire();
        SignalGenerator.getInstance().showToast("yayyy!!! loggggggg logggg n", 500);
        SignalGenerator.getInstance().vibrate(50);
    }
    public void death() {
        if (gotoDeathScreen) {
            gameControl.death(GpsManager.getInstance().getCoord());
            openDeadScreen(gameControl.getOdemeter());
            gotoDeathScreen = false;
        }
    }
    private void openDeadScreen(int score) {
        Intent deadIntent = new Intent(this, DeathActivity.class);
        deadIntent.putExtra("Score", score);
        startActivity(deadIntent);
        finish();
    }

}