package me.anky.coolchineseidioms;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Anky An on 25/02/2017.
 * anky25@gmail.com
 */

public class IdiomCollectionContract {
    private IdiomCollectionContract(){}

    public static final String CONTENT_AUTHORITY = "me.anky.coolchineseidioms";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_IDIOMS = "idioms";

    public static final class IdiomCollectionEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_IDIOMS);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IDIOMS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_IDIOMS;

        // Name of the database table for a list of idioms
        public final static String TABLE_NAME = "idioms";

        // Unique ID number for the idiom
        public final static String _ID = BaseColumns._ID;

        // Idiom in simplified Chinese
        public final static String COLUMN_IDIOM = "idiom";

        // Idiom in traditional Chinese
        public final static String COLUMN_TRADITIONAL = "traditional";

        // Difficulty level of the idiom
        public final static String COLUMN_LEVEL = "level";

        // Pinyin1: pronunciation with the tones
        public final static String COLUMN_PINYIN1 = "pinyin1";

        // Pinyin2: pronunciation with no tones
        public final static String COLUMN_PINYIN2 = "pinyin2";

        // Translation in English
        public final static String COLUMN_TRANSLATION = "translation";

        // Explanation in Chinese
        public final static String COLUMN_EXPLANATION = "explanation";

        // Explanation in English
        public final static String COLUMN_EXPLANATION_ENG = "explanation_english";

        // Use frequency of the idiom
        public final static String COLUMN_FREQUENCY = "frequency";

        // Sample sentence 1 in Chinese
        public final static String COLUMN_EXAMPLE1 = "example_1";

        // Sample sentence 1 in English
        public final static String COLUMN_EXAMPLE1_ENG = "example_1_eng";

        // Sample sentence 1 audio file
        public final static String COLUMN_EXAMPLE1_AUDIO = "eg1_audio";

        // Sample sentence 2 in Chinese
        public final static String COLUMN_EXAMPLE2 = "example_2";

        // Sample sentence 2 in English
        public final static String COLUMN_EXAMPLE2_ENG = "example_2_eng";

        // Sample sentence 2 audio file
        public final static String COLUMN_EXAMPLE2_AUDIO = "eg2_audio";

        // Sample sentence 3 in Chinese
        public final static String COLUMN_EXAMPLE3 = "example_3";

        // Sample sentence 3 in English
        public final static String COLUMN_EXAMPLE3_ENG = "example_3_eng";

        // Sample sentence 3 audio file
        public final static String COLUMN_EXAMPLE3_AUDIO = "eg3_audio";

        // Idiom category: contain numbers
        public final static String COLUMN_CONTAIN_NUMBERS = "contain_numbers";

        // Idiom category: contain animals
        public final static String COLUMN_CONTAIN_ANIMALS = "contain_animals";

        // Idiom category: related to seasons
        public final static String COLUMN_SEASONS = "about_seasons";

        // Audio file name
        public final static String COLUMN_AUDIO_FILE = "audio_file";

        // Youtube video url
        public final static String COLUMN_YOUTUBE_URL = "youtube_url";

        public static Uri buildIdiomUriWithId(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriWithStringId(String id){
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
