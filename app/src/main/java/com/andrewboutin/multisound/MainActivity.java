package com.andrewboutin.multisound;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.logging.Logger;

// TODO: Don't allow duplicate names
public class MainActivity extends AppCompatActivity {
    private Button addSoundButton;
    private ListView soundListView;
    private SoundAdapter soundAdapter;
    private ArrayList<Sound> sounds;
    private DBHandler dbHandler;
    private EditText nameEdit, fileEdit;
    private TextView nameText, fileText;

    // TODO: Comments
    // TODO: Default sound
    // TODO: ******Widget
    // TODO: Come with one sound as an example
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = DBHandler.getDbHandler(this);

        sounds = new ArrayList<>();

        soundAdapter = new SoundAdapter(this, sounds);

        populateSounds();

        soundListView = (ListView)findViewById(R.id.soundListView);
        soundListView.setAdapter(soundAdapter);
        soundListView.setItemsCanFocus(true);

        addSoundButton = (Button)findViewById(R.id.addSoundButton);
        addSoundButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String file = fileEdit.getText().toString();

                if(name.equals("") || file.equals("")){
                    Toast.makeText(v.getContext(), "Requires a name and file!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Sound sound = dbHandler.createSound(nameEdit.getText().toString(),
                                                    fileEdit.getText().toString());

                soundAdapter.add(sound);
                soundAdapter.notifyDataSetChanged();

                Toast.makeText(v.getContext(), "Created " + sound.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        nameEdit = (EditText)findViewById(R.id.nameEdit);
        fileEdit = (EditText)findViewById(R.id.fileEdit);

        nameText = (TextView)findViewById(R.id.nameView);
        fileText = (TextView)findViewById(R.id.fileView);

        setAppColors();
    }

    private void populateSounds(){
        sounds = dbHandler.getAllSounds();

        for(Sound sound: sounds)
            soundAdapter.add(sound);
    }

    private void setAppColors(){
        soundListView.getRootView().setBackgroundColor(getResources().getColor(android.R.color.black));

        View[] views = new View[]{nameEdit, fileEdit, nameText, fileText, soundListView};

        Resources resources = getResources();

        for(View view: views)
            view.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
