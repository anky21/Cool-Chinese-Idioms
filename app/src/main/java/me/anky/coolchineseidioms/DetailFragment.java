package me.anky.coolchineseidioms;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static me.anky.coolchineseidioms.UserContract.FavouritesEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.idiom_name_tv)
    TextView mIdiomNameTv;

    @BindView(R.id.favourite_button)
    FrameLayout mFavouriteButton;

    @BindView(R.id.favourite_icon)
    ImageView mFavouriteIcon;

    @BindView(R.id.pinyin1_tv)
    TextView mPinyin1Tv;

    @BindView(R.id.translation_tv)
    TextView mTranslationTv;

    @BindView(R.id.explanation_tv)
    TextView mExplanationTv;

    @BindView(R.id.eg1_chn_tv)
    TextView mEg1ChnTv;

    @BindView(R.id.eg1_eng_tv)
    TextView mEg1EngTv;

    @BindView(R.id.eg1_icon_frame)
    FrameLayout mEg1IconFrame;

    @BindView(R.id.eg2_chn_tv)
    TextView mEg2ChnTv;

    @BindView(R.id.eg2_eng_tv)
    TextView mEg2EngTv;

    @BindView(R.id.eg2_icon_frame)
    FrameLayout mEg2IconFrame;

    @BindView(R.id.eg3_chn_tv)
    TextView mEg3ChnTv;

    @BindView(R.id.eg3_eng_tv)
    TextView mEg3EngTv;

    @BindView(R.id.eg3_icon_frame)
    FrameLayout mEg3IconFrame;

    @BindView(R.id.sound_icon_frame)
    FrameLayout soundPlayIconFrame;

    @BindView(R.id.youtube_layout)
    LinearLayout mYoutubeLayout;

    private static final String YOUTUBE_REQUEST_URL = "https://www.youtube.com/watch?v=";

    ContentResolver mContentResolver;

    private String mIdiomId;
    private String mIdiomName;
    private String mIdiomAudio;
    private String mYoutubeKey;
    private String mPinyin1;
    private String mTranslation;
    private String mExplanationEng;
    private String mExample1;
    private String mExample1Eng;
    private String mExample2;
    private String mExample2Eng;
    private String mExample3;
    private String mExample3Eng;
    private String mEg1Audioid;
    private String mEg2Audioid;
    private String mEg3Audioid;

    /**
     * Content URI for the current idiom
     */
    private Uri mCurrentIdiomUri;

    /**
     * Identifier for the current idiom loader
     */
    private static final int CURRENT_IDIOM_LOADER = 7;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mContentResolver = getActivity().getContentResolver();

        Intent intent = getActivity().getIntent();
        mCurrentIdiomUri = intent.getData();

        // Initialise a loader the read the current idiom data from the database
        getLoaderManager().initLoader(CURRENT_IDIOM_LOADER, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                mCurrentIdiomUri,
                Utilities.IDIOM_ALL_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Context context = getContext();

        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            cursor.close();
            return;
        }

        // Read data from the cursor
        if (cursor.moveToFirst()) {
            mIdiomId = cursor.getString(Utilities.COL_IDIOM_ID);
            mIdiomName = cursor.getString(Utilities.COL_IDIOM_NAME);
            mPinyin1 = cursor.getString(Utilities.COL_PINYIN1);
            mTranslation = cursor.getString(Utilities.COL_TRANSLATION);
            mExplanationEng = cursor.getString(Utilities.COL_EXPLANATION_ENG);
            mExample1 = cursor.getString(Utilities.COL_EXAMPLE1);
            mExample1Eng = cursor.getString(Utilities.COL_EXAMPLE1_ENG);
            mExample2 = cursor.getString(Utilities.COL_EXAMPLE2);
            mExample2Eng = cursor.getString(Utilities.COL_EXAMPLE2_ENG);
            mExample3 = cursor.getString(Utilities.COL_EXAMPLE3);
            mExample3Eng = cursor.getString(Utilities.COL_EXAMPLE3_ENG);
            mIdiomAudio = cursor.getString(Utilities.COL_AUDIO_FILE);
            mYoutubeKey = cursor.getString(Utilities.COL_YOUTUBE);
            mEg1Audioid = cursor.getString(Utilities.COL_EXAMPLE1_AUDIO);
            mEg2Audioid = cursor.getString(Utilities.COL_EXAMPLE2_AUDIO);
            mEg3Audioid = cursor.getString(Utilities.COL_EXAMPLE3_AUDIO);

            // Check and set the favourite icon
            Utilities.setFavouriteIcon(Utilities.isFavourite(context, mIdiomId), mFavouriteIcon);
            mFavouriteButton.setOnClickListener(mFavouriteButtonClickListener);

            // Open Youtube or a website to see a video
            mYoutubeLayout.setOnClickListener(mYoutubeClickListener);

            // Set up the audio play button for the idiom
            soundPlayIconFrame.setOnClickListener(new SoundOCL(mIdiomAudio));

            // Set up the audio play button for example 1
            mEg1IconFrame.setOnClickListener(new SoundOCL(mEg1Audioid));

            // Set up the audio play button for example 2
            mEg2IconFrame.setOnClickListener(new SoundOCL(mEg2Audioid));

            // Set up the audio play button for example 3
            mEg3IconFrame.setOnClickListener(new SoundOCL(mEg3Audioid));

            mIdiomNameTv.setText(mIdiomName);
            mPinyin1Tv.setText(mPinyin1);
            int translationLength = mTranslationTv.getText().length();
            mTranslationTv.append(" " + mTranslation);
            changeTextStyle(mTranslationTv, translationLength);

            int explanationLength = mExplanationTv.getText().length();
            mExplanationTv.append(" " + mExplanationEng);
            changeTextStyle(mExplanationTv, explanationLength);

            mEg1ChnTv.setText(mExample1);
            mEg1EngTv.setText(mExample1Eng);
            mEg2ChnTv.setText(mExample2);
            mEg2EngTv.setText(mExample2Eng);
            mEg3ChnTv.setText(mExample3);
            mEg3EngTv.setText(mExample3Eng);
        }
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    // Change style of partial text
    private void changeTextStyle(TextView textView, int end) {
        SpannableStringBuilder textSpan = SpannableStringBuilder.valueOf(textView.getText());
        textSpan.setSpan(new TextAppearanceSpan(getActivity(), android.R.style.TextAppearance_Medium), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * Override OnClickListener to have some arguments
     */
    class SoundOCL implements View.OnClickListener {
        String audioFileName;

        public SoundOCL(String audioFileName) {
            this.audioFileName = audioFileName;
        }

        public void onClick(View v) {
            // Start MediaPlayerService to play the audio
            Intent playIntent = new Intent(getContext(), MediaPlayerService.class);
            playIntent.putExtra(Intent.EXTRA_TEXT, audioFileName);
            getActivity().startService(playIntent);
        }
    }


    // Triggered when user clicks on the Youtube video area
    private View.OnClickListener mYoutubeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri youtubeUri = Uri.parse(YOUTUBE_REQUEST_URL + mYoutubeKey);
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);
            startActivity(youtubeIntent);
        }
    };

    // Triggered when user clicks on the favourite button
    private View.OnClickListener mFavouriteButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((int) mFavouriteIcon.getTag() == R.drawable.ic_heart_outline) {
                ContentValues values = new ContentValues();
                values.put(FavouritesEntry.COLUMN_FAVORT_ID, mIdiomId);
                values.put(FavouritesEntry.COLUMN_FAVORT_IDIOM, mIdiomName);
                values.put(FavouritesEntry.COLUMN_FAVORT_AUDIO, mIdiomAudio);

                // Insert this idiom into the database
                mContentResolver.insert(FavouritesEntry.CONTENT_URI, values);
                Toast.makeText(getContext(), R.string.added_to_favourites,
                        Toast.LENGTH_SHORT).show();
                // Change the favourite icon
                Utilities.setFavouriteIcon(true, mFavouriteIcon);
            } else {
                mContentResolver.delete(
                        FavouritesEntry.CONTENT_URI,
                        FavouritesEntry.COLUMN_FAVORT_ID + "=?",
                        new String[]{mIdiomId}
                );
                // Change the favourite icon
                Utilities.setFavouriteIcon(false, mFavouriteIcon);
                Toast.makeText(getContext(), R.string.removed_from_favourites,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
