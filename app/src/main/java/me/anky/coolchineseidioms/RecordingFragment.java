package me.anky.coolchineseidioms;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.RECORD_AUDIO;

/**
 * A fragment with a Google +1 button.
 */
public class RecordingFragment extends Fragment {
    private static final String LOG_TAG = RecordingFragment.class.getSimpleName();

    private static String mFileName = null;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    public static final int RequestPermissionCode = 1;

    @BindView(R.id.button_record)
    AppCompatButton mButtonRecord;

    @BindView(R.id.button_play)
    AppCompatButton mButtonPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recording, container, false);
        ButterKnife.bind(this, rootView);

        // Record to the external cache directory for visibility
        mFileName = getActivity().getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecord.3gp";

        mButtonRecord.setOnClickListener(mRecordOCL);

        mButtonPlay.setOnClickListener(mPlayOCL);

        return rootView;
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mFileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mCompletionListener);
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void startRecording() {

        // Remove the previous recorded audio if exists
        File file = new File(mFileName);
        if (file.exists()) {
            file.delete();
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    public boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getActivity(),
                RECORD_AUDIO);
        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean RecordPermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            stopPlaying();
            mButtonPlay.setText(R.string.button_play);
            mButtonRecord.setEnabled(true);
        }
    };

    // On click listener for the record button
    private View.OnClickListener mRecordOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String recordButtonText = mButtonRecord.getText().toString();
            if (recordButtonText.equals(getString(R.string.button_record))) {
                if (checkPermission()) {

                    // Record to the external cache directory for visibility
                    mFileName = getActivity().getExternalCacheDir().getAbsolutePath();
                    mFileName += "/audiorecord.3gp";

                    startRecording();
                    mButtonRecord.setText(R.string.button_record_stop);
                    mButtonPlay.setEnabled(false);

                } else {
                    requestPermission();
                }
            } else {
                stopRecording();
                mButtonRecord.setText(R.string.button_record);
                mButtonPlay.setEnabled(true);
            }
        }
    };

    // On click listener for the play button
    private View.OnClickListener mPlayOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String playButtonText = mButtonPlay.getText().toString();
            if (playButtonText.equals(getString(R.string.button_play))) {
                startPlaying();
                mButtonPlay.setText(R.string.button_play_stop);
                mButtonRecord.setEnabled(false);
            } else {
                stopPlaying();
                mButtonPlay.setText(R.string.button_play);
                mButtonRecord.setEnabled(true);
            }
        }
    };
}
