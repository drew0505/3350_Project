// CLASS: Note
//
// REMARKS: Note object, create Note, get/set private fields
//
//------------------------------------------------------------

package comp3350.studywithme.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Note {

    private String title;         //instance variable title name
    private String date;          //instance variable date
    private boolean favorite;     //instance variable favorite status
    private List<Page> pageList;  //instance variable page list

    //default constructor, create an untitled note with one blank page
    public Note() {
        title = "Untitled";
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
        favorite = false;
        pageList = new ArrayList<>();
        validPageNum();
        linkNoteTitle();
    }

    //constructor, create a titled note with one page with given content
    public Note(String newTitle, String newContent) {
        title = newTitle;
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
        favorite = false;
        pageList = new ArrayList<>();
        pageList.add(new Page(newContent));
        validPageNum();
        linkNoteTitle();
    }

    //title name getter
    public String getTitle() {
        return title;
    }

    //date getter
    public String getDate() {
        return date;
    }

    //favorite status getter
    public boolean getFavorite() {
        return favorite;
    }

    //page list getter
    public List<Page> getPageList() {
        return pageList;
    }

    //title name setter
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setDate(String newDate) {
        date = newDate;
    }

    //favorite status setter
    public void setFavorite(boolean newFavorite) {
        favorite = newFavorite;
    }

    //favorite status switcher
    public void switchFavorite() {
        favorite = !favorite;
    }

    //method to add one page with given content in the end
    public void addPage(String newContent, int newPageNum, String newNoteTitle) {
        pageList.add(new Page(newContent, newPageNum, newNoteTitle));
    }

    //method to get the targeted page content
    public String getPageContent(int pageNum) {
        if (pageNum <= 0 || pageNum > pageList.size()) {
            System.out.println("Cannot get the page content: Given page number does not exist.");
            return null;
        } else {
            return pageList.get(pageNum-1).getContent();
        }
    }

    //method to get the size of the page list
    public int getNumPages() {
        return pageList.size();
    }

    //method to get title as a string
    public String toString() {
        return this.getTitle();
    }

    //helper method to validate the page number in page list
    private void validPageNum() {
        for (int i=0; i<pageList.size(); i++) {
            pageList.get(i).setPageNum(i+1);
        }
    }

    //helper method to link note title to page
    private void linkNoteTitle() {
        for (int i=0; i<pageList.size(); i++) {
            pageList.get(i).setNoteTitle(title);
        }
    }

}

