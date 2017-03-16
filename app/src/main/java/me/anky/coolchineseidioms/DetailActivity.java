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

        mFab.setTag(R.drawable.ic_record_white);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int) mFab.getTag() == R.drawable.ic_record_white) {
                    // Check that the activity is using the layout version with
                    // the fragment_container FrameLayout
                    if (findViewById(R.id.fragment_container) != null) {

                        // Create a new Fragment to be placed in the activity layout
                        mRecordingFragment = new RecordingFragment();

                        // Add the fragment to the 'fragment_container' FrameLayout
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_container, mRecordingFragment)
                                .commit();
                        mFab.setImageResource(R.drawable.ic_close_white);
                        mFab.setTag(R.drawable.ic_close_white);
                    }
                }else {
                    getSupportFragmentManager().beginTransaction()
                            .remove(mRecordingFragment)
                            .commit();
                    mFab.setImageResource(R.drawable.ic_record_white);
                    mFab.setTag(R.drawable.ic_record_white);
                }
            }
        });

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
}
