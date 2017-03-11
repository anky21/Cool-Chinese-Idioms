package me.anky.coolchineseidioms.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import me.anky.coolchineseidioms.HomeFragment;

/**
 * Created by Anky An on 5/03/2017.
 * anky25@gmail.com
 */

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, WidgetIntentService.class));
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, WidgetIntentService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(HomeFragment.ACTION_DATA_UPDATED.equals(intent.getAction())){
            context.startService(new Intent(context, WidgetIntentService.class));
        }
    }
}

