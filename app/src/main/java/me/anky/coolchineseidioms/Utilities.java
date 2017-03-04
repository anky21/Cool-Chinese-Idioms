package me.anky.coolchineseidioms;

import android.media.MediaPlayer;
import android.widget.ImageView;

import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;

/**
 * Created by Anky An on 22/02/2017.
 * anky25@gmail.com
 */

public class Utilities {

    // Set audio play icon
    public static void setAudioPlayIcon(MediaPlayer mediaPlayer, ImageView imageView) {
        boolean isPlaying = mediaPlayer.isPlaying();
        if (isPlaying) {
            imageView.setImageResource(R.drawable.ic_pause_sound);
            imageView.setTag(R.drawable.ic_pause_sound);
        } else {
            imageView.setImageResource(R.drawable.ic_play_sound);
            imageView.setTag(R.drawable.ic_play_sound);
        }
    }

    // A few attributes of interest for a list of idioms
    public static final String[] IDIOM_FEW_COLUMNS = {
            IdiomCollectionEntry._ID,
            IdiomCollectionEntry.COLUMN_IDIOM,
            IdiomCollectionEntry.COLUMN_AUDIO_FILE};

    // All attributes of an idiom
    public static final String[] IDIOM_ALL_COLUMNS = {
            IdiomCollectionEntry._ID,
            IdiomCollectionEntry.COLUMN_IDIOM,
            IdiomCollectionEntry.COLUMN_AUDIO_FILE,
            IdiomCollectionEntry.COLUMN_YOUTUBE_URL,
            IdiomCollectionEntry.COLUMN_TRADITIONAL,
            IdiomCollectionEntry.COLUMN_LEVEL,
            IdiomCollectionEntry.COLUMN_PINYIN1,
            IdiomCollectionEntry.COLUMN_PINYIN2,
            IdiomCollectionEntry.COLUMN_TRANSLATION,
            IdiomCollectionEntry.COLUMN_EXPLANATION,
            IdiomCollectionEntry.COLUMN_EXPLANATION_ENG,
            IdiomCollectionEntry.COLUMN_FREQUENCY,
            IdiomCollectionEntry.COLUMN_EXAMPLE1,
            IdiomCollectionEntry.COLUMN_EXAMPLE1_ENG,
            IdiomCollectionEntry.COLUMN_EXAMPLE1_AUDIO,
            IdiomCollectionEntry.COLUMN_EXAMPLE2,
            IdiomCollectionEntry.COLUMN_EXAMPLE2_ENG,
            IdiomCollectionEntry.COLUMN_EXAMPLE2_AUDIO,
            IdiomCollectionEntry.COLUMN_EXAMPLE3,
            IdiomCollectionEntry.COLUMN_EXAMPLE3_ENG,
            IdiomCollectionEntry.COLUMN_EXAMPLE3_AUDIO,
            IdiomCollectionEntry.COLUMN_CONTAIN_NUMBERS,
            IdiomCollectionEntry.COLUMN_CONTAIN_ANIMALS,
            IdiomCollectionEntry.COLUMN_SEASONS
    };

    // Indices tied to IDIOM_FEW_COLUMNS
    public static final int COL_IDIOM_ID = 0;
    public static final int COL_IDIOM_NAME = 1;
    public static final int COL_AUDIO_FILE = 2;
    public static final int COL_YOUTUBE = 3;
    public static final int COL_TRADITIONAL = 4;
    public static final int COL_LEVEL = 5;
    public static final int COL_PINYIN1 = 6;
    public static final int COL_PINYIN2 = 7;
    public static final int COL_TRANSLATION = 8;
    public static final int COL_EXPLANATION = 9;
    public static final int COL_EXPLANATION_ENG = 10;
    public static final int COL_FREQUENCY = 11;
    public static final int COL_EXAMPLE1 = 12;
    public static final int COL_EXAMPLE1_ENG = 13;
    public static final int COL_EXAMPLE1_AUDIO = 14;
    public static final int COL_EXAMPLE2 = 15;
    public static final int COL_EXAMPLE2_ENG = 16;
    public static final int COL_EXAMPLE2_AUDIO = 17;
    public static final int COL_EXAMPLE3 = 18;
    public static final int COL_EXAMPLE3_ENG = 19;
    public static final int COL_EXAMPLE3_AUDIO = 20;
    public static final int COL_CONTAIN_NUMBERS = 21;
    public static final int COL_CONTAIN_ANIMALS = 22;
    public static final int COL_SEASONS = 23;
}
