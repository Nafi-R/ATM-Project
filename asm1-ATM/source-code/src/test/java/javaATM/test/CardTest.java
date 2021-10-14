package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import javaATM.*;

public class CardTest {

	@Test
	public void testGetBalance() {
		// Account(int accountNumber, double balance)
		Account a = new Account(1, 1);
		// Card(int cardNumber, int pinNumber, Account acct)
		Card c = new Card(1, 1, a);
		String errormsg = String.format("Expected $1 as balance, got %f",c.getBalance());
		assertTrue(c.getBalance() == 1, errormsg);
	}
	
	@Test
	public void testAddMoney() {
		// Account(int accountNumber, double balance)
		Account a = new Account(1, 1);
		// Card(int cardNumber, int pinNumber, Account acct)
		Card c = new Card(1, 1, a);
		// account's money should now be $3
		c.addMoney(2);
		String errormsg = String.format("Expected $3 as balance after adding, got %f",c.getBalance());
		assertTrue(c.getBalance() == 3, errormsg);
	}
	
	@Test
	public void testGetStatus() {
		Card c = new Card(1, 1, null);
		String status = c.getStatus();
		String expected = "Valid";
		String errormsg = String.format("expected valid status, got %s",status);
		assertTrue(status.equals(expected), errormsg);
	}
	
	@Test
	public void testGetStatusNull() {
		Card c = new Card(1, 1, null, null, null, null);
		assertTrue(c.getStatus() == null, "expected null status. got non-null status");
	}
	
	@Test
	public void testSetStatus() {
		Card c = new Card(1, 1, null);
		c.setStatus("invalid");
		String status = c.getStatus();
		String expected = "invalid";
		String errormsg = String.format("expected invalid status, got %s",status);
		assertTrue(status.equals(expected), errormsg);
	}

	@Test
	public void testGetAccountNull() {
		Card c = new Card(1, 1, null);
		assertEquals(c.getAccount(), null, "Expected null account. Did not get null");
	}
	
	@Test
	public void testGetAccountNotNull() {
		Account a = new Account(1, 1);
		Card c = new Card(1, 1, a);
		// in theory, the account objects should point to the same address
		assertTrue(c.getAccount() == a, "expected non-null account, with account number 1, and balance 1");
	}

	@Test
	public void testGetStartDateNull() {
		// int cardNumber, int pinNumber, Account acct, LocalDate startDate, LocalDate expiryDate, String status
		Card c = new Card(1, 1, null, null, null, null);
		assertTrue(c.getStartDate() == null, "expected null start date. got non-null");
	}

	@Test	
	public void testGetStartDate() {
		LocalDate expected = LocalDate.now();
		Card c = new Card(1, 1, null);
		LocalDate csdate = c.getStartDate();
		int comparison = expected.compareTo(csdate);
		assertEquals(comparison, 0, "Start date should have been today, however card's start date returned value not equal to current date");
	}
	
	@Test
	public void testGetExpiryDateNull() {
		Card c = new Card(1, 1, null, null, null, null);
		assertTrue(c.getExpiryDate() == null, "expected null expiry date. got non-null");
	}	
	
	@Test
	public void testGetExpiryDateNotNull() {
		Card c = new Card(1, 1, null);
		LocalDate now = LocalDate.now();
		LocalDate expected = LocalDate.of(now.getYear() + 3, now.getMonthValue(), now.getDayOfMonth());
		LocalDate actual = c.getExpiryDate();
		assertEquals(actual.compareTo(expected), 0, "Expected expiry date to be 3 years later than start date");
	}
	
	@Test
	public void testGetPin() {
		Card c = new Card(1, 1, null, null, null, null);
		String errormsg = String.format("expected pin number as 1, got %d", c.getPin());
		assertTrue(c.getPin() == 1, errormsg);
	}
	
	@Test
	public void testSetPin() {
		Card c = new Card(1, 1, null, null, null, null);
		c.setPin(0);
		String errormsg = String.format("expected pin number as 0, got %d", c.getPin());
		assertTrue(c.getPin() == 0, errormsg);
	}
	
	@Test
	public void testChangeAccountNull() {
		Account a = new Account(1, 1);
		Card c = new Card(1,1,a);
		assertTrue(c.getAccount() == a, "Expected an account with account number 1, balance $1.");
		c.changeAccount(null);
		assertTrue(c.getAccount() == null, "expected account to change to null. was not null");
	}
	
	@Test
	public void testChangeAccountNotNull() {
		Account a = new Account(1, 1);
		Card c = new Card(1,1,null);
		assertTrue(c.getAccount() == null, "expected account to be null");
		c.changeAccount(a);
		assertTrue(c.getAccount() == a, "Expected account to change to account with number 1, balance $1.");
	}
	
	@Test
	public void testUpStatBeforeStart() {
		LocalDate start = LocalDate.of(2021,10,1);
		Card c = new Card(1,1,null,start,null,"Valid");
		String errormsg = String.format("Expected  'Not activated yet' status, got %s", c.getStatus());
		// check status is not yet activated
		assertTrue(c.getStatus().equals("Not activated yet"), errormsg);
	}
	
