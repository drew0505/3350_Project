// CLASS: BusinessException
//
// REMARKS: user defined exception for logic layer, super class of
//          NoteNotFoundException and PageNumberOutOfRangeException
//
//------------------------------------------------------------

package comp3350.studywithme.business.manage;

public class BusinessException extends Exception{
    public BusinessException(final String e){
        super(e);
    }
}
