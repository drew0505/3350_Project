// CLASS: NoteManage
//
// REMARKS: implementation of the NoteManageable
//
//------------------------------------------------------------

package comp3350.studywithme.business.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import comp3350.studywithme.application.Services;
import comp3350.studywithme.business.NoteManageable;
import comp3350.studywithme.business.PageManageable;
import comp3350.studywithme.objects.Note;
import comp3350.studywithme.objects.Page;
import comp3350.studywithme.persistence.NotesDatabase;
import comp3350.studywithme.persistence.PagesDatabase;

public class NoteManage implements NoteManageable, PageManageable {

    private final static String DEFAULT_TITLE = "Untitled";  //constant default title
    private final static int MAX_TITLE_LENGTH = 64;          //constant max title length
    private final static int MAX_CONTENT_LENGTH = 300;       //constant max content length
    private final static long DAY_DIFFERENCE = 86400000;     //constant long value for one day difference
    private final static long DAY_SEVEN = 7;                 //constant long value for 7 days
    private NotesDatabase notesDatabase;                     //instance variable, note database
    private PagesDatabase pagesDatabase;                     //instance variable, page database

    //constructor, create NoteManage object
    public NoteManage() {
        notesDatabase = Services.getNotesDatabase();
        pagesDatabase = Services.getPagesDatabase();
    }

    //injection constructor, create by using specific database source
    public NoteManage(final NotesDatabase notesDatabase, final PagesDatabase pagesDatabase) {
        this();
        this.notesDatabase = notesDatabase;
        this.pagesDatabase = pagesDatabase;
    }

    //method to create note with given title and content
    //if the given title is already used, then valid title
    public boolean createNote(String title, String content) {
        if (content.length() <= MAX_CONTENT_LENGTH) {
            return notesDatabase.insertNote(new Note(validTitle(title), content));
        } else {
            return false;
        }
    }

