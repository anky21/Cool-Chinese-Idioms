package me.anky.coolchineseidioms.utilities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.anky.coolchineseidioms.R;

/**
 * Created by Anky An on 28/02/2017.
 * anky25@gmail.com
 */

public class IdiomListCursorAdapter extends CursorAdapter {
    MainViewHolder mainViewHolder;

    public IdiomListCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    public class MainViewHolder {
        FrameLayout soundPlayButton;
        TextView idiomName;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        mainViewHolder = new MainViewHolder();

        rootView.setTag(mainViewHolder);

        return rootView;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Read the idiom attributes from the Cursor
        String idiomName = cursor.getString(Utilities.COL_IDIOM_NAME);
        final String audioFile = cursor.getString(Utilities.COL_AUDIO_FILE);

        // Set the views for the ViewHolder
        mainViewHolder.soundPlayButton = (FrameLayout) view.findViewById(R.id.sound_play_icon_frame);
        mainViewHolder.idiomName = (TextView) view.findViewById(R.id.idiom_name);

        // Update TextViews with the attributes for the current idiom
        mainViewHolder.idiomName.setText(idiomName);
        mainViewHolder.idiomName.setContentDescription(idiomName);
        mainViewHolder.soundPlayButton.setContentDescription(context.getResources()
                .getString(R.string.cd_sound_play_button) + idiomName);
        mainViewHolder.soundPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MediaPlayerService to play the audio
                Intent playIntent = new Intent(context, MediaPlayerService.class);
                playIntent.putExtra(Intent.EXTRA_TEXT, audioFile);
                context.startService(playIntent);
            }
        });
    }
}
