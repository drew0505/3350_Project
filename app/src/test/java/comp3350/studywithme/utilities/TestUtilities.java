package comp3350.studywithme.utilities;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Files;//problem with dependencies :(

import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import comp3350.studywithme.application.Services;

public class TestUtilities {
    private static final File DB_SRC = new File("src/main/assets/db/STWM.script");

    public static File copyDB() throws IOException {
        final File target = File.createTempFile("temp-db", ".script");
        Files.copy(DB_SRC, target);
        Services.setDBPathName(target.getAbsolutePath().replace(".script", ""));
        return target;
    }
}
