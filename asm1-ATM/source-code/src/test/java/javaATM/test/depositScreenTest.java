package javaATM.test;

import javaATM.GUILogic.guiLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class depositScreenTest {

    @Test
    void testAddToDeposit() {
        guiLogic deposit = new guiLogic();
        assertEquals(deposit.depositsSize(), 0);
        deposit.addToDeposit(100);
        assertEquals(deposit.depositsSize(), 1);
    }

    @Test
    void testAdd() {
        guiLogic deposit = new guiLogic();
        assertEquals(deposit.addAmount(), 0);
        deposit.addToDeposit(100);
        assertEquals(deposit.addAmount(), 100);
        deposit.addToDeposit(50);
        assertEquals(deposit.addAmount(), 150);
    }

    @Test
    void testDepositsSize() {
        guiLogic deposit = new guiLogic();
        assertEquals(deposit.depositsSize(), 0);
        deposit.addToDeposit(100);
        assertEquals(deposit.depositsSize(), 1);
    }

    @Test
    void testUndoAmount() {
        guiLogic deposit = new guiLogic();
        deposit.addToDeposit(100);
        deposit.addToDeposit(50);
        assertEquals(deposit.depositsSize(), 2);
        deposit.undoAmount();
        assertEquals(deposit.depositsSize(), 1);
    }

    @Test
    void testResetAmount() {
        guiLogic deposit = new guiLogic();
        deposit.addToDeposit(100);
        deposit.addToDeposit(50);
        deposit.addToDeposit(510);
        assertEquals(deposit.depositsSize(), 3);
        deposit.resetAmount();
        assertEquals(deposit.depositsSize(), 0);
    }


}
