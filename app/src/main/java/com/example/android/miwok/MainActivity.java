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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    TextView numbers , family , colors , phrases ;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.numbers :
                intent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(intent);
                break;

            case R.id.family :
                intent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(intent);
                break;

            case R.id.colors :
                intent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(intent);
                break;

            case R.id.phrases :
                intent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void initViews(){
        numbers = (TextView) findViewById(R.id.numbers);
        family  = (TextView) findViewById(R.id.family);
        colors  = (TextView) findViewById(R.id.colors);
        phrases = (TextView) findViewById(R.id.phrases);

        numbers.setOnClickListener(this);
        family.setOnClickListener(this);
        colors.setOnClickListener(this);
        phrases.setOnClickListener(this);
    }
}
