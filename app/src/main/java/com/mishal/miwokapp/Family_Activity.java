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
    private AudioManager audioManager;
    MediaPlayer.OnCompletionListener onCompletionListener = mp -> releaseResources();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        listView = findViewById(R.id.listView);
 
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words = new ArrayList<>();

        addDataInList(words);

       showInAdapter(words);
    }

    public void addDataInList(ArrayList<word> words){
     /*...*/
    }

    public void showInAdapter(ArrayList<word> words){
        wordAdapter adapter = new wordAdapter(this, words, R.color.green);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            releaseResources();
            word word = words.get(position);

            //media will run when audio focus will REQUEST_GRANTED
          /*...*/
        });
    }

    public void releaseResources() {

        if (mediaPlayer != null) {

            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(listener);
        }

    }

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

    @Override
    protected void onStop() {
        super.onStop();

        releaseResources();
    }
}
