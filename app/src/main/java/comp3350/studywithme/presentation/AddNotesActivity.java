// CLASS: AddNotesActivity
//
// REMARKS: this activity handles adding notes to the system
//
//------------------------------------------------------------

package comp3350.studywithme.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.studywithme.R;
import comp3350.studywithme.business.NoteManageable;
import comp3350.studywithme.business.manage.NoteManage;
import comp3350.studywithme.persistence.NotesDatabase;

public class AddNotesActivity extends AppCompatActivity {

    //private NoteManage noteManage;
    private NoteManageable noteManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        EditText titleView, contentView;
        titleView = (EditText) findViewById(R.id.note_title);
        contentView = (EditText) findViewById(R.id.input_contents);

        String selectedNoteTitle = (String) getIntent().getSerializableExtra("selected_note_title");
        String selectedNoteContent = (String) getIntent().getSerializableExtra("selected_note_content");

        if(selectedNoteTitle != null && selectedNoteContent != null) {
            titleView.setText(selectedNoteTitle);
            contentView.setText(selectedNoteContent);
        }
    }

    public void saveButtonOnClick(View view) {
        noteManage = new NoteManage();

        TextView title = (TextView) findViewById(R.id.note_title);
        String titleString = title.getText().toString();
        TextView content= (TextView) findViewById(R.id.input_contents);
        String contentString = content.getText().toString();

        noteManage = new NoteManage();

        if (noteManage.createNote(titleString, contentString)) {
            backToMainPage();
            Toast.makeText(getApplicationContext(), "note created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "note content should be at most 300 characters", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}