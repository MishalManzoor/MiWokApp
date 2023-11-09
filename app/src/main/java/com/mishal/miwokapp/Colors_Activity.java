package com.mishal.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Colors_Activity extends AppCompatActivity {

    ListView listView;
    private MediaPlayer mediaPlayer;

    //audio manager class for audio focus
    private AudioManager audioManager;

    /**
     * Clean up the media player by releasing its resources.
     */
    public void releaseResources() {

        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {

            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(listener);
        }

    }

    //Audio focus listener
    private final AudioManager.OnAudioFocusChangeListener listener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                releaseResources();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    };

    //we create an global variable for the listener
    MediaPlayer.OnCompletionListener onCompletionListener = mp -> releaseResources();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        listView = findViewById(R.id.listView);
        //get services
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words = new ArrayList<>();

        addDataInList(words);
        showInAdapter(words);
    }

    public void addDataInList(ArrayList<word> words){
        words.add(new word("red", "weṭeṭṭi", R.drawable.color_red,
                R.raw.color_red));
        words.add(new word("green", "chokokki", R.drawable.color_green,
                R.raw.color_green));
        words.add(new word("brown", "ṭakaakki", R.drawable.color_brown,
                R.raw.color_brown));
        words.add(new word("gray", "ṭopoppi", R.drawable.color_gray,
                R.raw.color_gray));
        words.add(new word("black", "kululli", R.drawable.color_black,
                R.raw.color_black));
        words.add(new word("white", "kelelli", R.drawable.color_white,
                R.raw.color_white));
        words.add(new word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow));
        words.add(new word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));
    }

    public void showInAdapter(ArrayList<word> words){
        wordAdapter adapter = new wordAdapter(this, words, R.color.purple);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            releaseResources();

            word word = words.get(position);

            //media will run when audio focus will REQUEST_GRANTED
            int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                mediaPlayer = MediaPlayer.create(Colors_Activity.this, word.getAudio());
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(onCompletionListener);
            }
        });
    }

    //this is also a callback once are activity resume this will execute
    @Override
    protected void onStop() {
        super.onStop();

        releaseResources();
    }
}