// CLASS: ViewNotes
//
// REMARKS: this activity presents user with the view screen of the notes
//          it will allow user to look through pages back and forth,
//          switch favorite status, and go to edit mode
//
//------------------------------------------------------------

package comp3350.studywithme.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.studywithme.R;
import comp3350.studywithme.business.NoteManageable;
import comp3350.studywithme.business.PageManageable;
import comp3350.studywithme.business.manage.NoteManage;
import comp3350.studywithme.business.manage.NoteNotFoundException;
import comp3350.studywithme.objects.Note;
import comp3350.studywithme.objects.Page;

public class ViewNotes extends AppCompatActivity {

    //private NoteManage noteManage;
    private NoteManageable noteManage;
    private PageManageable pageManage;
    private ImageButton favorite_button;
    private String selectedNoteTitle, selectedNoteContent;
    private boolean selectedNoteFavorite;
    private int selectedPageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        noteManage = new NoteManage();

        TextView titleView, contentsView, pageNumView;
        titleView = (TextView) findViewById(R.id.note_title_for_view);
        contentsView = (TextView) findViewById(R.id.input_contents_for_view);
        pageNumView = (TextView) findViewById(R.id.page_num_for_view);
        favorite_button = (ImageButton) findViewById(R.id.fav_button);

        selectedNoteTitle = (String) getIntent().getSerializableExtra("selected_note_title");
        selectedNoteContent = (String) getIntent().getSerializableExtra("selected_note_content");
        selectedNoteFavorite = (Boolean) getIntent().getSerializableExtra("selected_note_favorite");
        selectedPageNum = (int) getIntent().getSerializableExtra("selected_page_num");

        if(selectedNoteTitle != null && selectedNoteContent != null) {
            titleView.setText(selectedNoteTitle);
            contentsView.setText(selectedNoteContent);
            pageNumView.setText("Page " + selectedPageNum);
            if(selectedNoteFavorite) {
                favorite_button.setBackgroundResource(R.drawable.baseline_star_24);
            }else {
                favorite_button.setBackgroundResource(R.drawable.baseline_star_border_24);
            }
        }
    }

    public void editButtonOnClick(View view) {
        goToEditingMode(selectedNoteTitle, selectedNoteContent, selectedNoteFavorite, selectedPageNum);
    }

    public void prevButtonOnClick(View view) {
        noteManage = new NoteManage();
        pageManage = new NoteManage();
        Page result = pageManage.getPrevPage(selectedNoteTitle, selectedPageNum);

        try {
            if (result == null) {
                Toast.makeText(getApplicationContext(), "it is the first page", Toast.LENGTH_SHORT).show();
            } else {
                goToPrevPage(selectedNoteTitle, noteManage.getFavorite(selectedNoteTitle), result.getContent(), result.getPageNum());
            }
        } catch (final NoteNotFoundException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void nextButtonOnClick(View view) {
        noteManage = new NoteManage();
        pageManage = new NoteManage();
        Page result = pageManage.getNextPage(selectedNoteTitle, selectedPageNum);

        try {
            if (result == null) {
                Toast.makeText(getApplicationContext(), "it is the last page", Toast.LENGTH_SHORT).show();
            } else {
                goToNextPage(selectedNoteTitle, noteManage.getFavorite(selectedNoteTitle), result.getContent(), result.getPageNum());
            }
        } catch (final NoteNotFoundException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void favoriteButtonOnClick(View view) {
        noteManage = new NoteManage();

        try {
            noteManage = new NoteManage();
            noteManage.switchFavorite(selectedNoteTitle);

            if(noteManage.getFavorite(selectedNoteTitle)) {
                favorite_button.setBackgroundResource(R.drawable.baseline_star_24);
            } else {
                favorite_button.setBackgroundResource(R.drawable.baseline_star_border_24);
            }
            backToMainPage();
        } catch (final NoteNotFoundException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void goToEditingMode(String selectedNoteTitle, String selectedNoteContent, boolean selectedNoteFavorite, int selectedPageNum) {
        Intent intent = new Intent(this, ModifyNotesActivity.class);
        intent.putExtra("selected_note_title", selectedNoteTitle);
        intent.putExtra("selected_note_favorite", selectedNoteFavorite);
        intent.putExtra("selected_note_content", selectedNoteContent);
        intent.putExtra("selected_page_num", selectedPageNum);
        startActivity(intent);
    }

    public void goToPrevPage(String selectedNoteTitle, boolean selectedNoteFavorite, String selectedNoteContent, int selectedPageNum) {
        Intent intent = new Intent(this, ViewNotes.class);
        intent.putExtra("selected_note_title", selectedNoteTitle);
        intent.putExtra("selected_note_favorite", selectedNoteFavorite);
        intent.putExtra("selected_note_content", selectedNoteContent);
        intent.putExtra("selected_page_num", selectedPageNum);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void goToNextPage(String selectedNoteTitle, boolean selectedNoteFavorite, String selectedNoteContent, int selectedPageNum) {
        Intent intent = new Intent(this, ViewNotes.class);
        intent.putExtra("selected_note_title", selectedNoteTitle);
        intent.putExtra("selected_note_favorite", selectedNoteFavorite);
        intent.putExtra("selected_note_content", selectedNoteContent);
        intent.putExtra("selected_page_num", selectedPageNum);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void backToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}