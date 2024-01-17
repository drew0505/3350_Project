// CLASS: NoteTest
//
// REMARKS: unit test for Note object
//
//------------------------------------------------------------

package comp3350.studywithme.objects;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.junit.Test;

public class NoteTest {

    @Test
    public void testNoteConstructorV1() {
        Note note;

        System.out.println("\nStarting testNoteConstructorV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        System.out.println("Finished testNoteConstructorV1\n");
    }

    @Test
    public void testNoteConstructorV2() {
        Note note;

        System.out.println("\nStarting testNoteConstructorV2");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        System.out.println("Finished testNoteConstructorV2\n");
    }

    @Test
    public void testSetTitleV1() {
        Note note;

        System.out.println("\nStarting testSetTitleV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        note.setTitle("Test Changing Title");
        assertEquals("Title should be Test Changing Title", "Test Changing Title", note.getTitle());

        System.out.println("Finished testSetTitleV1\n");
    }

    @Test
    public void testSetTitleV2() {
        Note note;

        System.out.println("\nStarting testSetTitleV2");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        note.setTitle("Test Changing Title");
        assertEquals("Title should be Test Changing Title", "Test Changing Title", note.getTitle());

        System.out.println("Finished testSetTitleV2\n");
    }

    @Test
    public void testSetDateV1() {
        Note note;

        System.out.println("\nStarting testSetDateV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        note.setDate("08-12-2000");
        assertEquals("Date should be 08-12-2000", "08-12-2000", note.getDate());

        System.out.println("Finished testSetDateV1\n");
    }

    @Test
    public void testSetDateV2() {
        Note note;

        System.out.println("\nStarting testSetDateV2");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        note.setDate("08-12-2000");
        assertEquals("Date should be 08-12-2000", "08-12-2000", note.getDate());

        System.out.println("Finished testSetDateV2\n");
    }

    @Test
    public void testSetFavoriteV1() {
        Note note;

        System.out.println("\nStarting testSetFavoriteV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        note.setFavorite(true);
        assertTrue("Favorite should be true", note.getFavorite());

        System.out.println("Finished testSetFavoriteV1\n");
    }

    @Test
    public void testSetFavoriteV2() {
        Note note;

        System.out.println("\nStarting testSetFavoriteV2");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        note.setFavorite(true);
        assertTrue("Favorite should be true", note.getFavorite());

        System.out.println("Finished testSetFavoriteV2\n");
    }

    @Test
    public void testSwitchFavoriteV1() {
        Note note;

        System.out.println("\nStarting testSwitchFavoriteV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        note.switchFavorite();
        assertTrue("Favorite should be true", note.getFavorite());

        System.out.println("Finished testSwitchFavoriteV1\n");
    }

    @Test
    public void testSwitchFavoriteV2() {
        Note note;

        System.out.println("\nStarting testSwitchFavoriteV3");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        note.switchFavorite();
        assertTrue("Favorite should be true", note.getFavorite());

        System.out.println("Finished testSwitchFavoriteV2\n");
    }

    @Test
    public void testAddPageV1() {
        Note note;

        System.out.println("\nStarting testAddPageV1");

        note = new Note();
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Untitled", "Untitled", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertEquals("There should be no page in the note", 0, note.getPageList().size());

        note.addPage("New Content", 1, "New Note Title");
        assertEquals("Page 1 content should be New Content", "New Content", note.getPageContent(1));
        assertEquals("Page 1 number should be 1", 1, note.getPageList().get(0).getPageNum());
        assertEquals("Page 1 note title should be New Note Title", "New Note Title", note.getPageList().get(0).getNoteTitle());

        assertEquals("Page list should contain 1 page", 1, note.getNumPages());

        System.out.println("Finished testAddPageV1\n");
    }

    @Test
    public void testAddPageV2() {
        Note note;

        System.out.println("\nStarting testAddPageV2");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        note.addPage("New Content", 2, "New Note Title");
        assertEquals("Page 2 content should be New Content", "New Content", note.getPageContent(2));
        assertEquals("Page 2 number should be 1", 2, note.getPageList().get(1).getPageNum());
        assertEquals("Page 2 note title should be New Note Title", "New Note Title", note.getPageList().get(1).getNoteTitle());

        note.addPage("New Content", 3, "New Note Title");
        assertEquals("Page 3 content should be New Content", "New Content", note.getPageContent(3));
        assertEquals("Page 3 number should be 1", 3, note.getPageList().get(2).getPageNum());
        assertEquals("Page 3 note title should be New Note Title", "New Note Title", note.getPageList().get(2).getNoteTitle());

        assertEquals("Page list should contain 3 page", 3, note.getNumPages());

        System.out.println("Finished testAddPageV2\n");
    }

    @Test
    public void testInvalidCases() {
        Note note;

        System.out.println("\nStarting testInvalidCases");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        assertNull("Page number not exist", note.getPageContent(7));

        System.out.println("Finished testInvalidCases\n");
    }

    @Test
    public void testToString() {
        Note note;

        System.out.println("\nStarting testInvalidCases");

        note = new Note("Test Title", "Test Content");
        assertNotNull("Note should be created", note);
        assertEquals("Title should be Test Title", "Test Title", note.getTitle());
        assertEquals("Date should be current date", new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()), note.getDate());
        assertFalse("Favorite status should be false", note.getFavorite());
        assertNotNull("There should be one page in the note", note.getPageList());
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0));
        assertNotNull("There should be one blank page in the note", note.getPageList().get(0).getContent());
        assertEquals("Page content should be Test Content", "Test Content", note.getPageList().get(0).getContent());

        System.out.println(note.toString());

        System.out.println("Finished testInvalidCases\n");
    }

}
