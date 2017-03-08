package me.anky.coolchineseidioms;

import android.app.Service;
import android.content.Context;
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
    private static final String LOG_TAG = "testing";
    private static MediaPlayerService serviceInstance;

    MediaPlayer mMediaPlayer = null;
    AudioManager am;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceInstance = this;
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Stop the media player when a new service is requested
        releaseMediaPlayer();
        String audioFile = intent.getStringExtra(Intent.EXTRA_TEXT);
        int audioFileId = getApplicationContext().getResources().getIdentifier(audioFile, "raw",
                getApplicationContext().getPackageName());
        // Initialise the media player
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(audioFileId);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Called when media player is ready
    @Override
    public void onPrepared(MediaPlayer mp) {
        int result = am.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mp.start();
        }
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            // Release its resources
            mMediaPlayer.release();

            // Set the media player back to null.
            mMediaPlayer = null;
        }
    }

    // Triggered when media player completes playing
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
            // Stop the service
            stopSelf();
        }
    };

    /*
    * This listener is triggered whenever the audio focus changes
    * */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                if (mMediaPlayer != null) {
                    // Pause playback and reset player to the start of the file
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (mMediaPlayer != null) {
                    // Resume playback when focus is regained.
                    mMediaPlayer.start();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
}
