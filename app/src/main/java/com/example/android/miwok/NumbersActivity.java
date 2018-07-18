/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
public class NumbersActivity extends AppCompatActivity {

    WordAdapter numbersAdapter;
    ArrayList <WordObject> numbers;

    LinearLayout rootView;
    ListView listNumbers;

    MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
    }};


    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        /*
        rootView = (LinearLayout) findViewById(R.id.rootView);
        for(int i = 0 ; i < numbers.size(); i++){
            TextView textView = new TextView(this);
            textView.setText(numbers.get(i));
            textView.setTextSize(35);
            textView.setTextColor(Color.GREEN);
            rootView.addView(textView);
        }
        */
        numbers = new ArrayList<>();
        numbers.add(new WordObject("One"   , "Lutti"    , R.drawable.number_one   , R.raw.number_one) );
        numbers.add(new WordObject("Two"   , "Otiiko"   , R.drawable.number_two   , R.raw.number_two));
        numbers.add(new WordObject("Three" , "Tolookosu", R.drawable.number_three , R.raw.number_three));
        numbers.add(new WordObject("Four"  , "Oyyisa"   , R.drawable.number_four  , R.raw.number_four));
        numbers.add(new WordObject("Five"  , "Massokka" , R.drawable.number_five  , R.raw.number_five));
        numbers.add(new WordObject("Six"   , "Temmokka" , R.drawable.number_six   , R.raw.number_six));
        numbers.add(new WordObject("Seven" , "Kenekaku" , R.drawable.number_seven , R.raw.number_seven));
        numbers.add(new WordObject("Eight" , "Kawinta"  , R.drawable.number_eight , R.raw.number_eight));
        numbers.add(new WordObject("Nine"  , "Wo’e"     , R.drawable.number_nine  , R.raw.number_nine));
        numbers.add(new WordObject("Ten"   , "Na’aacha" , R.drawable.number_ten   , R.raw.number_ten));

        //    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, numbers);
        numbersAdapter = new WordAdapter(this , numbers , R.color.category_numbers );
        listNumbers = (ListView) findViewById(R.id.list);
        listNumbers.setAdapter(numbersAdapter);

        listNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             //   Toast.makeText(NumbersActivity.this, "item click " + position, Toast.LENGTH_SHORT).show();

                WordObject numberObject = numbers.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus( onAudioFocusChangeListener ,AudioManager.STREAM_MUSIC ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, numberObject.getSound());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }

        }});



    }


    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }



    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
