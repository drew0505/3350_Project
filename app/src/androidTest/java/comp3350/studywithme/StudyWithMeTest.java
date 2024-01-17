// CLASS: StudyWithMeTest
//
// REMARKS: system testing for the app
//
//------------------------------------------------------------

package comp3350.studywithme;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import comp3350.studywithme.presentation.MainActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudyWithMeTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testACreateNoteWithTitle() {
        onView(withId(R.id.add_button)).perform(click());

        // add the new note
        onView(withId(R.id.note_title)).perform(typeText("COMP 3350 Lecture 20"));
        //onView(withId(R.id.input_contents)).perform(typeText("Hello, here is the content"));
        closeSoftKeyboard();
        onView(withId(R.id.save_button)).perform(click());
    }

    @Test
    public void testBChangeTitle() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.note_title_2)).perform(replaceText("COMP 3350 Lecture 20 New Version"));
        closeSoftKeyboard();
        onView(withId(R.id.update_button)).perform(click());
    }

    @Test
    public void testCChangeContent() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.input_contents_2)).perform(replaceText("HAHAHAHAHAHaaaaaa, New content here"));
        closeSoftKeyboard();
        onView(withId(R.id.update_button)).perform(click());
    }

    @Test
    public void testDFavoriteNote() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.fav_button)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onData(anything()).atPosition(1).perform(click());
    }

    @Test
    public void testEUnFavoriteNote() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.fav_button)).perform(click());
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onData(anything()).atPosition(1).perform(click());
    }

    @Test
    public void testFFindNote() {
        onView(withId(R.id.search_bar)).perform(click()).perform(typeText("COMP 3350 Lecture 20 New Version"));
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());

        onView(withId(R.id.note_title_for_view)).check(matches(withText("COMP 3350 Lecture 20 New Version")));
    }

    @Test
    public void testGInsertPageAfter() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.insert_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.input_contents_3)).perform(typeText("New page added after"));
        closeSoftKeyboard();
        onView(withId(R.id.insert_after_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added after")));
    }

    @Test
    public void testHInsertPageBefore() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.insert_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.input_contents_3)).perform(typeText("New page added before"));
        closeSoftKeyboard();
        onView(withId(R.id.insert_before_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added before")));
    }

    @Test
    public void testICreateNoteWithBoth() {
        onView(withId(R.id.add_button)).perform(click());

        // add the new note
        onView(withId(R.id.note_title)).perform(typeText("COMP 3350 Lecture 21"));
        onView(withId(R.id.input_contents)).perform(typeText("Hello, here is the content"));
        closeSoftKeyboard();
        onView(withId(R.id.save_button)).perform(click());
    }

    @Test
    public void testJNextPage() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());

        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added before")));

        onView(withId(R.id.next_button)).perform(click());

        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added after")));
    }

    @Test
    public void testKPrevPage() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.prev_button)).perform(click());

        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added before")));

        onView(withId(R.id.prev_button)).perform(click());

        onView(withId(R.id.input_contents_for_view)).check(matches(withText("HAHAHAHAHAHaaaaaa, New content here")));
    }

    @Test
    public void testLPageNum() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());

        onView(withId(R.id.page_num_for_view)).check(matches(withText("Page 1")));

        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.page_num_for_view)).check(matches(withText("Page 2")));

        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.page_num_for_view)).check(matches(withText("Page 3")));
    }

    @Test
    public void testMFavoriteList() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onData(anything()).atPosition(1).perform(click());
    }

    @Test
    public void testNInsertFirst() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.insert_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.input_contents_3)).perform(typeText("New page added first"));
        closeSoftKeyboard();
        onView(withId(R.id.insert_before_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added first")));
    }

    @Test
    public void testODeletePage() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());
    }

    @Test
    public void testPInsertAfterInBetween() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.insert_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.input_contents_3)).perform(typeText("New page added after between"));
        closeSoftKeyboard();
        onView(withId(R.id.insert_after_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.input_contents_for_view)).check(matches(withText("New page added after between")));
    }

    @Test
    public void testQAllNoteList() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onData(anything()).atPosition(0).perform(click());
    }

    @Test
    public void testRDeleteNote() {
        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.notes_list)).atPosition(0).perform(click());
        onView(withId(R.id.edit_button)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.delete_button)).perform(click());

    }

}