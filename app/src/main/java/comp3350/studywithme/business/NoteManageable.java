// CLASS: Interface NoteManageable
//
// REMARKS: interface of the NoteManage
//
//------------------------------------------------------------

package comp3350.studywithme.business;

import java.text.ParseException;
import java.util.List;

import comp3350.studywithme.business.manage.NoteNotFoundException;
import comp3350.studywithme.objects.Note;

public interface NoteManageable {

    //interface to create a note with given name and content
    boolean createNote(String title, String content);

    //interface to delete a note
    boolean deleteNote(String title) throws NoteNotFoundException;

    //interface to get note with given title
    Note getNote(String title) throws NoteNotFoundException;

    //interface to change title
    boolean changeTitle(String title, String newTitle) throws NoteNotFoundException;

    //interface to switch favorite
    boolean switchFavorite(String title) throws NoteNotFoundException;

    //interface to get favorite
    boolean getFavorite(String title) throws NoteNotFoundException;

    void updateDate(String title) throws NoteNotFoundException;

    //interface to send the list of titles to UI
    List<String> sendAllAvailableTitles();

    List<String> sendAllFavoriteTitles();

    //interface to send all notes to UI
    List<Note> sendAllAvailableNotes();

    //interface to send all favorite notes to UI
    List<Note> sendAllFavoriteNotes();

    //interface to get the String list of default recommendation note titles
    List<String> getDefaultRecommendation() throws ParseException, NoteNotFoundException;

    //interface to get the String list of favorite recommendation note titles
    List<String> getFavoriteRecommendation() throws ParseException, NoteNotFoundException;

    //interface to get the list of default recommendation notes
    List<Note> getDefaultRecommendationNotes() throws ParseException, NoteNotFoundException;

    //interface to get the list of favorite recommendation notes
    List<Note> getFavoriteRecommendationNotes() throws ParseException, NoteNotFoundException;

}
