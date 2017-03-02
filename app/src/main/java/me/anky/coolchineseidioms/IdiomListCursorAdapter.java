package me.anky.coolchineseidioms;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;

/**
 * Created by Anky An on 28/02/2017.
 * anky25@gmail.com
 */

public class IdiomListCursorAdapter extends CursorAdapter {
    MainViewHolder mainViewHolder;

    public IdiomListCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    public class MainViewHolder{
        FrameLayout soundPlayButton;
        TextView idiomName;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        mainViewHolder = new MainViewHolder();

        mainViewHolder.soundPlayButton = (FrameLayout)rootView.findViewById(R.id.sound_play_icon_frame);
        mainViewHolder.idiomName = (TextView)rootView.findViewById(R.id.idiom_name);

        rootView.setTag(mainViewHolder);

        return rootView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Find the columns of idiom attributes
        int columnIdiom = cursor.getColumnIndex(IdiomCollectionEntry.COLUMN_IDIOM);
        int columnAudio = cursor.getColumnIndex(IdiomCollectionEntry.COLUMN_AUDIO_FILE);

        // Read the idiom attributes from the Cursor
        String idiomName = cursor.getString(columnIdiom);
        final String audioFile = cursor.getString(columnAudio);

        // Update TextViews with the attributes for the current idiom
        mainViewHolder.idiomName.setText(idiomName);
        mainViewHolder.soundPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, audioFile, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
