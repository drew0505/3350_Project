// CLASS: AddPagesActivity
//
// REMARKS: this activity handles adding pages to the system
//
//------------------------------------------------------------

package comp3350.studywithme.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import comp3350.studywithme.R;
import comp3350.studywithme.business.NoteManageable;
import comp3350.studywithme.business.PageManageable;
import comp3350.studywithme.business.manage.BusinessException;
import comp3350.studywithme.business.manage.NoteManage;

public class AddPageActivity extends AppCompatActivity {

    public static boolean insertDelayHandler = true;
   // NoteManage noteManage;
    private PageManageable pageManage;
    String selectedNoteTitle;
    int selectedPageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        TextView titleView = (TextView) findViewById(R.id.note_title_3);

        selectedNoteTitle = (String) getIntent().getSerializableExtra("selected_note_title");
        selectedPageNum = (int) getIntent().getSerializableExtra("selected_page_num");

        titleView.setText(selectedNoteTitle);

    }

    public void insertBeforeButtonOnClick(View view) {
        try {
            if (insertDelayHandler) {
                insertDelayHandler = false;
                TextView content = (TextView) findViewById(R.id.input_contents_3);
                String contentString = content.getText().toString();

                pageManage = new NoteManage();
                pageManage.insertPageBefore(selectedNoteTitle, contentString, selectedPageNum);

                backToMainPage();
                Toast.makeText(getApplicationContext(), "page inserted", Toast.LENGTH_SHORT).show();
            }
        } catch (final BusinessException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void insertAfterButtonOnClick(View view) {
        try {
            if (insertDelayHandler) {
                insertDelayHandler = false;
                TextView content = (TextView) findViewById(R.id.input_contents_3);
                String contentString = content.getText().toString();

                pageManage = new NoteManage();
                pageManage.insertPageAfter(selectedNoteTitle, contentString, selectedPageNum);

                backToMainPage();
                Toast.makeText(getApplicationContext(), "page inserted", Toast.LENGTH_SHORT).show();
            }
        } catch (final BusinessException e) {
            Messages.fatalError(this, e.getMessage());
        }
    }

    public void backToMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}