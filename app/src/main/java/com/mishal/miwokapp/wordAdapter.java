package com.mishal.miwokapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class wordAdapter extends ArrayAdapter<word> {

    private final int colorActivity;

    // invoke the suitable constructor of the ArrayAdapter class
    public wordAdapter(@NonNull Context context, ArrayList<word> arrayList,
                       int colorResourceId) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
        colorActivity = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View listView = convertView;

        if (listView == null) {

            listView =
                    LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                            parent, false);
        }

        // get Position
        // build in method getItem(--)
        word currentNumberOfPosition = getItem(position);

        TextView miWork = listView.findViewById(R.id.miWork);
        miWork.setText(currentNumberOfPosition.getMiworkTranslation());

        TextView default_text = listView.findViewById(R.id.default_text);
        default_text.setText(currentNumberOfPosition.getDefaultTranslation());

        ImageView images = listView.findViewById(R.id.images);

        if (currentNumberOfPosition.hasImage()) {

            images.setImageResource(currentNumberOfPosition.getImages());

            images.setVisibility(View.VISIBLE);
        } else {

            images.setVisibility(View.GONE);
        }

        View textColor = listView.findViewById(R.id.linearLayout);
        int color = ContextCompat.getColor(getContext(), colorActivity);
        textColor.setBackgroundColor(color);

        View textColor1 = listView.findViewById(R.id.linearLayout3);
        int color1 = ContextCompat.getColor(getContext(), colorActivity);
        textColor1.setBackgroundColor(color1);

        View textColor2 = listView.findViewById(R.id.ConstraintLayout);
        int color2 = ContextCompat.getColor(getContext(), colorActivity);
        textColor2.setBackgroundColor(color2);


        return listView;
    }
}