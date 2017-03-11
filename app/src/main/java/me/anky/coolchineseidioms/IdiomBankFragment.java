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
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anky.coolchineseidioms.idiomdatabase.IdiomCollectionContract.IdiomCollectionEntry;
import me.anky.coolchineseidioms.utilities.IdiomListCursorAdapter;
import me.anky.coolchineseidioms.utilities.Utilities;

import static me.anky.coolchineseidioms.userdata.UserContract.FavouritesEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class IdiomBankFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int FAVOURITE_LOADER = 3;
    private IdiomListCursorAdapter mIdiomListCursorAdapter;

    @BindView(R.id.favourite_list)
    ListView mFavouriteListView;

    @BindView(R.id.empty_view)
    RelativeLayout mEmptyView;

    public IdiomBankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kick off the loader
        getActivity().getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_idiom_bank, container, false);
        ButterKnife.bind(this, rootView);

        mIdiomListCursorAdapter = new IdiomListCursorAdapter(getContext(), null);
        mFavouriteListView.setAdapter(mIdiomListCursorAdapter);
        mFavouriteListView.setEmptyView(mEmptyView);

        mFavouriteListView.setOnItemClickListener(mFavouriteOnClickListener);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                FavouritesEntry.CONTENT_URI,
                Utilities.FAVOURITE_DB_COLUMNS,
                null,
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

    // Triggered when user clicks on an idiom
    private AdapterView.OnItemClickListener mFavouriteOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get idiom ID from the favourite table
            String idiomId = null;
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            if(cursor != null){
                idiomId = cursor.getString(Utilities.COL_IDIOM_ID);
            }

            Intent intent = new Intent(getContext(), DetailActivity.class);
            Uri currentIdiomUri = IdiomCollectionEntry.buildUriWithStringId(idiomId);
            intent.setData(currentIdiomUri);
            startActivity(intent);
        }
    };
}