	@Test
	public void testUpStatAfterExpiry() {
		LocalDate start = LocalDate.of(2021,3,1);
		LocalDate expiry = LocalDate.of(2021,9,1);
		Card c = new Card(1,1,null,start,expiry,"Valid");
		String errormsg = String.format("Expected  'Expired' status, got %s", c.getStatus());
		// check status is not yet activated
		assertTrue(c.getStatus().equals("Expired"), errormsg);
	}
	
	@Test
	public void testUpStatWithinDates() {
		LocalDate start = LocalDate.of(2021,3,1);
		LocalDate expiry = LocalDate.of(2021,10,1);
		Card c = new Card(1,1,null,start,expiry,"Expired");
		String errormsg = String.format("Expected 'Valid' status, got %s", c.getStatus());
		assertTrue(c.getStatus().equals("Valid"), errormsg);
	}
	
	// if none of the statuses checked for appear
	// then the status shouldn't update after the updateStatus() method is called
	@Test
	public void testNoUpdate() {
		LocalDate start = LocalDate.of(2021,3,1);
		LocalDate expiry = LocalDate.of(2021,10,1);
		Card c = new Card(1,1,null,start,expiry,"hi");
		String errormsg = String.format("Expected status 'hi', got %s", c.getStatus());
		assertTrue(c.getStatus().equals("hi"), errormsg);
		errormsg = String.format("Still expected status 'hi', got %s", c.getStatus());
		assertTrue(c.getStatus().equals("hi"), errormsg);
	}
	
	@Test
	public void testDeductMoneyPos() {
		Account a = new Account(1, 100);
		Card c = new Card(2, a);
		c.deductMoney(5);
		String errormsg = String.format("Expected $95 balance, got %f",c.getBalance());
		assertEquals(c.getBalance(), 95, errormsg);
	}
	
	@Test
	public void testDeductMoneyNegative() {
		Account a = new Account(1, 100);
		Card c = new Card(2, a);
		c.deductMoney(-5);
		String errormsg = String.format("Expected $105 balance after deducting $-5, got %f",c.getBalance());
		assertEquals(c.getBalance(), 105, errormsg);
	}
	
	@Test
	public void testGetCardNumber() {
		Card c = new Card(1,1,null);
		String errormsg = String.format("Expected a card number of 1. Got %d",c.getCardNumber());
		assertEquals(c.getCardNumber(), 1, errormsg);
	}
	
	@Test
	public void testSetCardNumber() {
		Card c = new Card(1,1,null);
		// change the card's number
		c.setCardNumber(2);
		String errormsg = String.format("Expected a card number of 2. Got %d",c.getCardNumber());
		assertEquals(c.getCardNumber(), 2, errormsg);	
	}
	
	// test the Card(int pin, Account acct) constructor
	@Test
	public void testConstructorNullAcct() {
		Card c = new Card(1, null);
		int cnum = c.getCardNumber(); // card number
		String errormsg = String.format("Expected 5 digit card number. Got %d, which is not 5 digits",c.getCardNumber());
		assertTrue( (cnum > 9999 && cnum <= 99999) , errormsg);
		assertTrue(c.getAccount() == null, "expected null account. got non-null");
	}
	
	@Test
	public void testConstructorNotNullAcct() {
		Account a = new Account(1, 100);
		Card c = new Card(1, a);
		int cnum = c.getCardNumber(); // card number
		String errormsg = String.format("Expected 5 digit card number. Got %d, which is not 5 digits",c.getCardNumber());
		// card number
		assertTrue( (cnum > 9999 && cnum <= 99999) , errormsg);
		// account
		assertTrue(c.getAccount() == a, "Expected account with account number 1 and balance $100, got a different account");
	}


	@Test
    public void initTest()
    {
        Account acc = new Account();
        Card c1 = new Card(6753,acc);
        assertEquals(6753, c1.getPin());
    }

    @Test
    public void statusTest()
    {
        Account acc = new Account();
        Card c1 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2020,12,2), "Stolen");
        assertEquals("Stolen", c1.getStatus());

        Card c2 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2020,12,2), "Valid");
        assertEquals("Expired", c2.getStatus());

        Card c3 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2021,12,2), "Expired");
        assertEquals("Valid", c3.getStatus());

        Card c4 = new Card(123456,1231,acc, LocalDate.of(2021,12,2),LocalDate.of(2022,12,2), "Valid");
        assertEquals("Not activated yet", c4.getStatus());
    }

    @Test
    public void cardBalanceTest()
    {
        Account acc = new Account();
        Card c1 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2020,12,2), "Stolen");
        c1.addMoney(10);
        assertEquals(10, c1.getBalance());
        c1.addMoney(10);
        assertEquals(20, c1.getBalance());
        c1.deductMoney(5);
        assertEquals(15, c1.getBalance());
    }

    @Test
    public void changePinTest()
    {
        Account acc = new Account();
        Card c1 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2020,12,2), "Stolen");
        assertEquals(1231, c1.getPin());
        c1.setPin(3212);
        assertEquals(3212, c1.getPin());
    }

    @Test
    public void changeCardNumberTest()
    {
        Account acc = new Account();
        Card c1 = new Card(123456,1231,acc, LocalDate.of(2020,12,2),LocalDate.of(2020,12,2), "Stolen");
        assertEquals(123456, c1.getCardNumber());
        c1.setCardNumber(9999);
        assertEquals(9999, c1.getCardNumber());
    }


}

