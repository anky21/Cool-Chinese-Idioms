package me.anky.coolchineseidioms;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Anky An on 9/03/2017.
 * anky25@gmail.com
 */

    public class SchedulingService extends IntentService implements OnTaskCompleted {
        public SchedulingService() {
            super("SchedulingService");
        }

        @Override
        public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
            new DailyIdiomAsyncTask(this).execute(getApplication());
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
        }

        @Override
        public void onTaskCompleted() {
            // Update the App Widget when data is written into the database
            HomeFragment.updateWidgets(getApplicationContext());
        }
    }

