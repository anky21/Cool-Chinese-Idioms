package me.anky.coolchineseidioms;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class IdiomListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER = 0;

    IdiomListCursorAdapter mIdiomListCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idiom_list);

        ListView listView = (ListView)findViewById(R.id.list);
        mIdiomListCursorAdapter = new IdiomListCursorAdapter(this, null);
        listView.setAdapter(mIdiomListCursorAdapter);

        getLoaderManager().initLoader(LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                IdiomCollectionContract.IdiomCollectionEntry.CONTENT_URI,
                null,
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
}
