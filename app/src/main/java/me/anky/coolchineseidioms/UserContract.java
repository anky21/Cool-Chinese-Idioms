package me.anky.coolchineseidioms;

/**
 * Created by Anky An on 5/03/2017.
 * anky25@gmail.com
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Define table and column names for the user database
 */
public class UserContract {

    public static final String CONTENT_AUTHORITY = "me.anky.coolchineseidioms.user";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVOURITES = "favourites";
    public static final String PATH_DAILYIDIOM = "dailyidiom";

    // Table to store favourite idioms
    public static final class FavouritesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVOURITES);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITES;

        // Name of the database table for a list of idioms
        public final static String TABLE_NAME = "favourites";

        public final static String COLUMN_FAVORT_ID = "id";

        public final static String COLUMN_FAVORT_IDIOM = "idiom";

        public final static String COLUMN_FAVORT_AUDIO = "audio";
    }

    // Table to store idiom of the day
    public static final class DailyIdiomMEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DAILYIDIOM);
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAILYIDIOM;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DAILYIDIOM;

        // Name of the database table for a list of idioms
        public final static String TABLE_NAME = "dailyidiom";

        public final static String COLUMN_DAILY_IDIOM_ID = "id";

        public final static String COLUMN_DAILY_IDIOM = "idiom";

        public final static String COLUMN_DAILY_IDIOM_AUDIO = "audio";

        public final static String COLUMN_TRANSLATION = "translation";
    }
}
