package com.andrewboutin.multisound;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

// TODO: Don't allow duplicate names
public class MainActivity extends AppCompatActivity {
    private Button addSoundButton;
    private ListView soundListView;
    private SoundAdapter soundAdapter;
    private ArrayList<Sound> sounds;
    private DBHandler dbHandler;
    private EditText nameEdit;
    private TextView nameText, fileText, fileChooser;

    // TODO: Comments
    // TODO: Default sound
    // TODO: Come with one sound as an example
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utility.setUp(this.getApplicationContext());

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
                String file = fileChooser.getText().toString();

                if(name.equals("") || file.equals("")){
                    Toast.makeText(v.getContext(), "Requires a name and file!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Sound sound = dbHandler.createSound(name, file);

                soundAdapter.add(sound);
                soundAdapter.notifyDataSetChanged();

                nameEdit.setText("");
                fileChooser.setText("");

                Utility.hideSoftKeyboard(addSoundButton);

                Toast.makeText(v.getContext(), "Created " + sound.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        fileChooser = (TextView)findViewById(R.id.fileChooser);
        fileChooser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("audio/*");
                startActivityForResult(fileIntent, 1);
            }
        });

        nameEdit = (EditText)findViewById(R.id.nameEdit);

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

        View[] views = new View[]{nameEdit, fileChooser, nameText, fileText, soundListView};

        Resources resources = getResources();

        for(View view: views)
            view.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                Uri selectedImageURI = data.getData();
                String filePath = new File(getRealPathFromURI(selectedImageURI)).getPath();
                ((TextView)findViewById(R.id.fileChooser)).setText(filePath);
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
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
