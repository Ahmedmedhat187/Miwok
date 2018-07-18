package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<WordObject> {

    private int color ;

    public WordAdapter(@NonNull Context context , ArrayList <WordObject> objects , int activityColor ) {
        super(context, 0 ,  objects);
        color = ContextCompat.getColor(getContext() , activityColor);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final WordObject object = getItem(position);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.word_item, parent, false);
        }


        TextView text1 = (TextView) listItemView.findViewById(R.id.text1);
        text1.setText(object.getNumberEnglish());

        TextView text2 = (TextView) listItemView.findViewById(R.id.text2);
        text2.setText(object.getNumberTranslated());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);

        /*
            if (object.getImage() == -1) {
                image.setVisibility(View.GONE);
            } else {
                image.setImageResource(object.getImage());
                image.setVisibility(View.VISIBLE);
            }
        */
        if (object.hasImage()) {
            image.setImageResource(object.getImage());
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }

        View text_container = listItemView.findViewById(R.id.text_container);
        text_container.setBackgroundColor(color);

        return listItemView;
    }
}





