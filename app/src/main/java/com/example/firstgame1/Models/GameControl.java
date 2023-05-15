package com.example.firstgame1.Models;

import com.example.firstgame1.Utilities.MySPv3;
import com.google.gson.Gson;

public class GameControl {
    public static final int LIVES = 3;
    public static final int ROWS = 8;
    public static final int COLUMNS = 5;
    public static final String DROP = "DROP";
    public static final String LOG = "LOG";
    public boolean sensor;
    private int delaySpeedDrops;
    private int delayStartDrops;

    private int odemeter;
    private int hearts;

    public GameControl(int dropsDelay, int startDelay) {
        delayStartDrops = startDelay;
        delaySpeedDrops = dropsDelay;
        hearts = LIVES;
        odemeter = 0;
        sensor = false;
    }

    public int getDelaySpeedDrops() {
        return delaySpeedDrops;
    }

    public int getDelayStartDrops() {
        return delayStartDrops;
    }
    public int getOdemeter() {
        return odemeter;
    }

    public int getHearts() {
        return hearts;
    }

    public int addOdemeter(int meters) {
        odemeter += meters;
        return odemeter;
    }

    public boolean hurt() {
        hearts--;
        return hearts == 0;
    }
    public void death(double[] coord) {

        String str_json = MySPv3.getInstance().getString("Scores","");
        Scores scores_json = new Gson().fromJson(str_json, Scores.class);
        if (scores_json == null) {
            scores_json = new Scores();
            scores_json.add(new Score(odemeter, coord));
        }
        else {
            scores_json.add(new Score(odemeter, coord));
        }
        MySPv3.getInstance().putString("Scores", scores_json.toJson());
    }
}
