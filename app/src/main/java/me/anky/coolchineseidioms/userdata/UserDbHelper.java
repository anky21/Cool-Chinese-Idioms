package me.anky.coolchineseidioms.userdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anky An on 5/03/2017.
 * anky25@gmail.com
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "user.db";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold favourite idioms
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + UserContract.FavouritesEntry.TABLE_NAME + " (" +
                UserContract.FavouritesEntry._ID + " INTEGER PRIMARY KEY," +
                UserContract.FavouritesEntry.COLUMN_FAVORT_ID + " TEXT UNIQUE NOT NULL, " +
                UserContract.FavouritesEntry.COLUMN_FAVORT_IDIOM + " TEXT NOT NULL, " +
                UserContract.FavouritesEntry.COLUMN_FAVORT_AUDIO + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_DAILYIDIOM_TABLE = "CREATE TABLE " + UserContract.DailyIdiomMEntry.TABLE_NAME + " (" +
                UserContract.DailyIdiomMEntry._ID + " INTEGER PRIMARY KEY," +
                UserContract.DailyIdiomMEntry.COLUMN_DAILY_IDIOM_ID + " TEXT UNIQUE NOT NULL, " +
                UserContract.DailyIdiomMEntry.COLUMN_DAILY_IDIOM + " TEXT NOT NULL, " +
                UserContract.DailyIdiomMEntry.COLUMN_DAILY_IDIOM_AUDIO + " TEXT NOT NULL, " +
                UserContract.DailyIdiomMEntry.COLUMN_TRANSLATION + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FAVOURITES_TABLE);
        db.execSQL(SQL_CREATE_DAILYIDIOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is created and saved in the user's own device, and won't be upgraded
    }
}
