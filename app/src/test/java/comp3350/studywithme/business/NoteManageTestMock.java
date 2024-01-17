// CLASS: NoteManageTestMock
//
// REMARKS: unit test for NoteManageTest using mock
//
//------------------------------------------------------------

package comp3350.studywithme.business;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import comp3350.studywithme.business.manage.BusinessException;
import comp3350.studywithme.business.manage.NoteManage;
import comp3350.studywithme.business.manage.NoteNotFoundException;
import comp3350.studywithme.business.manage.PageNumberOutOfRangeException;
import comp3350.studywithme.objects.Note;
import comp3350.studywithme.persistence.NotesDatabase;
import comp3350.studywithme.persistence.PagesDatabase;

public class NoteManageTestMock {

    private NoteManageable noteManageable;
    private PageManageable pageManageable;
    private NotesDatabase notesDatabase;
    private PagesDatabase pagesDatabase;

    @Before
    public void setup() {
        notesDatabase = mock(NotesDatabase.class);
        pagesDatabase = mock(PagesDatabase.class);
        noteManageable = new NoteManage(notesDatabase, pagesDatabase);
        pageManageable = new NoteManage(notesDatabase, pagesDatabase);
    }

    @Test
    public void testCreateNoteValid() {
        System.out.println("\ntestCreateNoteValid");

        Note note = new Note("chapter1", "this is the content of the chapter1");

        when(notesDatabase.insertNote(any(Note.class))).thenReturn(true);
        assertTrue(noteManageable.createNote(note.getTitle(), note.getPageContent(1)));
        verify(notesDatabase).insertNote(any(Note.class));

        System.out.println("Finished testCreateNoteValid\n");
    }

    @Test
    public void testCreateNoteInvalid() {
        System.out.println("\ntestCreateNoteInvalid");

        String content = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        Note note = new Note("chapter1", content);

        when(notesDatabase.insertNote(any(Note.class))).thenReturn(false);
        assertFalse(noteManageable.createNote(note.getTitle(), note.getPageContent(1)));

        System.out.println("Finished testCreateNoteInvalid\n");
    }

