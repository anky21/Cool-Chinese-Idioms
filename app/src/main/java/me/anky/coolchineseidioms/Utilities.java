package me.anky.coolchineseidioms;

import android.media.MediaPlayer;

/**
 * Created by Anky An on 22/02/2017.
 * anky25@gmail.com
 */

public class Utilities {

    /**
     * Clean up the media player by releasing its resources.
     */
    public static void releaseMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            // Release its resources
            mediaPlayer.release();
        }
    }
}
