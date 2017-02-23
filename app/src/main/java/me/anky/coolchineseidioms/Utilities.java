package me.anky.coolchineseidioms;

import android.media.MediaPlayer;
import android.widget.ImageView;

/**
 * Created by Anky An on 22/02/2017.
 * anky25@gmail.com
 */

public class Utilities {

    // Set audio play icon
    public static void  setAudioPlayIcon(MediaPlayer mediaPlayer,ImageView imageView){
        boolean isPlaying = mediaPlayer.isPlaying();
        if(isPlaying){
            imageView.setImageResource(R.drawable.ic_pause_sound);
            imageView.setTag(R.drawable.ic_pause_sound);
        } else {
            imageView.setImageResource(R.drawable.ic_play_sound);
            imageView.setTag(R.drawable.ic_play_sound);
        }
    }
}
