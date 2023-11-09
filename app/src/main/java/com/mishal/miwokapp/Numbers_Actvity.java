package com.mishal.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class Numbers_Actvity extends AppCompatActivity {

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
        words.add(new word("one", "lutti", R.drawable.number_one,
                R.raw.number_one));
        words.add(new word("two", "otiiko", R.drawable.number_two,
                R.raw.number_two));
        words.add(new word("three", "tolookosu", R.drawable.number_three,
                R.raw.number_three));
        words.add(new word("four", "oyyisa", R.drawable.number_four,
                R.raw.number_four));
        words.add(new word("five", "massokka", R.drawable.number_five,
                R.raw.number_five));
        words.add(new word("six", "temmokka", R.drawable.number_six,
                R.raw.number_six));
        words.add(new word("seven", "kenekaku", R.drawable.number_seven,
                R.raw.number_seven));
        words.add(new word("eight", "kawinta", R.drawable.number_eight,
                R.raw.number_eight));
        words.add(new word("nine", "wo'e", R.drawable.number_nine,
                R.raw.number_nine));
        words.add(new word("ten", "na'aacha", R.drawable.number_ten,
                R.raw.number_ten));

    }

    public void showInAdapter(ArrayList<word> words){
        wordAdapter adapter = new wordAdapter(this, words, R.color.organ);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {

            releaseResources();

            word word = words.get(position);

//media will run when audio focus will REQUEST_GRANTED
            int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                mediaPlayer = MediaPlayer.create(Numbers_Actvity.this, word.getAudio());
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


//        int index = 0;
//        while (index < words.size()) {
//
//            TextView view = new TextView(this);
//            view.setText(words.get(index));
//            linearLayout.addView(view);
//
//            index++;
//        }

//        ArrayAdapter<word> adapter = new ArrayAdapter<>(this, R.layout.list_item, words);
//        listView.setAdapter(adapter);
