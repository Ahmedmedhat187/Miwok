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
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    WordAdapter colorAdapter;
    ArrayList<WordObject> color;

    ListView listColor;

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

        color = new ArrayList<>();
        color.add(new WordObject("red"           ,  "weṭeṭṭi"  , R.drawable.color_red            , R.raw.color_red));
        color.add(new WordObject("mustard yellow", "chiwiiṭә" , R.drawable.color_mustard_yellow , R.raw.color_mustard_yellow));
        color.add(new WordObject("dusty yellow"  , "ṭopiisә"  , R.drawable.color_dusty_yellow   , R.raw.color_dusty_yellow));
        color.add(new WordObject("green"         , "chokokki" , R.drawable.color_green          , R.raw.color_green));
        color.add(new WordObject("brown"         , "ṭakaakki" , R.drawable.color_brown          , R.raw.color_brown));
        color.add(new WordObject("gray"          , "ṭopoppi"  , R.drawable.color_gray           , R.raw.color_gray));
        color.add(new WordObject("black"         , "kululli"  , R.drawable.color_black          , R.raw.color_black));
        color.add(new WordObject("white"         , "kelelli"  , R.drawable.color_white          , R.raw.color_white));

        colorAdapter = new WordAdapter(this , color, R.color.category_colors );
        listColor = (ListView) findViewById(R.id.list);
        listColor.setAdapter(colorAdapter);

        listColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordObject colorObject = color.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus( onAudioFocusChangeListener ,AudioManager.STREAM_MUSIC ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, colorObject.getSound());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
         }});
    }


    @Override
    protected void onStop() {
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
