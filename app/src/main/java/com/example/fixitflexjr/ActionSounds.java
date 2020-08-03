package com.example.fixitflexjr;

import android.media.MediaPlayer;

public class ActionSounds {
    public static MediaPlayer jumpSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);{
        {
            jumpSound.setVolume(100, 100);
            jumpSound.setLooping(false);
        }
    }

    public static MediaPlayer fixSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.fixsound);{
        {
            fixSound.setVolume(100, 100);
            fixSound.setLooping(false);
        }
    }

    public static MediaPlayer smashSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.smashsound);{
        {
            smashSound.setVolume(100, 100);
            smashSound.setLooping(false);
        }
    }

    public static MediaPlayer loseLifeSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.loselifesound);{
        {
            loseLifeSound.setVolume(100, 100);
            loseLifeSound.setLooping(false);
        }
    }



}
