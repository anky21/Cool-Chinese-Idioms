package me.anky.coolchineseidioms;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Anky An on 7/03/2017.
 * anky25@gmail.com
 */

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String ACTION_PLAY = "me.anky.coolchineseidioms.PLAY";
    MediaPlayer mMediaPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(Intent.EXTRA_TEXT) && intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
            String audioFile = intent.getStringExtra(Intent.EXTRA_TEXT);
            int audioFileId = getApplicationContext().getResources().getIdentifier(audioFile, "raw",
                    getApplicationContext().getPackageName());
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), audioFileId);
            mMediaPlayer.setOnPreparedListener(this);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Called when media player is ready
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
