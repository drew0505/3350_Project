/*
 * PagesDatabase
 *
 * PURPOSE: This interface is designed to be implemented by the NotesDatabaseStubs class
 */

package comp3350.studywithme.persistence;

public interface PagesDatabase {

    /* Inserting a new page before the current page
     *
     * Parameter:
     * String title - The note's title that we want to update
     * int pageNum - A new page number
     * String newContent - The new content for the new page
     */
    void insertPageBefore(String title, int pageNum, String newContent);

    /* Inserting a new page after the current page
     *
     * Parameter:
     * String title - The note's title that we want to update
     * int pageNum - A new page number
     * String newContent - The new content for the new page
     */
    void insertPageAfter(String title, int pageNum, String newContent);

    /* Delete the page within a note
     *
     * Parameter:
     * String title - The note's title
     * int pageNum - The page that we want to delete
     */
    void deletePage(String title, int pageNum);

    /* Edit the page's content.
     *
     * Parameter:
     * String title - The note's title
     * String newContent - The new content
     * int pageNum - The page that we want to change its content
     */
    void changePageContent(String title, String newContent, int pageNum);

}
