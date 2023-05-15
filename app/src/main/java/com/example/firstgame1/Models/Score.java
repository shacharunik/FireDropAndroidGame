package com.example.firstgame1.Models;

public class Score {
    private int score;
    private double longitude, latitude;

    public Score(int score, double[] coord) {
        this.score = score;
        this.latitude = coord[0];
        this.longitude = coord[1];
    }

    public int getScore() {
        return score;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
