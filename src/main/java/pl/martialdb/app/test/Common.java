package pl.martialdb.app.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import pl.martialdb.app.db.MartialDatabase;

public class Common {
    public static MartialDatabase db;
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Utils.dropTestDB();
        db = Utils.initTestDB();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Utils.dropTestDB();
    }
}
