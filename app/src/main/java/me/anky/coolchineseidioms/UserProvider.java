package me.anky.coolchineseidioms;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.anky.coolchineseidioms.UserContract.DailyIdiomMEntry;

import static android.R.attr.id;
import static me.anky.coolchineseidioms.UserContract.CONTENT_AUTHORITY;
import static me.anky.coolchineseidioms.UserContract.FavouritesEntry;
import static me.anky.coolchineseidioms.UserContract.PATH_DAILYIDIOM;
import static me.anky.coolchineseidioms.UserContract.PATH_FAVOURITES;

/**
 * Created by Anky An on 5/03/2017.
 * anky25@gmail.com
 */

public class UserProvider extends ContentProvider {

    private static final String LOG_TAG = UserProvider.class.getSimpleName();
    private UserDbHelper mDbHelper;

    private static final int FAVOURITES = 100;
    private static final int FAVOURITE_ID = 101;
    private static final int DAILYIDIOM = 300;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_FAVOURITES, FAVOURITES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_FAVOURITES + "/#", FAVOURITE_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_DAILYIDIOM, DAILYIDIOM);
    }


    @Override
    public boolean onCreate() {
        mDbHelper = new UserDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
            selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
                cursor = db.query(FavouritesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case FAVOURITE_ID:
                selection = FavouritesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(FavouritesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DAILYIDIOM:
                cursor = db.query(DailyIdiomMEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVOURITES:
                return FavouritesEntry.CONTENT_LIST_TYPE;
            case FAVOURITE_ID:
                return FavouritesEntry.CONTENT_ITEM_TYPE;
            case DAILYIDIOM:
                return DailyIdiomMEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVOURITES: {
                // Insert the new idiom with the given values
                long id = db.insert(FavouritesEntry.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(uri, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DAILYIDIOM: {
                // Insert the new idiom with the given values
                long id = db.insert(DailyIdiomMEntry.TABLE_NAME, null, contentValues);
                if (id > 0)
                    returnUri = ContentUris.withAppendedId(uri, id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Notify all listeners that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        // Number of rows deleted
        int rowsDeleted;

        switch (match) {
            case FAVOURITES:
                rowsDeleted = db.delete(FavouritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE_ID:
                selection = FavouritesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(FavouritesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DAILYIDIOM:
                rowsDeleted = db.delete(DailyIdiomMEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // Notify all listeners if >= 1 row has been deleted
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case FAVOURITES:
                rowsUpdated = db.update(FavouritesEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            case DAILYIDIOM:
                rowsUpdated = db.update(DailyIdiomMEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
