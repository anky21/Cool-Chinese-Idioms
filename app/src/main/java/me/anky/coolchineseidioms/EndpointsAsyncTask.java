package me.anky.coolchineseidioms;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import me.anky.coolchineseidioms.backend.myApi.MyApi;

/**
 * Created by Anky An on 20/02/2017.
 * anky25@gmail.com
 */

class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    public OnTaskCompleted listener = null;

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://chineseidioms-159403.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        try {
            return myApiService.fetchIdiom().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public EndpointsAsyncTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onTaskCompleted(result);
    }
}