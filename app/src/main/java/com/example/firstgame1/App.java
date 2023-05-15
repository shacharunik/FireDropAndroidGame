package com.example.firstgame1;

import android.app.Application;

import com.example.firstgame1.Location.GpsManager;
import com.example.firstgame1.Utilities.AudioManager;
import com.example.firstgame1.Utilities.MySPv3;
import com.example.firstgame1.Utilities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySPv3.init(this);
        SignalGenerator.init(this);
        AudioManager.init(this);


    }
}
