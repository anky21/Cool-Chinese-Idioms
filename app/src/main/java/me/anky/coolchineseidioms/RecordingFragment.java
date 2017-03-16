package me.anky.coolchineseidioms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment with a Google +1 button.
 */
public class RecordingFragment extends Fragment {

    @BindView(R.id.button_listen)
    AppCompatButton mButtonListen;

    @BindView(R.id.button_record)
    AppCompatButton mButtonRecord;

    @BindView(R.id.button_play)
    AppCompatButton mButtonPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recording, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }

}
