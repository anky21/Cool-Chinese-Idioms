package me.anky.coolchineseidioms;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;

import static me.anky.coolchineseidioms.Utilities.IDIOM_FEW_COLUMNS;

/**
 * Created by Anky An on 1/03/2017.
 * anky25@gmail.com
 */

public class IdiomListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER = 0;

    @BindView(R.id.list)
    ListView listView;

    IdiomListCursorAdapter mIdiomListCursorAdapter;

    public IdiomListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String intentExtra = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (intentExtra.equals(HomeFragment.COMMON)) {
                selection = IdiomCollectionEntry.COLUMN_FREQUENCY + " = 1";
            } else if (intentExtra.equals(HomeFragment.BEGINNER)){
                selection = IdiomCollectionEntry.COLUMN_LEVEL + " = 0";
            } else if (intentExtra.equals(HomeFragment.INTERMEDIATE)){
                selection = IdiomCollectionEntry.COLUMN_LEVEL + " = 1";
            }else if (intentExtra.equals(HomeFragment.ADVANCED)){
                selection = IdiomCollectionEntry.COLUMN_LEVEL + " = 2";
            }
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
}