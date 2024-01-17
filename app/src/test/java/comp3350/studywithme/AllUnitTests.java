package comp3350.studywithme;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studywithme.business.NoteManageTestMock;
import comp3350.studywithme.objects.NoteTest;
import comp3350.studywithme.objects.PageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        NoteTest.class,
        PageTest.class,
        NoteManageTestMock.class
})


public class AllUnitTests {



}
