package me.anky.coolchineseidioms;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Anky An on 28/02/2017.
 * anky25@gmail.com
 */

public class IdiomListCursorAdapter extends CursorAdapter {
    // Handles playback of all the sound files
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    MainViewHolder mainViewHolder;

    public IdiomListCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    public class MainViewHolder {
        FrameLayout soundPlayButton;
        TextView idiomName;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        mainViewHolder = new MainViewHolder();

        rootView.setTag(mainViewHolder);

        return rootView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Read the idiom attributes from the Cursor
        String idiomName = cursor.getString(Utilities.COL_IDIOM_NAME);
        final String audioFile = cursor.getString(Utilities.COL_AUDIO_FILE);
        final int audioId = context.getResources().getIdentifier(audioFile, "raw", context.getPackageName());

        // Set the views for the ViewHolder
        mainViewHolder.soundPlayButton = (FrameLayout) view.findViewById(R.id.sound_play_icon_frame);
        mainViewHolder.idiomName = (TextView) view.findViewById(R.id.idiom_name);

        // Update TextViews with the attributes for the current idiom
        mainViewHolder.idiomName.setText(idiomName);
        mainViewHolder.soundPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Release the MediaPlayer if it currently exists
                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Create and setup the {@link MediaPlayer}
                    mediaPlayer = MediaPlayer.create(context, audioId);

                    // Start playing the audio file
                    mediaPlayer.start();

                    // Setup a listener to stop and release the media player after playing
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    public void releaseMediaPlayer() {

        if (mediaPlayer != null) {
            // Release its resources
            mediaPlayer.release();

            // Set the media player back to null.
            mediaPlayer = null;
        }
    }

    /*
    * This listener is triggered whenever the audio focus changes
    * */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                if (mediaPlayer != null) {
                    // Pause playback and reset player to the start of the file
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (mediaPlayer != null) {
                    // Resume playback when focus is regained.
                    mediaPlayer.start();
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * Triggered when the {@link MediaPlayer} has completed playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Release the media player resources.
            releaseMediaPlayer();

            // Abandon the audio focus
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    };
}
