package com.example.firstgame1.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.firstgame1.R;

public class AudioManager {

    private static AudioManager instance;
    private Context context;

    private MediaPlayer raw_WAV_fire, raw_WAV_drop;


    private AudioManager(Context context) {
        this.context = context;
        this.raw_WAV_fire =  MediaPlayer.create(context, R.raw.fire);
        this.raw_WAV_drop =  MediaPlayer.create(context, R.raw.drop);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AudioManager(context);

        }
    }
    public void PlayFire() {
        raw_WAV_fire.start();
    }
    public void PlayDrop() {
        raw_WAV_drop.start();
    }

    public static AudioManager getInstance() {
        return instance;
    }


}
