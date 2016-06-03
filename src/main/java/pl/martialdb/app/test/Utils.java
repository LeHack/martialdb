package pl.martialdb.app.test;

import jersey.repackaged.com.google.common.base.Joiner;
import pl.martialdb.app.db.MartialDatabase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Utils {
    static final String testDb = "test.db";

    public static MartialDatabase initTestDB() {
        MartialDatabase db = null;
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:" + testDb);
            db = new MartialDatabase(c);
            loadSQLFile(c, "Schema/create.sql");
            loadSQLFile(c, "Schema/testdata.sql");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return db;
    }

    public static void loadSQLFile(Connection c, String path) throws IOException, SQLException {
        // Now feed it with our user Schema
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        List<String> statements = Arrays.asList( Joiner.on("\n").join(lines).split(";") );
        for (String stmt : statements)
            c.prepareStatement(stmt).execute();
    }

    public static void dropTestDB() {
        File f = new File(testDb);
        if (f.exists())
            f.delete();
    }
}
