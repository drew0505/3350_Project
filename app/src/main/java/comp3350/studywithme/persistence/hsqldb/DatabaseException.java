/*
 * DatabaseException
 *
 * PURPOSE: user defined exception for database
 */

package comp3350.studywithme.persistence.hsqldb;

public class DatabaseException extends RuntimeException{
    public DatabaseException(final Exception e){
        super(e);
    }
}
