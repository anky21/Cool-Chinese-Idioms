package me.anky.coolchineseidioms;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnTaskCompleted{

    private MediaPlayer mediaPlayer;
    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.idiom_of_the_day)
    TextView idiomTv;

    @BindView(R.id.sound_play_icon)
    FrameLayout soundPlayIcon;

    private String idiomPinyin = "";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        new EndpointsAsyncTask(this).execute();

        // Change background colour of the CardView
        cardView.setCardBackgroundColor(Color.CYAN);


        soundPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.releaseMediaPlayer(mediaPlayer);
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.duiniutanqin);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Utilities.releaseMediaPlayer(mp);
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Utilities.releaseMediaPlayer(mediaPlayer);
    }

    @Override
    public void onTaskCompleted(String result) {
        idiomPinyin = result;
        idiomTv.setText(idiomPinyin);
    }

}
