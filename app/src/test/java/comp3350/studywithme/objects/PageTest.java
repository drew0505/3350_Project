// CLASS: PageTest
//
// REMARKS: unit test for Page object
//
//------------------------------------------------------------

package comp3350.studywithme.objects;

import org.junit.Test;
import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void testPageConstructorV1() {
        Page page;

        System.out.println("\nStarting testPageConstructorV1");

        page = new Page("Test Content");
        assertNotNull("Page should be created", page);
        assertEquals("Page content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 0, page.getPageNum());
        assertNull("page note title should be null", page.getNoteTitle());

        System.out.println("Finished testPageConstructorV1\n");
    }

    @Test
    public void testPageConstructorV2() {
        Page page;

        System.out.println("\nStarting testPageConstructorV2");

        page = new Page("Test Content", 10, "Test Note Title");
        assertNotNull("Page should be created", page);
        assertEquals("Page content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 10, page.getPageNum());
        assertEquals("page note title should be null", "Test Note Title", page.getNoteTitle());

        System.out.println("Finished testPageConstructorV2\n");
    }

    @Test
    public void testSetContentV1() {
        Page page;

        System.out.println("\nStarting testSetContentV1");

        page = new Page("Test Content");
        assertNotNull("Page should be created", page);
        assertEquals("Page Content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 0, page.getPageNum());
        assertNull("page note title should be null", page.getNoteTitle());

        page.setContent("Test Changing Content");
        assertNotNull("Page should be still be there", page);
        assertEquals("Page content should be Test Changing Content", "Test Changing Content", page.getContent());

        System.out.println("Finished testSetContentV1\n");
    }

    @Test
    public void testSetContentV2() {
        Page page;

        System.out.println("\nStarting testSetContentV2");

        page = new Page("Test Content", 10, "Test Note Title");
        assertNotNull("Page should be created", page);
        assertEquals("Page content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 10, page.getPageNum());
        assertEquals("page note title should be null", "Test Note Title", page.getNoteTitle());

        page.setContent("Test Changing Content");
        assertNotNull("Page should be still be there", page);
        assertEquals("Page content should be Test Changing Content", "Test Changing Content", page.getContent());

        System.out.println("Finished testSetContentV2\n");
    }

    @Test
    public void testSetPageNumV1() {
        Page page;

        System.out.println("\nStarting testSetPageNumV1");

        page = new Page("Test Content");
        assertNotNull("Page should be created", page);
        assertEquals("Page Content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 0, page.getPageNum());
        assertNull("page note title should be null", page.getNoteTitle());

        page.setPageNum(10);
        assertNotNull("Page should be still be there", page);
        assertEquals("Page number should be 10", 10, page.getPageNum());

        System.out.println("Finished testSetPageNumV1\n");
    }

    @Test
    public void testSetPageNumV2() {
        Page page;

        System.out.println("\nStarting testSetPageNumV3");

        page = new Page("Test Content", 8, "Test Note Title");
        assertNotNull("Page should be created", page);
        assertEquals("Page content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 8, page.getPageNum());
        assertEquals("page note title should be null", "Test Note Title", page.getNoteTitle());

        page.setPageNum(10);
        assertNotNull("Page should be still be there", page);
        assertEquals("Page number should be 10", 10, page.getPageNum());

        System.out.println("Finished testSetPageNumV2\n");
    }

    @Test
    public void testSetPageNoteTitleV1() {
        Page page;

        System.out.println("\nStarting testSetPageNoteTitleV2");

        page = new Page("Test Content");
        assertNotNull("Page should be created", page);
        assertEquals("Page Content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 0, page.getPageNum());
        assertNull("page note title should be null", page.getNoteTitle());

        page.setNoteTitle("Test Changing Note Title");
        assertNotNull("Page should still be there", page);
        assertEquals("Page note title should be Test Note Title", "Test Changing Note Title", page.getNoteTitle());

        System.out.println("Finished testSetPageNoteTitleV1\n");
    }

    @Test
    public void testSetPageNoteTitleV2() {
        Page page;

        System.out.println("\nStarting testSetPageNoteTitleV3");

        page = new Page("Test Content", 10, "Test Note Title");
        assertNotNull("Page should be created", page);
        assertEquals("Page content should be Test Content", "Test Content", page.getContent());
        assertEquals("page number should be 0", 10, page.getPageNum());
        assertEquals("page note title should be null", "Test Note Title", page.getNoteTitle());

        page.setNoteTitle("Test Changing Note Title");
        assertNotNull("Page should still be there", page);
        assertEquals("Page note title should be Test Note Title", "Test Changing Note Title", page.getNoteTitle());

        System.out.println("Finished testSetPageNoteTitleV2\n");
    }

}
