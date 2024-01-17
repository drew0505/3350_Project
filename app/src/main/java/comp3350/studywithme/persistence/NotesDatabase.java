/*
 * NotesDatabase
 *
 * PURPOSE: This interface is designed to be implemented by the NotesDatabaseStubs class
 */

package comp3350.studywithme.persistence;

import java.util.List;

import comp3350.studywithme.objects.Note;

public interface NotesDatabase {

    /* We want to search "title" note through the database and return that note.
     *
     * Parameter:
     *  String title - Title of a note that we want to search
     *
     * Return:
     *  Note object - that we wanted to search
     */
    Note searchForNote(String title);

    /* Check the note in the database that is there a note with this "title" ?
     *
     * Parameter:
     * String title - Title of a note that we want to check
     *
     * Return:
     * boolean - true if the note is in the database, otherwise it is false
     */
    boolean isThereTitle(String title);

    /* Get the list of titles from the database.
     * (Just the title)
     *
     * Return:
     * List<String> object - List of the titles
     */
    List<String> titleList();

    /* Get the list of notes from the database.
     *
     * Return:
     * List<Note> object - List of the notes
     */
    List<Note> listAllNotes();

    /* Get the list of all favorite notes.
     *
     * Return:
     * List<Note> - List of the favorite notes
     */
    List<Note> listAllFavoriteNotes();

    /* A switch for favorite the note.
     *
     * Parameter:
     * String title - the note's title
     */
    void switchFavorite(String title);

    /* Insert a new note
     *
     * Parameter:
     * Note currentNote - The new note that we want to insert.
     *
     * Return:
     * boolean - true if it successfully inserted, otherwise it is false.
     */
    boolean insertNote(Note currentNote);

    /* Delete an existing note
     *
     * Parameter:
     * Note currentNote - The note that we want to delete.
     */
    void deleteNote(Note currentNote);

    /* Change the note's title.
     *
     * Parameter:
     * String title - Title that we want to change
     * String newTitle - The new title
     */
    void changeTitle(String title, String newTitle);

    /* Updates the date in the note.
     *
     * Parameter:
     * String title - The note's title that we want to update
     * String newDate - The new date
     */
    void updateDate(String title, String newDate);

}
