package chocan;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProviderDBTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkID() {
        int id = 700000;
        ProviderDB testObj = new ProviderDB();
        assertTrue(testObj.CheckID(id));
    }

    @Test
    public void add() {
    }

    @Test
    public void update() {
    }

    @Test
    public void load() {
    }

    @Test
    public void save() {
    }
}