    //method to delete note with given title if there is such note in the database
    //otherwise show that the note cannot be deleted
    public boolean deleteNote(String title) throws NoteNotFoundException {
        if (notesDatabase.isThereTitle(title)) {
            notesDatabase.deleteNote(notesDatabase.searchForNote(title));
            return true;
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to returns corresponding note to the (preferably) UI
    public Note getNote(String title) throws NoteNotFoundException {
        Note temp = notesDatabase.searchForNote(title);

        if (temp == null) {
            throw new NoteNotFoundException();
        } else {
            return temp;
        }

    }

    //method to change the title name of the note with given name
    //change title if and only if the new title is not the same as the original one
    //and there exists one note with the given original title
    public boolean changeTitle(String title, String newTitle) throws NoteNotFoundException {
        if (title.equals(newTitle)) {
            return true;
        } else {
            if (notesDatabase.isThereTitle(title)) {
                notesDatabase.changeTitle(title, validTitle(newTitle));
                return true;
            } else {
                throw new NoteNotFoundException();
            }
        }
    }

    //method to switch favorite status
    public boolean switchFavorite(String title) throws NoteNotFoundException {
        if (notesDatabase.isThereTitle(title)) {
            notesDatabase.switchFavorite(title);
            return true;
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to get the favorite status
    public boolean getFavorite(String title) throws NoteNotFoundException {
        if (notesDatabase.isThereTitle(title)) {
            return notesDatabase.searchForNote(title).getFavorite();
        } else {
            throw new NoteNotFoundException();
        }
    }//getFavorite()

    //method to change page content with given page number
    //if and only if there exists note with given title
    //and page number is in the range
    public boolean changePageContent(String title, String newContent, int pageNum) throws BusinessException {
        if (notesDatabase.isThereTitle(title)) {
            if (pageNum >= 1 && pageNum <= notesDatabase.searchForNote(title).getNumPages()) {
                pagesDatabase.changePageContent(title, newContent, pageNum);
                return true;
            } else {
                throw new PageNumberOutOfRangeException();
            }
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to insert page before the current page
    public boolean insertPageBefore(String title, String newContent, int pageNum) throws BusinessException {
        if (notesDatabase.isThereTitle(title)) {
            if (pageNum >= 1 && pageNum <= notesDatabase.searchForNote(title).getNumPages()) {
                pagesDatabase.insertPageBefore(title, pageNum, newContent);
                return true;
            } else {
                throw new PageNumberOutOfRangeException();
            }
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to insert page after the current page
    public boolean insertPageAfter(String title, String newContent, int pageNum) throws BusinessException {
        if (notesDatabase.isThereTitle(title)) {
            if (pageNum >= 1 && pageNum <= notesDatabase.searchForNote(title).getNumPages()) {
                pagesDatabase.insertPageAfter(title, pageNum, newContent);
                return true;
            } else {
                throw new PageNumberOutOfRangeException();
            }
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to delete the current page
    public boolean deletePage(String title, int pageNum) throws BusinessException {
        if (notesDatabase.isThereTitle(title)) {
            if (pageNum >= 1 && pageNum <= notesDatabase.searchForNote(title).getNumPages()) {
                pagesDatabase.deletePage(title, pageNum);
                return true;
            } else {
                throw new PageNumberOutOfRangeException();
            }
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to get the previous page
    public Page getPrevPage(String title, int currPageNum) {
        if (notesDatabase.isThereTitle(title)) {
            if ( currPageNum > 1 && currPageNum <= notesDatabase.searchForNote(title).getPageList().size()) {
                return notesDatabase.searchForNote(title).getPageList().get(currPageNum-2);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //method to get the next page
    public Page getNextPage(String title, int currPageNum) {
        if (notesDatabase.isThereTitle(title)) {
            if (currPageNum >= 1 && currPageNum < notesDatabase.searchForNote(title).getNumPages()) {
                return notesDatabase.searchForNote(title).getPageList().get(currPageNum);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    //method to update the date of the note
    public void updateDate(String title) throws NoteNotFoundException {
        if (notesDatabase.isThereTitle(title)) {
            notesDatabase.updateDate(title, new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date()));
        } else {
            throw new NoteNotFoundException();
        }
    }

    //method to send back the list of titles to ui
    public List<String> sendAllAvailableTitles() {
        return notesDatabase.titleList();
    }

    //method to send back the list of favorite titles to ui
    public List<String> sendAllFavoriteTitles() {
        if (sendAllFavoriteNotes() != null) {
            List<String> result = new ArrayList<>();

            for (int i=0; i<sendAllFavoriteNotes().size(); i++) {
                result.add(sendAllFavoriteNotes().get(i).getTitle());
            }

            return result;
        } else {
            return null;
        }
    }

    //method to send list of notes to ui
    public List<Note> sendAllAvailableNotes() {
        return notesDatabase.listAllNotes();
    }

    //method to send list of favorite notes to ui
    public List<Note> sendAllFavoriteNotes() {
        return notesDatabase.listAllFavoriteNotes();
    }

    //helper method to validate the tittle
    //return the validated unique title
    //title length should be 64 characters long
    private String validTitle(String title) {
        if (title.length() <= MAX_TITLE_LENGTH && title.length() > 0) {
            if (title.contains("\n")) {
                String[] temp = title.split("\n");
                String result = "";
                for (int i=0; i<temp.length; i++) {
                    result += temp[i];
                }
                if (result.trim().isEmpty()) {
                    return checkDuplicateTitle(DEFAULT_TITLE);
                } else {
                    return checkDuplicateTitle(result.trim());
                }
            } else {
                if (title.trim().isEmpty()) {
                    return checkDuplicateTitle(DEFAULT_TITLE);
                } else {
                    return checkDuplicateTitle(title.trim());
                }
            }
        } else {
            return checkDuplicateTitle(DEFAULT_TITLE);
        }
    }

    //helper method to check the duplicated title
    //return the unique title as the String
    //where title is the unique in the database
    private String checkDuplicateTitle(String title) {
        if (notesDatabase.isThereTitle(title)) {
            int i = 1;
            String result = title + " (" + i + ")";

            while (notesDatabase.isThereTitle(result)) {
                i++;
                result = title + " (" + i + ")";
            }
            return result;
        } else {
            return title;
        }
    }

    //method to get the the list of note titles needed to be recommended from all notes
    public List<String> getDefaultRecommendation() throws ParseException, NoteNotFoundException {
        List<String> recommendationList = new ArrayList<>();

        for (int i=0; i<notesDatabase.listAllNotes().size(); i++) {
            if (checkRecommendation(notesDatabase.listAllNotes().get(i).getTitle())) {
                recommendationList.add(notesDatabase.listAllNotes().get(i).getTitle());
            }
        }
        return recommendationList;
    }

    //method to get the the list of note titles needed to be recommended from all favorite notes
    public List<String> getFavoriteRecommendation() throws ParseException, NoteNotFoundException {
        List<String> recommendationList = new ArrayList<>();

        for (int i=0; i<notesDatabase.listAllNotes().size(); i++) {
            if (notesDatabase.listAllNotes().get(i).getFavorite() && checkRecommendation(notesDatabase.listAllNotes().get(i).getTitle())) {
                recommendationList.add(notesDatabase.listAllNotes().get(i).getTitle());
            }
        }
        return recommendationList;
    }

    //method to get the the list of notes needed to be recommended from all notes
    @Override
    public List<Note> getDefaultRecommendationNotes() throws ParseException, NoteNotFoundException {
        List<Note> recommendationList = new ArrayList<>();

        for (int i=0; i<notesDatabase.listAllNotes().size(); i++) {
            if (checkRecommendation(notesDatabase.listAllNotes().get(i).getTitle())) {
                recommendationList.add(notesDatabase.listAllNotes().get(i));
            }
        }
        return recommendationList;
    }

    //method to get the the list of notes needed to be recommended from all favorite notes
    @Override
    public List<Note> getFavoriteRecommendationNotes() throws ParseException, NoteNotFoundException {
        List<Note> recommendationList = new ArrayList<>();

        for (int i=0; i<notesDatabase.listAllNotes().size(); i++) {
            if (notesDatabase.listAllNotes().get(i).getFavorite() && checkRecommendation(notesDatabase.listAllNotes().get(i).getTitle())) {
                recommendationList.add(notesDatabase.listAllNotes().get(i));
            }
        }
        return recommendationList;
    }

    //helper method to check if the note with given title should be recommended or not
    private boolean checkRecommendation(String title) throws ParseException, NoteNotFoundException {
        if (notesDatabase.isThereTitle(title)) {
            String currDateStr = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
            String targetDateStr = notesDatabase.searchForNote(title).getDate();
            Date currDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(currDateStr);
            Date targetDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(targetDateStr);
            long difference = (currDate.getTime() - targetDate.getTime()) / DAY_DIFFERENCE;

            return difference >= DAY_SEVEN;
        } else {
            throw new NoteNotFoundException();
        }
    }

}