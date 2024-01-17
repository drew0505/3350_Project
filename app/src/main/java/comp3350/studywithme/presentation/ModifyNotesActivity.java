// CLASS: ModifyNotesActivity
//
// REMARKS: this activity will handle modification of the notes
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
import comp3350.studywithme.business.PageManageable;
import comp3350.studywithme.business.manage.BusinessException;
import comp3350.studywithme.business.manage.NoteManage;

public class ModifyNotesActivity extends AppCompatActivity {

    public static boolean deleteDelayHandler = true;
    //private NoteManage noteManage;
    private NoteManageable noteManage;
    private PageManageable pageManage;
    private String selectedNoteTitle, selectedNoteContent, titleOld;
    private boolean selectedNoteFavorite;
    private int selectedPageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_notes);

        TextView v, pageNumView;
        v = (TextView) findViewById(R.id.note_title_2);
        pageNumView = (TextView) findViewById(R.id.page_num_for_modify);

        EditText titleView, contentView;
        titleView = (EditText) findViewById(R.id.note_title_2);
        contentView = (EditText) findViewById(R.id.input_contents_2);

        Bundle bundle = this.getIntent().getExtras();
        titleOld = bundle.getString("selected_note_title");

        selectedNoteTitle = (String) getIntent().getSerializableExtra("selected_note_title");
        selectedNoteContent = (String) getIntent().getSerializableExtra("selected_note_content");
        selectedNoteFavorite = (Boolean) getIntent().getSerializableExtra("selected_note_favorite");
        selectedPageNum = (int) getIntent().getSerializableExtra("selected_page_num");

        if(selectedNoteTitle != null && selectedNoteContent != null) {
            titleView.setText(selectedNoteTitle);
            contentView.setText(selectedNoteContent);
            pageNumView.setText("Page " + selectedPageNum);
        }
    }

    public void updateButtonOnClick(View view) {
        try {
            TextView titleNew = (TextView) findViewById(R.id.note_title_2);
            String titleStringNew = titleNew.getText().toString();
            TextView contentNew = (TextView) findViewById(R.id.input_contents_2);
            String contentStringNew = contentNew.getText().toString();

            noteManage = new NoteManage();
            pageManage = new NoteManage();
            noteManage.changeTitle(titleOld, titleStringNew);
            pageManage.changePageContent(titleStringNew, contentStringNew, selectedPageNum);

            backToMainPage();
            Toast.makeText(getApplicationContext(), "note updated", Toast.LENGTH_SHORT).show();
        } catch (final BusinessException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void insertButtonOnClick(View view) {
        noteManage = new NoteManage();
        goToAddPageActivity(selectedNoteTitle, selectedPageNum);
    }

    public void deleteButtonOnClick(View view) {
        try {
            if (deleteDelayHandler) {
                deleteDelayHandler = false;
                noteManage = new NoteManage();
                pageManage = new NoteManage();
                if (selectedNoteTitle != null && selectedPageNum != 0) {
                    if (noteManage.getNote(selectedNoteTitle).getNumPages() == 1) {
                        noteManage.deleteNote(selectedNoteTitle);
                        Toast.makeText(getApplicationContext(), "last page deleted, therefore note deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        pageManage.deletePage(selectedNoteTitle, selectedPageNum);
                        Toast.makeText(getApplicationContext(), "page " + selectedPageNum + " deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                backToMainPage();
            }
        } catch (final BusinessException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void backToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToAddPageActivity(String selectedNoteTitle, int selectedPageNum) {
        Intent intent = new Intent(this, AddPageActivity.class);
        intent.putExtra("selected_note_title", selectedNoteTitle);
        intent.putExtra("selected_page_num", selectedPageNum);
        startActivity(intent);
    }

}