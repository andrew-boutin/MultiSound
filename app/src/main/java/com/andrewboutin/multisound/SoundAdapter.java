package com.andrewboutin.multisound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 8/9/2015.
 */
public class SoundAdapter extends ArrayAdapter<Sound>{
    private DBHandler dbHandler;

    public SoundAdapter(Context context, ArrayList<Sound> sounds) {
        super(context, 0, sounds);
        dbHandler = DBHandler.getDbHandler(context);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent){
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sound, parent, false);

        final Sound sound = (Sound)getItem(index);

        TextView name = (TextView) convertView.findViewById(R.id.tvName);
        name.setText(sound.getName());

        TextView file = (TextView) convertView.findViewById(R.id.tvFile);
        file.setText(sound.getFileName());

        TextView id = (TextView) convertView.findViewById(R.id.tvID);
        id.setText(Long.toString(sound.getId()));

        Button deleteButton = (Button)convertView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                remove(sound);
                dbHandler.deleteSound(sound);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
