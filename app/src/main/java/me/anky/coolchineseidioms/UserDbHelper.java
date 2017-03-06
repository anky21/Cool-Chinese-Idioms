package me.anky.coolchineseidioms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.anky.coolchineseidioms.UserContract.*;

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
        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + FavouritesEntry.TABLE_NAME + " (" +
                FavouritesEntry._ID + " INTEGER PRIMARY KEY," +
                FavouritesEntry.COLUMN_FAVORT_ID + " TEXT UNIQUE NOT NULL, " +
                FavouritesEntry.COLUMN_FAVORT_IDIOM + " TEXT NOT NULL, " +
                FavouritesEntry.COLUMN_FAVORT_AUDIO + " TEXT NOT NULL " +
                " );";

        final String SQL_CREATE_DAILYIDIOM_TABLE = "CREATE TABLE " + DailyIdiomMEntry.TABLE_NAME + " (" +
                DailyIdiomMEntry._ID + " INTEGER PRIMARY KEY," +
                DailyIdiomMEntry.COLUMN_DAILY_IDIOM_ID + " TEXT UNIQUE NOT NULL, " +
                DailyIdiomMEntry.COLUMN_DAILY_IDIOM + " TEXT NOT NULL, " +
                DailyIdiomMEntry.COLUMN_DAILY_IDIOM_AUDIO + " TEXT NOT NULL, " +
                DailyIdiomMEntry.COLUMN_TRANSLATION + " TEXT NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_FAVOURITES_TABLE);
        db.execSQL(SQL_CREATE_DAILYIDIOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is created and saved in the user's own device, and won't be upgraded
    }
}
