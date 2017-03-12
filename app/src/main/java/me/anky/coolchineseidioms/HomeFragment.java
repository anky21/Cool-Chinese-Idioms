package me.anky.coolchineseidioms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anky.coolchineseidioms.idiomdatabase.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.userdata.UserContract.DailyIdiomMEntry;
import me.anky.coolchineseidioms.alarm.AlarmReceiver;
import me.anky.coolchineseidioms.utilities.DailyIdiomAsyncTask;
import me.anky.coolchineseidioms.utilities.MediaPlayerService;
import me.anky.coolchineseidioms.utilities.OnTaskCompleted;
import me.anky.coolchineseidioms.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnTaskCompleted {
    public static final String ACTION_DATA_UPDATED = "me.anky.coolchineseidioms.ACTION_DATA_UPDATED";

    AlarmReceiver alarm = new AlarmReceiver();

    private String mDailyIdiomId;

    private String mDailyIdiomAudio;

    @BindView(R.id.idiom_of_the_day)
    TextView mIdiomOfTheDay;

    @BindView(R.id.idiom_meaning)
    TextView mIdiomTranslation;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.sound_play_icon_frame)
    FrameLayout soundPlayIconFrame;

    @BindView(R.id.sound_play_icon)
    ImageView soundPlayIcon;

    @BindView(R.id.common_idioms_course)
    TextView commonIdiomsCourse;

    @BindView(R.id.beginner_level_course)
    TextView beginnerLevelCourse;

    @BindView(R.id.intermediate_level_course)
    TextView intermediateLevelCourse;

    @BindView(R.id.advanced_level_course)
    TextView advancedLevelCourse;

    @BindView(R.id.all_idioms_course)
    TextView allIdiomsCourse;

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        // When the app runs for the first time (the database is empty), run the AsyncTask
        // and set the alarm
        Cursor cursor = getContext().getContentResolver()
                .query(DailyIdiomMEntry.CONTENT_URI, null, null, null, null);
        if (!cursor.moveToFirst()) {
            new DailyIdiomAsyncTask(this).execute(getContext());
            alarm.setAlarm(getContext());
        }
        cursor.close();

        soundPlayIcon.setTag(R.drawable.ic_play_sound);
        // Set up OnClickListener for sound play button
        soundPlayIconFrame.setOnClickListener(mSoundPlayIconClickListener);

        // Set up click actions for courses
        commonIdiomsCourse.setOnClickListener(mCommonIdiomsClickListener);
        beginnerLevelCourse.setOnClickListener(mBeginnerLevelClickListener);
        intermediateLevelCourse.setOnClickListener(mIntermediateLevelClickListener);
        advancedLevelCourse.setOnClickListener(mAdvancedLevelClickListener);
        allIdiomsCourse.setOnClickListener(mAllIdiomsClickListener);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fetch idiom of the day data from the database and show it on the screen
        setUpIdiomOfTheDay();
    }

    @Override
    public void onTaskCompleted() {
        // Fetch idiom of the day data from the database and show it on the screen
        setUpIdiomOfTheDay();

        // Update the App Widget when data is written into the database
        updateWidgets(getContext());
    }

    private void setUpIdiomOfTheDay() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(DailyIdiomMEntry.CONTENT_URI,
                Utilities.DAILY_IDIOM_COLUMNS,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            mDailyIdiomId = cursor.getString(Utilities.COL_DAILY_IDIOM_ID);
            String idiomName = cursor.getString(Utilities.COL_DAILY_IDIOM);
            mDailyIdiomAudio = cursor.getString(Utilities.COL_DAILY_IDIOM_AUDIO);
            String idiomTranslation = cursor.getString(Utilities.COL_DAILY_IDIOM_TRANSLATION);
            mIdiomOfTheDay.setText(idiomName);
            mIdiomTranslation.setText(idiomTranslation);

            // Change background colour of the CardView
            cardView.setCardBackgroundColor(Color.WHITE);
            // Click on the card view to see detail view of idiom of the day
            cardView.setOnClickListener(mCardViewOCL);
        }
        cursor.close();
    }

    // Update app widgets
    public static void updateWidgets(Context context) {
        // Setting the package ensures that only components in this app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }

    // Click listener for common idioms course
    private View.OnClickListener mCommonIdiomsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_FREQUENCY + " = 1";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = commonIdiomsCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    // Click listener for beginner level course
    private View.OnClickListener mBeginnerLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 0";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = beginnerLevelCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    // Click listener for intermediate level course
    private View.OnClickListener mIntermediateLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 1";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = intermediateLevelCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    // Click listener for advanced level course
    private View.OnClickListener mAdvancedLevelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String stringExtra = IdiomCollectionEntry.COLUMN_LEVEL + " = 2";
            intent.putExtra(Intent.EXTRA_TEXT, stringExtra);
            String newTitle = advancedLevelCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    // Click listener for all idioms course
    private View.OnClickListener mAllIdiomsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), IdiomListActivity.class);
            String newTitle = allIdiomsCourse.getText().toString();
            intent.putExtra(Intent.EXTRA_TITLE, newTitle);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    // Triggered when user clicks on the sound button
    private View.OnClickListener mSoundPlayIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Start MediaPlayerService to play the audio
            Intent playIntent = new Intent(getContext(), MediaPlayerService.class);
            playIntent.putExtra(Intent.EXTRA_TEXT, mDailyIdiomAudio);
            getActivity().startService(playIntent);
        }
    };

    // Triggered when user clicks idiom of the day feature
    private View.OnClickListener mCardViewOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            Uri currentIdiomUri = IdiomCollectionEntry.buildUriWithStringId(mDailyIdiomId);
            intent.setData(currentIdiomUri);
            startActivity(intent);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };
}
