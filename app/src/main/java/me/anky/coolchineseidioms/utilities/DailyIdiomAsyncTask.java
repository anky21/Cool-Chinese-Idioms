package me.anky.coolchineseidioms.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import me.anky.randomnumber.CreateRandomNumber;

import static me.anky.coolchineseidioms.idiomdatabase.IdiomCollectionContract.IdiomCollectionEntry;
import static me.anky.coolchineseidioms.userdata.UserContract.DailyIdiomMEntry;

/**
 * Created by Anky An on 20/02/2017.
 * anky25@gmail.com
 */

public class DailyIdiomAsyncTask extends AsyncTask<Context, Void, Void> {
    public OnTaskCompleted listener = null;

    // Projection
    private String[] projection = {
            IdiomCollectionEntry._ID,
            IdiomCollectionEntry.COLUMN_IDIOM,
            IdiomCollectionEntry.COLUMN_AUDIO_FILE,
            IdiomCollectionEntry.COLUMN_TRANSLATION
    };

    // indices
    private static final int COL_IDIOM_ID = 0;
    private static final int COL_IDIOM_NAME = 1;
    private static final int COL_AUDIO_FILE = 2;
    private static final int COL_TRANSLATION = 3;

    @Override
    protected Void doInBackground(Context... params) {
        Context context = params[0];
        // Create a random number (aka idiom ID) depending on the number of idioms in the database
        int rnd = CreateRandomNumber.createRandomNumber();

        ContentResolver contentResolver = context.getContentResolver();
        // Build the uri with this idiom ID
        Uri uri = IdiomCollectionEntry.buildIdiomUriWithId(rnd);
        // Query the database get more info for this idiom
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()) {
            String idiomId = cursor.getString(COL_IDIOM_ID);
            String idiom = cursor.getString(COL_IDIOM_NAME);
            String audioFile = cursor.getString(COL_AUDIO_FILE);
            String translation = cursor.getString(COL_TRANSLATION);

            ContentValues values = new ContentValues();
            values.put(DailyIdiomMEntry.COLUMN_DAILY_IDIOM_ID, idiomId);
            values.put(DailyIdiomMEntry.COLUMN_DAILY_IDIOM, idiom);
            values.put(DailyIdiomMEntry.COLUMN_DAILY_IDIOM_AUDIO, audioFile);
            values.put(DailyIdiomMEntry.COLUMN_TRANSLATION, translation);

            // Empty the database before inserting the new idiom of the day
            contentResolver.delete(DailyIdiomMEntry.CONTENT_URI, null, null);
            contentResolver.insert(DailyIdiomMEntry.CONTENT_URI, values);
        }
        cursor.close();
        return null;
    }

    public DailyIdiomAsyncTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onTaskCompleted();
    }
}
