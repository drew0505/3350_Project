// CLASS: Interface PageManageable
//
// REMARKS: interface of the NoteManage
//
//------------------------------------------------------------

package comp3350.studywithme.business;

import comp3350.studywithme.business.manage.BusinessException;
import comp3350.studywithme.objects.Page;

public interface PageManageable {

    //interface to change the page content with given number
    boolean changePageContent(String title, String newContent, int pageNum) throws BusinessException;

    //interface to insert page before the current page
    boolean insertPageBefore(String title, String newContent, int pageNum) throws BusinessException;

    //interface to insert page after the current page
    boolean insertPageAfter(String title, String newContent, int pageNum) throws BusinessException;

    //interface to delete current page
    boolean deletePage(String title, int pageNum) throws BusinessException;

    //interface to get previous page
    Page getPrevPage(String title, int pageNum);

    //interface to get next page
    Page getNextPage(String title, int pageNum);

}
