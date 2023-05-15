package com.example.firstgame1.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Comparator;

public class Scores {

    private ArrayList<Score> scores = new ArrayList<>();

    public Scores() {

    }

    public void add(Score score) {
        scores.add(score);
        scores.sort(Comparator.comparingInt(Score::getScore).reversed());
        if (scores.size() > 10) {
            scores.subList(10, scores.size()).clear();
        }
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public ArrayList<Score> getRecords() {
        return scores;
    }
}
