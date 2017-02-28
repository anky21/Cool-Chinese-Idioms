package me.anky.coolchineseidioms;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;

/**
 * Created by Anky An on 28/02/2017.
 * anky25@gmail.com
 */

public class IdiomListCursorAdapter extends CursorAdapter {

    public IdiomListCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView rowIdTv = (TextView)view.findViewById(R.id.row_id);
        TextView idiomNameTv = (TextView)view.findViewById(R.id.idiom_name);

        // Find the columns of idiom attributes
        int columnId = cursor.getColumnIndex(IdiomCollectionEntry._ID);
        int columnIdiom = cursor.getColumnIndex(IdiomCollectionEntry.COLUMN_IDIOM);

        // Read the idiom attributes from the Cursor
        String rowId = cursor.getString(columnId);
        String idiomName = cursor.getString(columnIdiom);

        // Update TextViews with the attributes for the current idiom
        rowIdTv.setText(rowId);
        idiomNameTv.setText(idiomName);
    }
}
