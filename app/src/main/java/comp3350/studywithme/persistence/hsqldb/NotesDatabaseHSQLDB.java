/*
 * NotesDatabaseHSQLDB
 *
 * PURPOSE: This class deals with the storage of the notes object in the relational
 *          database and the retrieval of the notes from the database.
 */

package comp3350.studywithme.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.studywithme.objects.Note;
import comp3350.studywithme.objects.Page;
import comp3350.studywithme.persistence.NotesDatabase;
import comp3350.studywithme.persistence.PagesDatabase;

public class NotesDatabaseHSQLDB implements NotesDatabase, PagesDatabase {
    private final String dbPath;

    /*
     * Constructor
     */
    public NotesDatabaseHSQLDB(final String path){
        dbPath = path;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /* We want to search "title" note through the database and return that note.
     *
     * Parameter:
     *  String title - Title of a note that we want to search
     *
     * Return:
     *  Note object - that we wanted to search
     */
    @Override
    public Note searchForNote(String title) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM TITLES JOIN PUBLIC.PAGES P on TITLES.TITLEID = P.TITLEID WHERE TITLEID = ? ORDER BY PAGENUM ASC");
            st.setString(1, title);
            final ResultSet rs = st.executeQuery();

            Note newNote = new Note();
            boolean isNoteCreated = false;

            while (rs.next() ) {
                if (!isNoteCreated) {
                    newNote.setTitle(rs.getString("TITLEID"));
                    newNote.setDate(rs.getString("DATE"));
                    newNote.setFavorite(rs.getBoolean("FAVORITE"));
                    isNoteCreated = true;
                }
                newNote.addPage(rs.getString("CONTENT"), rs.getInt("PAGENUM"), rs.getString("TITLEID"));
            }

            rs.close();
            st.close();

            return newNote;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Check the note in the database that is there a note with this "title" ?
     *
     * Parameter:
     * String title - Title of a note that we want to check
     *
     * Return:
     * boolean - true if the note is in the database, otherwise it is false
     */
    @Override
    public boolean isThereTitle(String title) {
        try (Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("SELECT TITLES.TITLEID FROM TITLES WHERE TITLEID = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Get the list of titles from the database.
     * (Just the title)
     *
     * Return:
     * List<String> object - List of the titles
     */
    @Override
    public List<String> titleList() {
        List<String> resultList = new ArrayList<>();

        try (final Connection connection = connection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT TITLES.TITLEID FROM TITLES");

            while (resultSet.next()){
                resultList.add(resultSet.getString("TITLEID"));
            }

            resultSet.close();
            statement.close();

            return resultList;
        } catch (SQLException e){
            throw new DatabaseException(e);
        }
    }
    /* Get the list of notes from the database.
     *
     * Return:
     * List<Note> object - List of the notes
     */
    @Override
    public List<Note> listAllNotes(){
        List<Note> resultList = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM TITLES ORDER BY TITLEID ASC");

            while (rs.next()) {
                final String currTitle = rs.getString("TITLEID");
                final PreparedStatement st2 = c.prepareStatement("SELECT * FROM TITLES JOIN PUBLIC.PAGES P on TITLES.TITLEID = P.TITLEID WHERE TITLEID = ? ORDER BY PAGENUM ASC");
                st2.setString(1, currTitle);
                final ResultSet rs2 = st2.executeQuery();

                Note newNote = new Note();
                newNote.setTitle(rs.getString("TITLES.TITLEID"));
                newNote.setFavorite(rs.getBoolean("FAVORITE"));
                newNote.setDate(rs.getString("DATE"));

                while (rs2.next()) {
                    newNote.addPage(rs2.getString("CONTENT"), rs2.getInt("PAGENUM"), rs2.getString("TITLEID"));
                }

                resultList.add(newNote);
            }

            rs.close();
            st.close();

            return resultList;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Get the list of all favorite notes.
     *
     * Return:
     * List<Note> - List of the favorite notes
     */
    @Override
    public List<Note> listAllFavoriteNotes() {
        List<Note> resultList = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM TITLES WHERE FAVORITE = true ORDER BY TITLEID ASC");

            while (rs.next()) {
                final String currTitle = rs.getString("TITLEID");
                final PreparedStatement st2 = c.prepareStatement("SELECT * FROM TITLES JOIN PUBLIC.PAGES P on TITLES.TITLEID = P.TITLEID WHERE TITLEID = ? ORDER BY PAGENUM ASC");
                st2.setString(1, currTitle);
                final ResultSet rs2 = st2.executeQuery();

                Note newNote = new Note();
                newNote.setTitle(rs.getString("TITLES.TITLEID"));
                newNote.setFavorite(rs.getBoolean("FAVORITE"));
                newNote.setDate(rs.getString("DATE"));

                while (rs2.next()) {
                    newNote.addPage(rs2.getString("CONTENT"), rs2.getInt("PAGENUM"), rs2.getString("TITLEID"));
                }

                resultList.add(newNote);
            }

            rs.close();
            st.close();

            return resultList;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* A switch for favorite the note.
     *
     * Parameter:
     * String title - the note's title
     */
    public void switchFavorite(String title) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("SELECT FAVORITE FROM TITLES WHERE TITLEID = ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean result = rs.getBoolean("FAVORITE");
                PreparedStatement ps2 = c.prepareStatement("UPDATE TITLES SET FAVORITE = ? WHERE TITLEID = ?");
                ps2.setBoolean(1,!result);
                ps2.setString(2, title);
                ps2.executeUpdate();

                ps2.close();
            }

            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Insert a new note
     *
     * Parameter:
     * Note currentNote - The new note that we want to insert.
     *
     * Return:
     * boolean - true if it successfully inserted, otherwise it is false.
     */
    @Override
    public boolean insertNote(Note currentNote) {
        try (final Connection c = connection()) {

            final PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO TITLES VALUES (?, ?, ?)");
            preparedStatement.setString(1, currentNote.getTitle());
            preparedStatement.setString(2, currentNote.getDate());
            preparedStatement.setBoolean(3, currentNote.getFavorite());
            preparedStatement.executeUpdate();

            for (int i=0; i<currentNote.getNumPages(); i++) {
                final PreparedStatement preparedStatement2 = c.prepareStatement("INSERT INTO PAGES VALUES (?, ?, ?)");
                preparedStatement2.setString(1, currentNote.getTitle());
                preparedStatement2.setInt(2, currentNote.getPageList().get(i).getPageNum());
                preparedStatement2.setString(3, currentNote.getPageList().get(i).getContent());
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }

            preparedStatement.close();
            c.close();

            return true;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Delete an existing note
     *
     * Parameter:
     * Note currentNote - The note that we want to delete.
     */
    @Override
    public void deleteNote(Note currentNote) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("DELETE FROM TITLES WHERE TITLES.TITLEID = ?");
            ps.setString(1, currentNote.getTitle());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /* Inserting a new page before the current page
     *
     * Parameter:
     * String title - The note's title that we want to update
     * int pageNum - A new page number
     * String newContent - The new content for the new page
     */
    @Override
    public void insertPageBefore(String title, int pageNum, String newContent) {
        try (final Connection c = connection()) {
            final PreparedStatement ps = c.prepareStatement("SELECT * FROM PAGES WHERE TITLEID = ? ORDER BY PAGENUM ASC");
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            List<Page> temp = new ArrayList<>();

            while (rs.next()) {
                if (rs.getInt("PAGENUM") >= pageNum) {

                    temp.add(new Page(rs.getString("CONTENT"), (rs.getInt("PAGENUM")+1), rs.getString("TITLEID")));
                    final PreparedStatement ps2 = c.prepareStatement("DELETE FROM PAGES WHERE TITLEID = ? AND PAGENUM = ?");
                    ps2.setString(1, title);
                    ps2.setInt(2, rs.getInt("PAGENUM"));
                    ps2.executeUpdate();

                    ps2.close();
                }
            }

            rs.close();

            for (int i=0; i<temp.size(); i++) {
                final PreparedStatement ps3 = c.prepareStatement("INSERT INTO PAGES VALUES (?, ?, ?)");
                ps3.setString(1, temp.get(i).getNoteTitle());
                ps3.setInt(2, temp.get(i).getPageNum());
                ps3.setString(3, temp.get(i).getContent());
                ps3.executeUpdate();

                ps3.close();
            }

            final PreparedStatement ps4 = c.prepareStatement("INSERT INTO PAGES VALUES (?, ?, ?)");
            ps4.setString(1, title);
            ps4.setInt(2, pageNum);
            ps4.setString(3, newContent);
            ps4.executeUpdate();

            ps4.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Inserting a new page after the current page
     *
     * Parameter:
     * String title - The note's title that we want to update
     * int pageNum - A new page number
     * String newContent - The new content for the new page
     */
    @Override
    public void insertPageAfter(String title, int pageNum, String newContent) {
        try (final Connection c = connection()) {
            final PreparedStatement ps = c.prepareStatement("SELECT * FROM PAGES WHERE TITLEID = ? ORDER BY PAGENUM ASC");
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            List<Page> temp = new ArrayList<>();

            while (rs.next()) {
                if (rs.getInt("PAGENUM") > pageNum) {

                    temp.add(new Page(rs.getString("CONTENT"), (rs.getInt("PAGENUM")+1), rs.getString("TITLEID")));
                    final PreparedStatement ps2 = c.prepareStatement("DELETE FROM PAGES WHERE TITLEID = ? AND PAGENUM = ?");
                    ps2.setString(1, title);
                    ps2.setInt(2, rs.getInt("PAGENUM"));
                    ps2.executeUpdate();

                    ps2.close();
                }
            }

            rs.close();

            for (int i=0; i<temp.size(); i++) {
                final PreparedStatement ps3 = c.prepareStatement("INSERT INTO PAGES VALUES (?, ?, ?)");
                ps3.setString(1, temp.get(i).getNoteTitle());
                ps3.setInt(2, temp.get(i).getPageNum());
                ps3.setString(3, temp.get(i).getContent());
                ps3.executeUpdate();

                ps3.close();
            }

            final PreparedStatement ps4 = c.prepareStatement("INSERT INTO PAGES VALUES (?, ?, ?)");
            ps4.setString(1, title);
            ps4.setInt(2, pageNum+1);
            ps4.setString(3, newContent);
            ps4.executeUpdate();

            ps4.close();
            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Delete the page within a note
     *
     * Parameter:
     * String title - The note's title
     * int pageNum - The page that we want to delete
     */
    @Override
    public void deletePage(String title, int pageNum) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("DELETE FROM PAGES WHERE TITLEID = ? AND PAGENUM = ?");
            ps.setString(1, title);
            ps.setInt(2, pageNum);
            ps.executeUpdate();

            ps.close();

            final PreparedStatement ps2 = c.prepareStatement("SELECT * FROM PAGES WHERE TITLEID = ? ORDER BY PAGENUM ASC");
            ps2.setString(1, title);

            ResultSet rs = ps2.executeQuery();
            int currPageNum = 1;
            while (rs.next()) {
                if (currPageNum >= pageNum) {
                    final PreparedStatement ps3 = c.prepareStatement("UPDATE PAGES SET PAGENUM = ? WHERE TITLEID = ? AND PAGENUM = ?");
                    ps3.setInt(1, currPageNum);
                    ps3.setString(2, title);
                    ps3.setInt(3, rs.getInt("PAGENUM"));
                    ps3.executeUpdate();
                    ps3.close();
                }
                currPageNum++;
            }

            ps2.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Change the note's title.
     *
     * Parameter:
     * String title - Title that we want to change
     * String newTitle - The new title
     */
    @Override
    public void changeTitle(String title, String newTitle) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("UPDATE TITLES SET TITLEID = ? WHERE TITLEID = ?");
            ps.setString(1, newTitle);
            ps.setString(2, title);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Edit the page's content.
     *
     * Parameter:
     * String title - The note's title
     * String newContent - The new content
     * int pageNum - The page that we want to change its content
     */
    @Override
    public void changePageContent(String title, String newContent, int pageNum) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("UPDATE PAGES SET CONTENT = ? WHERE TITLEID = ? AND PAGENUM = ?");
            ps.setString(1, newContent);
            ps.setString(2, title);
            ps.setInt(3, pageNum);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    /* Updates the date in the note.
     *
     * Parameter:
     * String title - The note's title that we want to update
     * String newDate - The new date
     */
    @Override
    public void updateDate(String title, String newDate) {
        try (final Connection c = connection()) {
            PreparedStatement ps = c.prepareStatement("UPDATE TITLES SET DATE = ? WHERE TITLEID = ?");
            ps.setString(1, newDate);
            ps.setString(2, title);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}


