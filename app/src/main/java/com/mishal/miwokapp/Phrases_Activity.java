package com.mishal.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases_Activity extends AppCompatActivity {

    ListView listView;
    private MediaPlayer mediaPlayer;

    //audio manager class for audio focus
    private AudioManager audioManager;

    //we create an global variable for the listener
    MediaPlayer.OnCompletionListener onCompletionListener = mp -> releaseResources();

    //this is also a callback once are activity starts this will execute
    @SuppressLint("MissingInflatedId")
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
        words.add(new word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?", "tinnә oyaase'nә",
                R.raw.phrase_what_is_your_name));
        words.add(new word("My name is...", "oyaaset...",
                R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?", "michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        words.add(new word("I’m feeling good.", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        words.add(new word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I’m coming.", "hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        words.add(new word("I’m coming.", "әәnәm",
                R.raw.phrase_im_coming));
        words.add(new word("Let’s go.", "yoowutis",
                R.raw.phrase_lets_go));
        words.add(new word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));
    }

    public void showInAdapter(ArrayList<word> words){
        wordAdapter adapter = new wordAdapter(this, words, R.color.blue);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            releaseResources();

            word word = words.get(position);

            //Used to identify the volume of audio streams for music playback
            int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,
                    // Used to indicate a temporary gain or request of audio focus
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            //media will run when audio focus will REQUEST_GRANTED
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                mediaPlayer = MediaPlayer.create(Phrases_Activity.this, word.getAudio());
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

            // Set the media player back to null.
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(listener);
        }

    }

    //Audio focus listener
    private final AudioManager.OnAudioFocusChangeListener listener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        releaseResources();
                /*
                Used to indicate a transient loss of audio focus where the
                    loser of the audio focus can lower its output volume if it
                    wants to continue playing
                 */
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    /*
                   (also referred to as "ducking"),
                    as the new focus owner doesn't require others to be silent.
                     */
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