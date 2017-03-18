package me.anky.coolchineseidioms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private RecordingFragment mRecordingFragment;

    private final static String RECORDING_FRAGMENT = "recording_fragment";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab_voice_recorder)
    FloatingActionButton mFab;

    @BindView(R.id.adView)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // Set a Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set the initial tag for the FAB image
        mFab.setTag(R.drawable.ic_record_white);
        // If the fragment is put in savedInstanceState, then set the FAB accordingly
        RecordingFragment recordingFragment = (RecordingFragment)getSupportFragmentManager()
                .findFragmentByTag(RECORDING_FRAGMENT);
        if(recordingFragment != null){
            mFab.setImageResource(R.drawable.ic_close_white);
            mFab.setTag(R.drawable.ic_close_white);
        }
        mFab.setOnClickListener(mFabOCL);

        // Initialise Google mobile ads SDK
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        mAdView.setContentDescription(getString(R.string.cd_google_ads));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    private View.OnClickListener mFabOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((int) mFab.getTag() == R.drawable.ic_record_white) {
                    // Create a new Fragment to be placed in the activity layout
                    mRecordingFragment = new RecordingFragment();

                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, mRecordingFragment, RECORDING_FRAGMENT)
                            .commit();
                    mFab.setImageResource(R.drawable.ic_close_white);
                    mFab.setTag(R.drawable.ic_close_white);
            }else {
                RecordingFragment recordingFragment = (RecordingFragment)getSupportFragmentManager().findFragmentByTag(RECORDING_FRAGMENT);
                if(recordingFragment != null){
                    getSupportFragmentManager().beginTransaction()
                            .remove(recordingFragment)
                            .commit();
                }
                mFab.setImageResource(R.drawable.ic_record_white);
                mFab.setTag(R.drawable.ic_record_white);
            }
        }
    };
}
