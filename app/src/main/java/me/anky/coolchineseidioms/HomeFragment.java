package me.anky.coolchineseidioms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnTaskCompleted {

    // Handles playback of all the sound files
    private MediaPlayer mediaPlayer;

    // Handles audio focus when playing a sound file
    private AudioManager audioManager;

    private int length = 0;

    // Tags to be sent with an intent
    public static final String COMMON = "common";
    public static final String BEGINNER = "beginner";
    public static final String INTERMEDIATE = "intermediate";
    public static final String ADVANCED = "advanced";

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.idiom_of_the_day)
    TextView idiomTv;

    @BindView(R.id.sound_play_icon_frame)
    FrameLayout soundPlayIconFrame;

    @BindView(R.id.sound_play_icon)
    ImageView soundPlayIcon;

    @BindView(R.id.common_idioms_course)
    TextView commonIdiomsCourse;

    @BindView(R.id.beginner_level_course)
    TextView beginnerLevelCourse;

    @BindView(R.id.intermediate_level_course)
    TextView intermediateLevelCourse;

    @BindView(R.id.advanced_level_course)
    TextView advancedLevelCoruse;

    @BindView(R.id.all_idioms_course)
    TextView allIdiomsCourse;

    private String idiom = "";

    public HomeFragment() {
        // Required empty public constructor
    }

    /*
    * This listener is triggered whenever the audio focus changes
    * */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback and reset player to the start of the file
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                // Change audio icon
                Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback when focus is regained.
                mediaPlayer.start();
                // Change audio icon
                Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
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
            // Reset position to the start
            length = 0;
            // Change audio icon
            Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
            // Release the media player resources.
            releaseMediaPlayer();
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        new EndpointsAsyncTask(this).execute();

        // Change background colour of the CardView
        cardView.setCardBackgroundColor(Color.WHITE);

        soundPlayIcon.setTag(R.drawable.ic_play_sound);

        soundPlayIconFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int)soundPlayIcon.getTag() == R.drawable.ic_play_sound && length == 0) {
                    // Release the MediaPlayer if it currently exists
                    releaseMediaPlayer();
                    int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // Create and setup the {@link MediaPlayer}
                        mediaPlayer = MediaPlayer.create(getContext(), R.raw.duiniutanqin);

                        // Start playing the audio file
                        mediaPlayer.start();

                        // Change audio icon
                        Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);

                        // Setup a listener to stop and release the media player after playing
                        mediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                } else if ((int)soundPlayIcon.getTag() == R.drawable.ic_play_sound && length != 0){
                    // Start from the saved position
                    mediaPlayer.seekTo(length);
                    mediaPlayer.start();

                    // Change audio icon
                    Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
                } else if(mediaPlayer.isPlaying()) {
                    // Save the current position
                    length = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                    // Change audio icon
                    Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
                }
            }
        });


        // Set up the click action for common idioms course
        commonIdiomsCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdiomListActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, COMMON);
                startActivity(intent);
            }
        });

        // Set up the click action for beginner level course
        beginnerLevelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdiomListActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, BEGINNER);
                startActivity(intent);
            }
        });

        // Set up the click action for intermediate level course
        intermediateLevelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdiomListActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, INTERMEDIATE);
                startActivity(intent);
            }
        });

        // Set up the click action for advanced level course
        advancedLevelCoruse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdiomListActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, ADVANCED);
                startActivity(intent);
            }
        });

        // Set up the click action for all idioms course
        allIdiomsCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IdiomListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    public void onTaskCompleted(String result) {
        idiom = result;
        idiomTv.setText(idiom);
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // Change audio icon
        soundPlayIcon.setImageResource(R.drawable.ic_play_sound);
        soundPlayIcon.setTag(R.drawable.ic_play_sound);

        if (mediaPlayer != null) {
            // Release its resources
            mediaPlayer.release();

            // Set the media player back to null.
            mediaPlayer = null;

            // Abandon the audio focus
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
