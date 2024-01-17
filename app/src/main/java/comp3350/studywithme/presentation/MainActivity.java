// CLASS: MainActivity
//
// REMARKS: this activity presents user with the home screen of the system
//          it will show the list of notes and handle the action when click
//          on the note from that list
//
//------------------------------------------------------------

package comp3350.studywithme.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import comp3350.studywithme.application.Services;
import comp3350.studywithme.business.NoteManageable;
import comp3350.studywithme.business.manage.NoteManage;
import comp3350.studywithme.business.manage.NoteNotFoundException;
import comp3350.studywithme.objects.Note;
import comp3350.studywithme.R;

public class MainActivity extends AppCompatActivity {

    private Button add_button;
    ListView listView;
    List<String> noteList;
    List<Note> notes;
    //NoteManage manage = new NoteManage();
    private NoteManageable manage = new NoteManage();
    boolean isDone = false;
    ArrayAdapter<String> arrayAdapter; // JOY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddPageActivity.insertDelayHandler = true;
        ModifyNotesActivity.deleteDelayHandler = true;

        if (!isDone) {
            copyDatabaseToDevice();
            isDone = true;
        }

        noteList = manage.sendAllAvailableTitles();

        listView = (ListView)findViewById(R.id.notes_list);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview,R.id.noteView, noteList); //joy
        listView.setAdapter(arrayAdapter);

        openNote(listView);

        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNote();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        noteList = manage.sendAllAvailableTitles();
        listView = (ListView)findViewById(R.id.notes_list);
        listView.setAdapter(arrayAdapter);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all_notes:
                noteList = manage.sendAllAvailableTitles();
                listView = (ListView)findViewById(R.id.notes_list);
                arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview,R.id.noteView, noteList);
                listView.setAdapter(arrayAdapter);
                Toast.makeText(this, "all notes clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fav_notes:
                noteList = manage.sendAllFavoriteTitles();
                listView = (ListView)findViewById(R.id.notes_list);
                arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview,R.id.noteView, noteList);
                listView.setAdapter(arrayAdapter);
                Toast.makeText(this, "all fav notes clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.recommend_notes:
                try {
                    noteList = manage.getDefaultRecommendation();
                    listView = (ListView)findViewById(R.id.notes_list);
                    arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.noteView, noteList);
                    listView.setAdapter(arrayAdapter);
                    Toast.makeText(this, "all recommend notes clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } catch (final NoteNotFoundException e) {
                    Messages.fatalError(this, e.getMessage());
                } catch (final ParseException e) {
                    Messages.fatalError(this, e.getMessage());
                }
            case R.id.recommend_fav_notes:
                try {
                    noteList = manage.getFavoriteRecommendation();
                    listView = (ListView)findViewById(R.id.notes_list);
                    arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.noteView, noteList);
                    listView.setAdapter(arrayAdapter);
                    Toast.makeText(this, "all recommend favourite notes clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } catch (final NoteNotFoundException e) {
                    Messages.fatalError(this, e.getMessage());
                } catch (final ParseException e) {
                    Messages.fatalError(this, e.getMessage());
                }
            default:
                return false;
        }
    }

    //When click on the item in the list, it will open the corresponding modify notes activity
    //and send information we need to that activity such as title and page content
    public void openNote(ListView listView){
        notes = manage.sendAllAvailableNotes();
        ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        try {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedNoteTitle = notes.get(i).getTitle();
                    String selectedNoteContent;
                    int selectedPageNum;

                    try {
                        manage.updateDate(selectedNoteTitle);
                    } catch (final NoteNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    if (notes.size() == 0) {
                        selectedNoteContent = null;
                        selectedPageNum = 0;
                    } else {
                        selectedNoteContent = notes.get(i).getPageContent(1);
                        selectedPageNum = 1;
                    }

                    boolean selectedNoteFavorite = notes.get(i).getFavorite();
                    Intent intent = new Intent(MainActivity.this, ViewNotes.class);
                    intent.putExtra("selected_note_title", selectedNoteTitle);
                    intent.putExtra("selected_note_content", selectedNoteContent);
                    intent.putExtra("selected_note_favorite", selectedNoteFavorite);
                    intent.putExtra("selected_page_num", selectedPageNum);
                    startActivity(intent);
                }
            });
        } catch (RuntimeException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    //create note, open add notes activity
    public void createNote() {
        Intent intent = new Intent(this, AddNotesActivity.class);
        startActivity(intent);
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);

            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);
            Services.setDBPathName(dataDirectory.toString() + "/" + Services.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}