    @Test
    public void testDeleteNoteValid() {
        System.out.println("\ntestDeleteNoteValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        notesDatabase.deleteNote(any(Note.class));

        try {
            assertTrue(noteManageable.deleteNote(note.getTitle()));
            verify(notesDatabase).deleteNote(any(Note.class));
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testDeleteNoteValid\n");
    }

    @Test
    public void testDeleteNoteInvalid() {
        System.out.println("\ntestDeleteNoteInvalid");

        assertThrows(NoteNotFoundException.class, () -> when(noteManageable.deleteNote(anyString())).thenThrow(new NoteNotFoundException()));

        System.out.println("Finished testDeleteNoteInvalid\n");
    }

    @Test
    public void testGetNoteValid() {
        System.out.println("\ntestGetNoteValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);

        try {
            assertEquals(note, noteManageable.getNote(note.getTitle()));
            verify(notesDatabase).searchForNote(note.getTitle());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetNoteValid\n");
    }

    @Test
    public void testGetNoteInvalid() {
        System.out.println("\ntestGetNoteInvalid");

        when(notesDatabase.searchForNote(anyString())).thenReturn(null);
        assertThrows(NoteNotFoundException.class, () -> when(noteManageable.getNote(anyString())).thenThrow(new NoteNotFoundException()));

        System.out.println("Finished testGetNoteInvalid\n");
    }

    @Test
    public void testChangeTitleValid() {
        System.out.println("\ntestChangeTitleValid");

        Note note = new Note("T1", "C1");

        try {
            assertTrue(noteManageable.changeTitle("C1", "C1"));
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);

        try {
            assertTrue(noteManageable.changeTitle(note.getTitle(), "T New"));
            verify(notesDatabase).changeTitle(note.getTitle(), "T New");
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testChangeTitleValid\n");
    }

    @Test
    public void testChangeTitleInvalid() {
        System.out.println("\ntestChangeTitleInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> when(noteManageable.changeTitle("T1", "T2")).thenThrow(new NoteNotFoundException()));

        System.out.println("Finished testChangeTitleInvalid\n");
    }

    @Test
    public void testSwitchFavoriteValid(){
        System.out.println("\ntestSwitchFavoriteValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);

        try {
            assertTrue(noteManageable.switchFavorite(note.getTitle()));
            verify(notesDatabase).switchFavorite(note.getTitle());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testSwitchFavoriteValid\n");
    }

    @Test
    public void testSwitchFavoriteInvalid(){
        System.out.println("\ntestSwitchFavoriteInvalid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> when(noteManageable.switchFavorite(note.getTitle())).thenThrow(new NoteNotFoundException()));

        System.out.println("Finished testSwitchFavoriteInvalid\n");
    }

    @Test
    public void testGetFavoriteValid(){
        System.out.println("\ntestGetFavoriteValid");

        Note note = new Note("T1", "C1");
        Note note1 = new Note("a","c");
        note1.setFavorite(true);

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.isThereTitle(note1.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        when(notesDatabase.searchForNote(note1.getTitle())).thenReturn(note1);

        try {
            assertFalse(noteManageable.getFavorite(note.getTitle()));
            assertTrue(noteManageable.getFavorite(note1.getTitle()));
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetFavoriteValid\n");
    }

    @Test
    public void testGetFavoriteInvalid(){
        System.out.println("\ntestGetFavoriteInvalid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> when(noteManageable.getFavorite(note.getTitle())).thenThrow(new NoteNotFoundException()));

        System.out.println("Finished testGetFavoriteInvalid\n");
    }

    @Test
    public void testChangePageContentValid() {
        System.out.println("\ntestChangePageContentValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);

        try {
            assertTrue(pageManageable.changePageContent(note.getTitle(), "C New", 1));
            verify(pagesDatabase).changePageContent(note.getTitle(), "C New", 1);
        } catch (BusinessException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testChangePageContentValid\n");
    }

    @Test
    public void testChangePageContentInvalid() {
        System.out.println("\ntestChangePageContentInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> pageManageable.changePageContent(anyString(), "C New", 1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertThrows(PageNumberOutOfRangeException.class, () -> pageManageable.changePageContent(note.getTitle(), "C New", 3));

        System.out.println("Finished testChangePageContentInvalid\n");
    }

    @Test
    public void testInsertPageBeforeValid() {
        System.out.println("\ntestInsertPageBeforeValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);

        try {
            assertTrue(pageManageable.insertPageBefore(note.getTitle(), "C2", 1));
            verify(pagesDatabase).insertPageBefore(note.getTitle(), 1, "C2");
        } catch (BusinessException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testInsertPageBeforeValid\n");
    }

    @Test
    public void testInsertPageBeforeInvalid() {
        System.out.println("\ntestInsertPageBeforeInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> pageManageable.insertPageBefore("Any Title", "Any Content", 1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle("T1")).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertThrows(PageNumberOutOfRangeException.class, () -> pageManageable.insertPageBefore("T1", "C2", 3));

        System.out.println("Finished testInsertPageBeforeInvalid\n");
    }

    @Test
    public void testInsertPageAfterValid() {
        System.out.println("\ntestInsertPageAfterValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);

        try {
            assertTrue(pageManageable.insertPageAfter(note.getTitle(), "C2", 1));
            verify(pagesDatabase).insertPageAfter(note.getTitle(), 1, "C2");
        } catch (BusinessException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testInsertPageAfterValid\n");
    }

    @Test
    public void testInsertPageAfterInvalid() {
        System.out.println("\ntestInsertPageAfterInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> pageManageable.insertPageAfter("Any Title", "Any Content", 1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle("T1")).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertThrows(PageNumberOutOfRangeException.class, () -> pageManageable.insertPageAfter("T1", "C2", 3));

        System.out.println("Finished testInsertPageAfterInvalid\n");
    }

    @Test
    public void testDeletePageValid(){
        System.out.println("\ntestDeletePageValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);

        try {
            assertTrue(pageManageable.deletePage(note.getTitle(), 1));
            verify(pagesDatabase).deletePage(note.getTitle(),1);
        } catch (BusinessException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testDeletePageValid\n");
    }

    @Test
    public void testDeletePageInvalid(){
        System.out.println("\ntestDeletePageInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> pageManageable.deletePage(anyString(),1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle("T1")).thenReturn(true);
        when(notesDatabase.searchForNote("T1")).thenReturn(note);
        assertThrows(PageNumberOutOfRangeException.class, () -> pageManageable.deletePage(note.getTitle(),3));

        System.out.println("Finished testDeletePageInvalid\n");
    }

    @Test
    public void testGetPrevPage(){
        System.out.println("\ntestGetPrevPage");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertNull(pageManageable.getPrevPage(anyString(),1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertNull(pageManageable.getPrevPage(note.getTitle(),1));

        note.addPage("C2", 2, "T1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertEquals("C1", pageManageable.getPrevPage("T1", 2).getContent());

        System.out.println("Finished testGetPrevPage\n");
    }

    @Test
    public void testGetNextPage(){
        System.out.println("\ntestGetNextPage");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertNull(pageManageable.getNextPage(anyString(),1));

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertNull(pageManageable.getNextPage(note.getTitle(),1));

        note.addPage("C2", 2, "T1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(note.getTitle())).thenReturn(note);
        assertEquals("C2", pageManageable.getNextPage("T1", 1).getContent());

        System.out.println("Finished testGetNextPage\n");
    }

    @Test
    public void testUpdateDateValid() {
        System.out.println("\ntestUpdateDateValid");

        Note note = new Note("T1", "C1");

        when(notesDatabase.isThereTitle(note.getTitle())).thenReturn(true);

        try {
            noteManageable.updateDate(note.getTitle());
            verify(notesDatabase).updateDate(note.getTitle(), new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()));
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testUpdateDateValid\n");
    }

    @Test
    public void testUpdateDateInvalid() {
        System.out.println("\ntestUpdateDateInvalid");

        when(notesDatabase.isThereTitle(anyString())).thenReturn(false);
        assertThrows(NoteNotFoundException.class, () -> noteManageable.updateDate(anyString()));

        System.out.println("Finished testUpdateDateInvalid\n");
    }

    @Test
    public void testSendAllAvailableTitles(){
        System.out.println("\ntestSendAllAvailableTitles");

        when(notesDatabase.titleList()).thenReturn(null);
        assertNull(noteManageable.sendAllAvailableTitles());

        List<String> list = new ArrayList<>();
        list.add("hello");

        when(notesDatabase.titleList()).thenReturn(list);
        assertNotNull(noteManageable.sendAllAvailableTitles());

        System.out.println("Finished testSendAllAvailableTitles\n");
    }

    @Test
    public void testSendAllFavoriteTitles(){
        System.out.println("\ntestSendAllFavoriteTitles");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllFavoriteNotes()).thenReturn(list);
        assertNotNull(noteManageable.sendAllFavoriteTitles());

        when(notesDatabase.listAllFavoriteNotes()).thenReturn(null);
        assertNull(noteManageable.sendAllFavoriteTitles());

        System.out.println("Finished testSendAllFavoriteTitles\n");
    }

    @Test
    public void testSendAllAvailableNotes(){
        System.out.println("\ntestSendAllAvailableNotes");

        List<Note> list = new ArrayList<>();
        Note note = new Note("T1", "C1");
        list.add(note);

        when(notesDatabase.listAllNotes()).thenReturn(list);
        assertNotNull(noteManageable.sendAllAvailableNotes());

        when(notesDatabase.listAllNotes()).thenReturn(null);
        assertNull(noteManageable.sendAllAvailableNotes());

        System.out.println("Finished testSendAllAvailableNotes\n");
    }

    @Test
    public void testSendAllFavoriteNotes(){
        System.out.println("\ntestSendAllFavoriteNotes");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllFavoriteNotes()).thenReturn(list);
        assertNotNull(noteManageable.sendAllFavoriteNotes());

        when(notesDatabase.listAllFavoriteNotes()).thenReturn(null);
        assertNull(noteManageable.sendAllFavoriteNotes());

        System.out.println("Finished testSendAllFavoriteNotes\n");
    }

    @Test
    public void testGetDefaultRecommendation() {
        System.out.println("\ntestGetDefaultRecommendation");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllNotes()).thenReturn(list);
        when(notesDatabase.isThereTitle(list.get(0).getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(list.get(0).getTitle())).thenReturn(list.get(0));

        try {
            assertNotNull(noteManageable.getDefaultRecommendation());
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetDefaultRecommendation\n");
    }

    @Test
    public void testGetFavoriteRecommendation() {
        System.out.println("\ntestGetFavoriteRecommendation");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllNotes()).thenReturn(list);
        when(notesDatabase.isThereTitle(list.get(0).getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(list.get(0).getTitle())).thenReturn(list.get(0));

        try {
            assertNotNull(noteManageable.getFavoriteRecommendation());
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetFavoriteRecommendation\n");
    }

    @Test
    public void testGetDefaultRecommendationNotes() {
        System.out.println("\ntestGetDefaultRecommendationNotes");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllNotes()).thenReturn(list);
        when(notesDatabase.isThereTitle(list.get(0).getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(list.get(0).getTitle())).thenReturn(list.get(0));

        try {
            assertNotNull(noteManageable.getDefaultRecommendationNotes());
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetDefaultRecommendationNotes\n");
    }

    @Test
    public void testGetFavoriteRecommendationNotes() {
        System.out.println("\ntestGetFavoriteRecommendationNotes");

        List<Note> list = new ArrayList<>();
        list.add(new Note("T1", "C1"));

        when(notesDatabase.listAllNotes()).thenReturn(list);
        when(notesDatabase.isThereTitle(list.get(0).getTitle())).thenReturn(true);
        when(notesDatabase.searchForNote(list.get(0).getTitle())).thenReturn(list.get(0));

        try {
            assertNotNull(noteManageable.getFavoriteRecommendationNotes());
        } catch (ParseException e) {
            Assert.fail(e.getMessage());
        } catch (NoteNotFoundException e) {
            Assert.fail(e.getMessage());
        }

        System.out.println("Finished testGetFavoriteRecommendationNotes\n");
    }

}

