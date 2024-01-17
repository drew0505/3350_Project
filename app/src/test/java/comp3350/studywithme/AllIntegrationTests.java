package comp3350.studywithme;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studywithme.business_persistence.NoteManageIT;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        NoteManageIT.class
})

public class AllIntegrationTests {
}
