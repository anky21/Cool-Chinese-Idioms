package me.anky.coolchineseidioms;

import android.content.Context;
import android.database.Cursor;
import android.widget.ImageView;

import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.UserContract.DailyIdiomMEntry;
import me.anky.coolchineseidioms.UserContract.FavouritesEntry;

/**
 * Created by Anky An on 22/02/2017.
 * anky25@gmail.com
 */

public class Utilities {

    // Check if an idiom is favourite
    public static boolean isFavourite(Context context, String idiomId) {
        String selection = FavouritesEntry.COLUMN_FAVORT_ID + "=?";
        String[] selectionArgs = new String[]{idiomId};

        Cursor cursor = context.getContentResolver().query(
                FavouritesEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null
        );
        if (cursor != null & cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    // Set the favourite icon
    public static void setFavouriteIcon(boolean isFavourite, ImageView imageView) {
        if (isFavourite) {
            imageView.setImageResource(R.drawable.ic_red_heart);
            imageView.setTag(R.drawable.ic_red_heart);
        } else {
            imageView.setImageResource(R.drawable.ic_heart_outline);
            imageView.setTag(R.drawable.ic_heart_outline);
        }
    }

    // Columns in dailyidiom table
    public static final String[] DAILY_IDIOM_COLUMNS = {
            DailyIdiomMEntry.COLUMN_DAILY_IDIOM_ID,
            DailyIdiomMEntry.COLUMN_DAILY_IDIOM,
            DailyIdiomMEntry.COLUMN_DAILY_IDIOM_AUDIO,
            DailyIdiomMEntry.COLUMN_TRANSLATION};

    // Indices for dailyidiom table
    public static final int COL_DAILY_IDIOM_ID = 0;
    public static final int COL_DAILY_IDIOM = 1;
    public static final int COL_DAILY_IDIOM_AUDIO = 2;
    public static final int COL_DAILY_IDIOM_TRANSLATION = 3;

    // Columns in favourite table
    public static final String[] FAVOURITE_DB_COLUMNS = {
            FavouritesEntry.COLUMN_FAVORT_ID,
            FavouritesEntry.COLUMN_FAVORT_IDIOM,
            FavouritesEntry.COLUMN_FAVORT_AUDIO,
            FavouritesEntry._ID};

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

    // Indices tied to IDIOM_COLLECTION_COLUMNS
    public static final int COL_IDIOM_ID = 0; // Index for both idioms.table and favourites.table
    public static final int COL_IDIOM_NAME = 1; // Index for both idioms.table and favourites.table
    public static final int COL_AUDIO_FILE = 2; // Index for both idioms.table and favourites.table
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
