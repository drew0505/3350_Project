package comp3350.studywithme.business_persistence;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.*;

import comp3350.studywithme.business.manage.BusinessException;
import comp3350.studywithme.business.manage.NoteManage;
import comp3350.studywithme.business.manage.NoteNotFoundException;
import comp3350.studywithme.objects.Note;
import comp3350.studywithme.persistence.NotesDatabase;
import comp3350.studywithme.persistence.PagesDatabase;
import comp3350.studywithme.persistence.hsqldb.NotesDatabaseHSQLDB;
import comp3350.studywithme.utilities.TestUtilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class NoteManageIT {

    private NoteManage noteManage;
    private NotesDatabase notesDatabase;
    private PagesDatabase pagesDatabase;

    private File tempDB;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtilities.copyDB();

        final NotesDatabase notesDatabase = new NotesDatabaseHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final PagesDatabase pagesDatabase = new NotesDatabaseHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));

        //default values
        notesDatabase.insertNote(new Note("T1", "C1"));
        notesDatabase.insertNote(new Note("T2", "C2"));
        notesDatabase.insertNote(new Note("T3", "C3"));
        notesDatabase.switchFavorite("T2");//T2 is favorite by default



        this.noteManage = new NoteManage(notesDatabase, pagesDatabase);


    }




    @Test
    public void testCreateNote(){
        //by default there is 3 notes in the database
        List<String> noteList = noteManage.sendAllAvailableTitles();
        assertEquals(3, noteList.size());

        //we don't care about behavior of functions here
        noteManage.createNote("New", "New content");

        noteList = noteManage.sendAllAvailableTitles();
        assertEquals(4, noteList.size());

        System.out.println("Finished test createNote");


    }//testCreateNote()


    @Test
    public void testDeleteNote() throws NoteNotFoundException {
        //by default there is 3 notes in the database
        List<String> noteList = noteManage.sendAllAvailableTitles();
        assertEquals(3, noteList.size());

        //we don't care about behavior of functions here
        noteManage.deleteNote("T3");

        noteList = noteManage.sendAllAvailableTitles();
        assertEquals(2, noteList.size());

        System.out.println("Finished test deleteNote");


    }//testCreateNote()

    @Test
    public void testGetNote() throws NoteNotFoundException {
        //by default there is 3 notes in the database
        List<String> noteList = noteManage.sendAllAvailableTitles();
        assertEquals(3, noteList.size());

        //we don't care about behavior of functions here
        assertNotNull(noteManage.getNote("T3"));

        System.out.println("Finished test getNote");


    }//testGetNote()

    @Test
    public void testChangeTitle() throws NoteNotFoundException {
        //by default there is 3 notes in the database
        List<String> noteList = noteManage.sendAllAvailableTitles();
        assertEquals(3, noteList.size());

        //we don't care about behavior of functions here
        noteManage.changeTitle("T3", "Changed title");
        assertNotNull(noteManage.getNote("Changed title"));

        System.out.println("Finished test changeTitle");


    }//changeTitle()

    @Test
    public void testSwitchFavorite() throws NoteNotFoundException {
        //by default there is 3 notes in the database

        noteManage.switchFavorite("T2");
        assertFalse(noteManage.getFavorite("T2"));//it will become false (default was true)

        System.out.println("Finished test switchFavorite");


    }//testSwitchFavorite()

    @Test
    public void testGetFavorite() throws NoteNotFoundException {
        //by default there is 3 notes in the database


        assertTrue(noteManage.getFavorite("T2"));//it will become false (default was true)

        System.out.println("Finished test getFavorite");


    }//testGetFavorite()

//    @Test
//    public void testUpdateDate() throws NoteNotFoundException {
//        //by default there is 3 notes in the database
//
//
//
//
//    }//testUpdateDate()


    @Test
    public void testSendAllAvailableTitles() {
        //by default there is 3 notes in the database

        List<String> noteList = noteManage.sendAllAvailableTitles();
        assertEquals(3, noteList.size());//it will become false (default was true)

        System.out.println("Finished test sendAllAvailableTitles");




    }//testSendAllAvailableTitles()


    @Test
    public void testSendAllFavoriteTitles()   {
        //by default there is 3 notes in the database

        List<String> favoriteList = noteManage.sendAllFavoriteTitles();
        assertEquals(1, favoriteList.size());//it will become false (default was true)

        System.out.println("Finished test sendAllFavoriteTitles");


    }//testSendAllFavoriteTitles()

    @Test
    public void testChangePageContent() throws BusinessException {
        //by default there is 3 notes in the database

        assertEquals("C1", noteManage.getNote("T1").getPageContent(1));
        assertTrue(noteManage.changePageContent("T1", "New content",1));
        assertEquals("New content", noteManage.getNote("T1").getPageContent(1));
        System.out.println("Finished test changePageContent");


    }//testChangePageContent()

    @Test
    public void testInsertPageBefore() throws BusinessException {
        //by default there is 3 notes in the database

        assertEquals("C1", noteManage.getNote("T1").getPageContent(1));
        assertTrue(noteManage.insertPageBefore("T1", "First page",1));
        assertEquals("First page", noteManage.getNote("T1").getPageContent(1));
        assertEquals("C1", noteManage.getNote("T1").getPageContent(2));
        System.out.println("Finished test insertPageBefore");


    }//testInsertPageBefore()

    @Test
    public void testInsertPageAfter() throws BusinessException {
        //by default there is 3 notes in the database

        assertEquals("C1", noteManage.getNote("T1").getPageContent(1));
        assertTrue(noteManage.insertPageBefore("T1", "First page",1));
        assertEquals("First page", noteManage.getNote("T1").getPageContent(1));
        assertEquals("C1", noteManage.getNote("T1").getPageContent(2));
        System.out.println("Finished test insertPageAfter");


    }//testInsertPageAfter()

    @Test
    public void testDeletePage() throws BusinessException {
        //by default there is 3 notes in the database

        noteManage.deletePage("T1", 1);
        assertNull(noteManage.getNote("T1").getPageContent(1));

        System.out.println("Finished test deletePage");


    }//testDeletePage()

    @Test
    public void testGetPrevPage() throws BusinessException {
        //by default there is 3 notes in the database
        assertTrue(noteManage.insertPageAfter("T1", "Second page", 1));

        assertEquals("C1",noteManage.getPrevPage("T1", 2).getContent());

        System.out.println("Finished test getPrevPage");


    }//testGetPrevPage()

    @Test
    public void testGetNextPage() throws BusinessException {
        //by default there is 3 notes in the database
        assertTrue(noteManage.insertPageAfter("T1", "Second page", 1));

        assertEquals("Second page",noteManage.getNextPage("T1", 1).getContent());

        System.out.println("Finished test getNextPage");


    }//testGetNextPage()



    @After
    public void destroy() {
        // reset DB
        this.tempDB.delete();
    }

}//NoteManageIT{}
