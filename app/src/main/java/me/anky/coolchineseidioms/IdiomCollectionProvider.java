package me.anky.coolchineseidioms;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Anky An on 25/02/2017.
 * anky25@gmail.com
 */

public class IdiomCollectionProvider extends ContentProvider {
    private static final int IDIOMS = 100;
    private static final int IDIOM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(IdiomCollectionContract.CONTENT_AUTHORITY, IdiomCollectionContract.PATH_IDIOMS, IDIOMS);

        sUriMatcher.addURI(IdiomCollectionContract.CONTENT_AUTHORITY, IdiomCollectionContract.PATH_IDIOMS + "/#", IDIOM_ID);
    }
    private IdiomCollectionDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new IdiomCollectionDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case IDIOMS:
                cursor = database.query(IdiomCollectionContract.IdiomCollectionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case IDIOM_ID:
                selection = IdiomCollectionContract.IdiomCollectionEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(IdiomCollectionContract.IdiomCollectionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("can't query");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case IDIOMS:
                return IdiomCollectionContract.IdiomCollectionEntry.CONTENT_LIST_TYPE;
            case IDIOM_ID:
                return IdiomCollectionContract.IdiomCollectionEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("unknown");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
