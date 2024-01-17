// CLASS: PageNumberOutOfRangeException
//
// REMARKS: user defined exception for logic layer,
//          sub class of BusinessException
//
//------------------------------------------------------------

package comp3350.studywithme.business.manage;

public class PageNumberOutOfRangeException extends BusinessException {
    public PageNumberOutOfRangeException() {
        super("Page number out of range");
    }
}
