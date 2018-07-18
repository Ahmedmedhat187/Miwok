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

public class FamilyActivity extends AppCompatActivity {

    WordAdapter familyAdapter;
    ArrayList<WordObject> family;

    ListView listFamily;

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

        family = new ArrayList<>();
        family.add(new WordObject("father"         , "әpә"     , R.drawable.family_father          , R.raw.family_father));
        family.add(new WordObject("mother"         , "әṭa"      , R.drawable.family_mother         , R.raw.family_mother));
        family.add(new WordObject("son"            , "angsi"   , R.drawable.family_son             , R.raw.family_son));
        family.add(new WordObject("daughter"       , "tune"    , R.drawable.family_daughter        , R.raw.family_daughter));
        family.add(new WordObject("older brother"  , "taachi"  , R.drawable.family_older_brother   , R.raw.family_older_brother));
        family.add(new WordObject("younger brother", "chalitti", R.drawable.family_younger_brother , R.raw.family_younger_brother));
        family.add(new WordObject("older sister"   , "teṭe"    , R.drawable.family_older_sister     , R.raw.family_older_sister));
        family.add(new WordObject("younger sister" , "kolliti" , R.drawable.family_younger_sister  , R.raw.family_younger_sister));
        family.add(new WordObject("grandmother "   , "ama"     , R.drawable.family_grandmother     , R.raw.family_grandmother));
        family.add(new WordObject("grandfather"    , "paapa"   , R.drawable.family_grandfather     , R.raw.family_grandfather));

        familyAdapter = new WordAdapter(this , family, R.color.category_family  );
        listFamily = (ListView) findViewById(R.id.list);
        listFamily.setAdapter(familyAdapter);

        listFamily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WordObject familyObject = family.get(position);

                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus( onAudioFocusChangeListener ,AudioManager.STREAM_MUSIC ,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, familyObject.getSound());
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
