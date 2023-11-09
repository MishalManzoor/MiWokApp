package com.mishal.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Family_Activity extends AppCompatActivity {

    ListView listView;
    private MediaPlayer mediaPlayer;
    //audio manager class for audio focus
    private AudioManager audioManager;

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
        words.add(new word("father", "әpә", R.drawable.family_father,
                R.raw.family_father));
        words.add(new word("mother", "әṭa", R.drawable.family_mother,
                R.raw.family_mother));
        words.add(new word("son", "angsi", R.drawable.family_son,
                R.raw.family_son));
        words.add(new word("daughter", "tune", R.drawable.family_daughter,
                R.raw.family_daughter));
        words.add(new word("older brother", "taachi", R.drawable.family_older_brother,
                R.raw.family_older_brother));
        words.add(new word("younger brother", "chalitti", R.drawable.family_younger_brother,
                R.raw.family_younger_brother));
        words.add(new word("older sister", "teṭe", R.drawable.family_older_sister,
                R.raw.family_older_sister));
        words.add(new word("younger sister", "kolliti", R.drawable.family_younger_sister,
                R.raw.family_younger_sister));
        words.add(new word("grandfather", "paapa", R.drawable.family_grandfather,
                R.raw.family_grandfather));
        words.add(new word("grandmother", "ama", R.drawable.family_grandmother,
                R.raw.family_grandmother));
    }

    public void showInAdapter(ArrayList<word> words){
        wordAdapter adapter = new wordAdapter(this, words, R.color.green);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            releaseResources();
            word word = words.get(position);

            //media will run when audio focus will REQUEST_GRANTED
            int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                mediaPlayer = MediaPlayer.create(Family_Activity.this, word.getAudio());
                mediaPlayer.start();

                mediaPlayer.setOnCompletionListener(onCompletionListener);

            }
        });
    }

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

    //this is also a callback once are activity resume this will execute
    @Override
    protected void onStop() {
        super.onStop();

        releaseResources();
    }
}