// CLASS: Page
//
// REMARKS: Page object, create Page, get/set private fields
//
//------------------------------------------------------------

package comp3350.studywithme.objects;

public class Page {

    private String content;    //instance variable content
    private int pageNum;       //instance variable page number
    private String noteTitle;  //instance variable note title linked to the note

    //constructor, create one page with given content
    public Page(String newContent) {
        content = newContent;
        pageNum = 0;
        noteTitle = null;
    }

    //specific constructor, create one page with given content, page number and title
    public Page(String newContent, int newPageNum, String newNoteTitle) {
        content = newContent;
        pageNum = newPageNum;
        noteTitle = newNoteTitle;
    }

    //content getter
    public String getContent() {
        return content;
    }

    //page number getter
    public int getPageNum() {
        return pageNum;
    }

    //note title getter
    public String getNoteTitle() {
        return noteTitle;
    }

    //content setter
    public void setContent(String newContent) {
        content = newContent;
    }

    //page number setter
    public void setPageNum(int newPageNum) {
        pageNum = newPageNum;
    }

    //note title setter
    public void setNoteTitle(String newNoteTitle) {
        noteTitle = newNoteTitle;
    }

}

