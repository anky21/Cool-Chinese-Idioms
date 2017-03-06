package me.anky.coolchineseidioms;

import android.os.AsyncTask;

/**
 * Created by Anky An on 20/02/2017.
 * anky25@gmail.com
 */

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    public OnTaskCompleted listener = null;

    @Override
    protected String doInBackground(Void... params) {

    }

    public EndpointsAsyncTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onTaskCompleted(result);
    }
}