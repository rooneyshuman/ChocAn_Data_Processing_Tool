package chocan;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {

    Member testMem = new Member(999,true,"Man","Fargo","Portland","OR","97230");
    @Test
    public void goLeft() {
    }

    @Test
    public void goRight() {
    }

    @Test
    public void setLeft() {
    }

    @Test
    public void setRight() {
    }

    @Test
    public void display() {
        testMem.display();
    }

    @Test
    public void checkID() {
        int testID = 999;
       //assertTrue(testMem.checkID(999));
    }

    @Test
    public void checkActivation() {
        assertFalse(testMem.checkActivation());
    }

    @Test
    public void editInfo() {
        testMem.editInfo();
    }

    @Test
    public void updateActivation() {
    }

    @Test
    public void display_status() {
    }
}