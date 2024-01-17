// CLASS: NoteNotFoundException
//
// REMARKS: user defined exception for logic layer,
//          sub class of BusinessException
//
//------------------------------------------------------------

package comp3350.studywithme.business.manage;

public class NoteNotFoundException extends BusinessException {
    public NoteNotFoundException() {
        super("Note not found");
    }
}
