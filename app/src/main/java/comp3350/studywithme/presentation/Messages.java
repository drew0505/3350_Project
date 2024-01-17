// CLASS: Messages
//
// REMARKS: this class will show the messages on the screen
//
//------------------------------------------------------------

package comp3350.studywithme.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import comp3350.studywithme.R;

public class Messages {

    public static void fatalError(final Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.fatalError));
        alertDialog.setMessage(message);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        alertDialog.show();
    }

    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(R.string.warning));
        alertDialog.setMessage(message);

        alertDialog.show();
    }

}
