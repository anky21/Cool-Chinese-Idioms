package me.anky.coolchineseidioms.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.app.TaskStackBuilder;
import android.widget.RemoteViews;

import me.anky.coolchineseidioms.DetailActivity;
import me.anky.coolchineseidioms.idiomdatabase.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.MainActivity;
import me.anky.coolchineseidioms.utilities.MediaPlayerService;
import me.anky.coolchineseidioms.R;
import me.anky.coolchineseidioms.utilities.Utilities;

import static me.anky.coolchineseidioms.userdata.UserContract.DailyIdiomMEntry.CONTENT_URI;

/**
 * Created by Anky An on 6/03/2017.
 * anky25@gmail.com
 */

public class WidgetIntentService extends IntentService {
    public WidgetIntentService(){
        super(WidgetIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                WidgetProvider.class));

        Cursor cursor = getContentResolver().query(CONTENT_URI, Utilities.DAILY_IDIOM_COLUMNS, null, null, null);
        if(cursor == null){
            return;
        }
        if(!cursor.moveToFirst()){
            cursor.close();
            return;
        }
        // Extract data from the cursor
        String idiomId = cursor.getString(Utilities.COL_DAILY_IDIOM_ID);
        String idiomName = cursor.getString(Utilities.COL_DAILY_IDIOM);
        String audioFile = cursor.getString(Utilities.COL_DAILY_IDIOM_AUDIO);
        String idiomTranslation = cursor.getString(Utilities.COL_DAILY_IDIOM_TRANSLATION);
        cursor.close();

        for (int appWidgetId : appWidgetIds) {
            int layoutId = R.layout.widget_layout;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            // Add the data to the RemoteViews
            views.setTextViewText(R.id.idiom_of_the_day, idiomName);
            views.setTextViewText(R.id.idiom_meaning, idiomTranslation);

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, DetailActivity.class);
            Intent upIntent = new Intent(this, MainActivity.class);
            Uri currentIdiomUri = IdiomCollectionEntry.buildUriWithStringId(idiomId);
            launchIntent.setData(currentIdiomUri);
            // Create back stack when launching the Detail activity
            PendingIntent pendingIntent = TaskStackBuilder.create(this)
                    .addNextIntent(upIntent)
                    .addNextIntent(launchIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

            // Create an Intent to play an audio file
            Intent playIntent = new Intent(this, MediaPlayerService.class);
            playIntent.putExtra(Intent.EXTRA_TEXT, audioFile);
            PendingIntent playPendingIntent = PendingIntent.getService(this, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.sound_play_icon_frame, playPendingIntent);

            // Perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
