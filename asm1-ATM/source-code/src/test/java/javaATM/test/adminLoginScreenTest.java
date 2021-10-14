package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javaATM.ATM;
import javaATM.Admin;
import javaATM.GUILogic.guiLogic;

public class adminLoginScreenTest {


    //logic tests
    @Test
    void testisIntNull() {
        assertFalse(guiLogic.isInt(null));
    }

    @Test
    void testisIntString() {
        assertFalse(guiLogic.isInt("a"));
    }

    @Test
    void testisInt() {
        assertTrue(guiLogic.isInt("1"));
    }


    @Test
    void testAdminValid() {
        Admin admin1 = new Admin("admin1",135,135);
        assertTrue(ATM.addAdmin(admin1));
        assertTrue(guiLogic.checkValidAdmin(135,135));
    }

    @Test
    void testInvalidAdmin() {
        assertFalse(guiLogic.checkValidAdmin(0,0));
    }

    @Test
    void testInvalidAdmin2() {
        //wrong id
        assertFalse(guiLogic.checkValidAdmin(0,135));
    }

    @Test
    void testInvalidAdmin3() {
        //wrong passcode
        assertFalse(guiLogic.checkValidAdmin(135,0));
    }

    @Test
    void testInvalidAdmin4() {
        //wrong id and passcode
        assertFalse(guiLogic.checkValidAdmin(0,0));
    }

}
