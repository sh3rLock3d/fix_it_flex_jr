package com.example.fixitflexjr.gameUtil;

import android.media.MediaPlayer;

import com.example.fixitflexjr.PlayActivity;
import com.example.fixitflexjr.R;

public class ActionSounds {
    public static MediaPlayer jumpSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);

    static {
        {
            jumpSound.setVolume(100, 100);
            jumpSound.setLooping(false);
        }
    }

    public static MediaPlayer fixSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.fixsound);

    static {
        {
            fixSound.setVolume(100, 100);
            fixSound.setLooping(false);
        }
    }

    public static MediaPlayer smashSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.smashsound);

    static {
        {
            smashSound.setVolume(100, 100);
            smashSound.setLooping(false);
        }
    }

    public static MediaPlayer loseLifeSound = MediaPlayer.create(PlayActivity.getInstance(), R.raw.loselifesound);

    static {
        {
            loseLifeSound.setVolume(100, 100);
            loseLifeSound.setLooping(false);
        }
    }


}
