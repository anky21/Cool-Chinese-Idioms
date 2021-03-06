package me.anky.coolchineseidioms;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anky.coolchineseidioms.idiomdatabase.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.utilities.IdiomListCursorAdapter;

import static me.anky.coolchineseidioms.utilities.Utilities.IDIOM_FEW_COLUMNS;

/**
 * Created by Anky An on 1/03/2017.
 * anky25@gmail.com
 */

public class IdiomListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER = 0;

    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.list)
    ListView listView;

    IdiomListCursorAdapter mIdiomListCursorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        // Kick off the loader
        getActivity().getLoaderManager().initLoader(LOADER, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_idiom_list, container, false);
        ButterKnife.bind(this, rootView);

        mIdiomListCursorAdapter = new IdiomListCursorAdapter(getContext(), null);
        listView.setAdapter(mIdiomListCursorAdapter);
        listView.setOnItemClickListener(mListViewItemClickListener);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String newTitle = null;
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            selection = intent.getStringExtra(Intent.EXTRA_TEXT);
        }
        if (intent.hasExtra(Intent.EXTRA_TITLE)) {
            newTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
            // Change the title in the toolbar
            getActivity().setTitle(newTitle);
        }
        return new CursorLoader(getContext(),
                IdiomCollectionEntry.CONTENT_URI,
                IDIOM_FEW_COLUMNS,
                selection,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mIdiomListCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mIdiomListCursorAdapter.swapCursor(null);
    }

    private AdapterView.OnItemClickListener mListViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView idiomNameTv = (TextView) view.findViewById(R.id.idiom_name);
            String idiomName = idiomNameTv.getText().toString();

            Intent intent = new Intent(getContext(), DetailActivity.class);
            Uri currentIdiomUri = IdiomCollectionEntry.buildIdiomUriWithId(id);
            intent.setData(currentIdiomUri);
            intent.putExtra(getString(R.string.detail_activity_title), idiomName);
            startActivity(intent);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(id));
            bundle.putString(FirebaseAnalytics.Param.CONTENT, "Idiom_ID" );
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

            // Override the transition
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };
}
