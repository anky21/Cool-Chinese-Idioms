package me.anky.coolchineseidioms;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Anky An on 7/03/2017.
 * anky25@gmail.com
 */

public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String ACTION_PLAY = "me.anky.coolchineseidioms.PLAY";
    MediaPlayer mMediaPlayer = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (startId == 1) {
            String audioFile = intent.getStringExtra(Intent.EXTRA_TEXT);
            int audioFileId = getApplicationContext().getResources().getIdentifier(audioFile, "raw",
                    getApplicationContext().getPackageName());
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                AssetFileDescriptor afd = getResources().openRawResourceFd(audioFileId);
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Called when media player is ready
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
