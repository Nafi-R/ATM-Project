package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import javaATM.*;

public class TransactionTest {

	// Account acc,String tty, int tnum, double tamt
	static Transaction test = new Transaction(null, "deposit", 1, 1);
	
	@Test
	void testGetAccount() {
		assertEquals(test.getAccount(), null, "failed to retrieve account correctly. Did not get null as account");
	}
	
	@Test
	void testGetTransactionType() {
		assertTrue(test.getTransactionType().equals("deposit"), "failed to get correct transaction type");
	}
	
	@Test
	void testGetTransactionNumber() {
		int transnum = test.getTransactionNumber();
		String errormsg = String.format("failed to correctly retrieve transaction number - expected 1, got %d", transnum);
		assertEquals(transnum, 1, errormsg);
	}
	
	@Test
	void testGetTransactionAmount() {
		double transAmt = test.getTransactionAmount();
		String errormsg = String.format("failed to correctly retrieve transaction number - expected 1, got %f", transAmt);
		assertEquals(transAmt, 1, errormsg);
	}

}
