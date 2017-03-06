package me.anky.coolchineseidioms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.UserContract.DailyIdiomMEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnTaskCompleted {

    // Handles playback of all the sound files
    private MediaPlayer mediaPlayer;

    // Handles audio focus when playing a sound file
    private AudioManager audioManager;

    private int length = 0;

    private String mDailyIdiomId;

    private String mDailyIdiomAudio;

    @BindView(R.id.idiom_of_the_day)
    TextView mIdiomOfTheDay;

    @BindView(R.id.idiom_meaning)
    TextView mIdiomTranslation;

    @BindView(R.id.card_view)
    CardView cardView;

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

        // Get idiom of the day
        new DailyIdiomAsyncTask(this).execute(getContext());

        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        soundPlayIcon.setTag(R.drawable.ic_play_sound);
        // Set up OnClickListener for sound play button
        soundPlayIconFrame.setOnClickListener(mSoundPlayIconClickListener);

        // Set up click actions for courses
        commonIdiomsCourse.setOnClickListener(mCommonIdiomsClickListener);
        beginnerLevelCourse.setOnClickListener(mBeginnerLevelClickListener);
        intermediateLevelCourse.setOnClickListener(mIntermediateLevelClickListener);
        advancedLevelCoruse.setOnClickListener(mAdvancedLevelClickListener);
        allIdiomsCourse.setOnClickListener(mAllIdiomsClickListener);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        length = 0;
        releaseMediaPlayer();
    }

    private void setUpIdiomOfTheDay() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(DailyIdiomMEntry.CONTENT_URI,
                Utilities.DAILY_IDIOM_COLUMNS,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            mDailyIdiomId = cursor.getString(Utilities.COL_DAILY_IDIOM_ID);
            String idiomName = cursor.getString(Utilities.COL_DAILY_IDIOM);
            mDailyIdiomAudio = cursor.getString(Utilities.COL_DAILY_IDIOM_AUDIO);
            String idiomTranslation = cursor.getString(Utilities.COL_DAILY_IDIOM_TRANSLATION);
            mIdiomOfTheDay.setText(idiomName);
            mIdiomTranslation.setText(idiomTranslation);

            // Change background colour of the CardView
            cardView.setCardBackgroundColor(Color.WHITE);
            // Click on the card view to see detial view of idiom of the day
            cardView.setOnClickListener(mCardViewOCL);
        }
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

    // Click listener for common idioms course
    private View.OnClickListener mCommonIdiomsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_FREQUENCY + " = 1";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = commonIdiomsCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);
        }
    };

    // Click listener for beginner level course
    private View.OnClickListener mBeginnerLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 0";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = beginnerLevelCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);
        }
    };

    // Click listener for intermediate level course
    private View.OnClickListener mIntermediateLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 1";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = intermediateLevelCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);
        }
    };

    // Click listener for advanced level course
    private View.OnClickListener mAdvancedLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 2";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = advancedLevelCoruse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);
        }
    };

    // Click listener for all idioms course
    private View.OnClickListener mAllIdiomsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String newTitle = allIdiomsCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);
        }
    };

    private View.OnClickListener mSoundPlayIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((int) soundPlayIcon.getTag() == R.drawable.ic_play_sound && length == 0) {
                // Get audio file name base on the daily idiom ID
                int audioId = getActivity().getResources().getIdentifier(mDailyIdiomAudio, "raw",
                        getActivity().getPackageName());
                // Release the MediaPlayer if it currently exists
                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Create and setup the {@link MediaPlayer}
                    mediaPlayer = MediaPlayer.create(getContext(), audioId);

                    // Start playing the audio file
                    mediaPlayer.start();

                    // Change audio icon
                    Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);

                    // Setup a listener to stop and release the media player after playing
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            } else if ((int) soundPlayIcon.getTag() == R.drawable.ic_play_sound && length != 0) {
                // Start from the saved position
                mediaPlayer.seekTo(length);
                mediaPlayer.start();

                // Change audio icon
                Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
            } else if (mediaPlayer.isPlaying()) {
                // Save the current position
                length = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();

                // Change audio icon
                Utilities.setAudioPlayIcon(mediaPlayer, soundPlayIcon);
            }
        }
    };

    // Triggered when user clicks idiom of the day feature
    private View.OnClickListener mCardViewOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            Uri currentIdiomUri = IdiomCollectionEntry.buildUriWithStringId(mDailyIdiomId);
            intent.setData(currentIdiomUri);
            startActivity(intent);
        }
    };

    @Override
    public void onTaskCompleted() {
        // Fetch idiom of the data from the database and show it in the CardView
        setUpIdiomOfTheDay();
    }
}
