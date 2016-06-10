package pl.martialdb.app.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import pl.martialdb.app.common.BaseModel;
import pl.martialdb.app.db.MartialDatabase;
import pl.martialdb.app.exceptions.ObjectNotFoundException;

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

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
       R apply(T t) throws ObjectNotFoundException;
    }

    public Throwable checkIfDeleted(CheckedFunction<MartialDatabase, BaseModel> check) {
        Throwable except = null;
        try {
            check.apply(db);
        }
        catch (ObjectNotFoundException e) {
            except = e;
        }
        return except;
    }

    public Date asDate(String dateStr) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e1) {}

        return date;
    }
}
