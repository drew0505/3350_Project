// CLASS: Services
//
// REMARKS: initiate database service
//
//------------------------------------------------------------

package comp3350.studywithme.application;

import comp3350.studywithme.persistence.NotesDatabase;
import comp3350.studywithme.persistence.PagesDatabase;
import comp3350.studywithme.persistence.hsqldb.NotesDatabaseHSQLDB;

public class Services {

    private static String dbName="/data/data/comp3350.studywithme/app_db/STWM";  //private instance, database path name
    private static NotesDatabase notesDatabase = null;                           //private static instance, notes database
    private static PagesDatabase pagesDatabase = null;                           //private static instance, pages database

    public static void main(String[] args){
        System.out.println("All done");
    }

    //static method to set database path name
    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    //static method to get the database path name
    public static String getDBPathName() {
        return dbName;
    }

    //static synchronized method to get the notes database
    public static synchronized NotesDatabase getNotesDatabase() {
        if (notesDatabase == null) {
             notesDatabase = new NotesDatabaseHSQLDB(Services.getDBPathName());
        }

        return notesDatabase;
    }

    //static synchronized method to get the pages database
    public static synchronized PagesDatabase getPagesDatabase() {
        if (pagesDatabase == null) {
            pagesDatabase = new NotesDatabaseHSQLDB(Services.getDBPathName());
        }
        return pagesDatabase;
    }

}
