package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import javaATM.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class ATMTest {

	/*
	* setup
	* check there's no "residual" data inside ATM
	*/
	@AfterEach
	public void resetFunds() {
		ATM.setATMFunds(0);
		// no funds
		assertTrue(ATM.getFunds() == 0);
		// no cards
		for (Card c : ATM.getCards()) {
			ATM.removeCard(c);
		}
		assertTrue(ATM.getCards().size() == 0);
		// no admins
		for (Admin a : ATM.getAdmins()) {
			ATM.removeAdmin(a);
		}
		assertTrue(ATM.getAdmins().size() == 0);
		// no accounts
		for (Account a : ATM.getAccounts()) {
			ATM.removeAccount(a);
		}
		assertTrue(ATM.getAccounts().size() == 0);
	}

	/*
	* ATM fund modification related
	*/
	@Test
	public void testDeposit() {
		ATM.deposit(100);
		assertEquals(ATM.getFunds(), 100, "Expected $100 in ATM. Did not get $100");
	}

	@Test
	public void testWithDrawATMInsufficient() {
		Account a = new Account(1, 1);
		Card c = new Card(1,a);
		c.addMoney(5);
		int rval = ATM.withdraw(c, 2);
		// return stat -1
		assertTrue(rval == -1);
	}
	
	@Test
	public void testWithDrawAccountInsufficient() {
		ATM.setATMFunds(10);
		Account a = new Account(1, 1);
		Card c = new Card(1,a);
		// w.draw $10 but acct only has 1
		int rval = ATM.withdraw(c, 10);
		// return status 1
		assertTrue(rval == 1);
	}
	
	@Test
	public void testWithDrawSuccess() {
		ATM.setATMFunds(1000);
		Account a = new Account(1, 100);
		Card c = new Card(1,a);
		int rval = ATM.withdraw(c, 10);
		// ret stat 0
		assertTrue(rval == 0);
		// check acct balance
		assertTrue(a.getBalance() == 90);
	}

	@Test
	public void testSetFunds() {
		ATM.setATMFunds(100);
		assertEquals(ATM.getFunds(), 100, "Expected $100 in ATM. Did not get $100");
	}
	
	// test if the initial ATM class funds is $0 and can get successfully
	@Test
	public void testGetFundsZero() {
		ATM.setATMFunds(0);
		float funds = ATM.getFunds();
		String errormsg = String.format("Expected funds to be $0. Got %f", funds);
		assertEquals(funds, 0, errormsg);
	}
	
	// test if the ATM class funds can be modified successfully

	@Test
	public void testGetATMBalance() {
		ATM.setATMFunds(0);
		float funds = ATM.getFunds();
		String errormsg = String.format("Expected funds to be $0. Got %f", funds);
		assertEquals(funds, 0, errormsg);
	}

	@Test
	public void testCheckBalance() {
		// int pin, Account acct
		Account a = new Account();
		Card c = new Card(1, a);
		double balance = ATM.checkBalance(c);
		String errormsg = String.format("Expected card's balance to be $0. Got %f", balance);
		assertEquals(balance,0,errormsg);
	}
	
	
	/**
	* Testing admin related
	*/
	@Test
	public void testRemoveAdminNull() {
		assertFalse(ATM.removeAdmin(null),"Expected false on removing null admin. Got true");
		
	}
	
	@Test
	public void testAddAdminNull() {
		assertFalse(ATM.addAdmin(null),"Expected false on adding null admin. Got true");
		
	}
	
	@Test
	public void testAddAndRemoveAdminSuccess() {
		// String name, int adminID, int passcode)
		Admin a = new Admin(null,1,2);
		// adding admin
		assertTrue(ATM.addAdmin(a));
		// removing admin
		assertTrue(ATM.removeAdmin(a));
	}
	
	@Test
	public void testRemoveNonRecordAdmin() {
		Admin a = new Admin(null,1,2);
		assertFalse(ATM.removeAdmin(a));
	}
	
	@Test
	public void testGetNonExistAdmin() {
		Admin a = new Admin(null,1,2);
		assertTrue(ATM.getAdmin(a.getAdminID()) == null);
	}
	
	@Test
	public void testGetExistAdmin() {
		Admin a = new Admin(null,1,2);
		// add the admin successfully
		assertTrue(ATM.addAdmin(a));
		// remove the admin successfully
		assertTrue(ATM.getAdmin(a.getAdminID()) != null);
	}
	
	@Test
	public void getAllAdmins() {
		Admin a = new Admin(null,1,2);
		ATM.addAdmin(a);
		List<Admin> admins = ATM.getAdmins();
		assertTrue(admins.size() == 1);
		assertTrue(admins.get(0).getAdminID() == 1);
	}
	
	@Test
	public void testAddingExistingAdmin() {
		Admin a = new Admin(null,1,2);
		assertTrue(ATM.addAdmin(a));
		assertFalse(ATM.addAdmin(a));
	}
	
	/*
	* testing account related
	*/
	@Test
	public void testAddAcctNull() {
		assertFalse(ATM.addAccount(null),"Expected false on adding null account. Got true");
	}
	
	@Test
	public void testRemoveAcctNull() {
		assertFalse(ATM.removeAccount(null), "Expected false on removing null account. Got true");
		
	}
	
	@Test
	public void testAddAndRemoveAcctSuccess() {
		// int accountNumber, double balance
		Account a = new Account(1,1);
		// correct account number?
		assertTrue(a.getAccountNumber() == 1);
		// adding account
		assertTrue(ATM.addAccount(a)); // FIX ME!?
		// removing account
		assertTrue(ATM.removeAccount(a)); // FIX ME!!?
	}
	
	@Test
	public void testRemoveAcctWithCard() {
		Account a = new Account(1,1);
		Card c = new Card(1,a);
		a.addCard(c);
		// adding card
		assertTrue(ATM.addCard(c));
		// adding account
		assertTrue(ATM.addAccount(a));
		// removing account - card should be removed too
		// should iterate thru cards list and remove card associated
		// with account 'a'
		assertTrue(ATM.removeAccount(a));
		// check that the card really is gone
		assertFalse(ATM.removeCard(c));
	}
	
	@Test
	public void testGetAccountByNum() {
		Account a = new Account(1,1);
		assertTrue(ATM.addAccount(a));
		Account a2 = ATM.getAccount(1);
		assertTrue(a2.getAccountNumber() == 1);
		assertTrue(a2.getBalance() == 1);
		// remove the account
		ATM.removeAccount(a);
	}
	
	@Test
	public void getAllAccounts() {
		Account a = new Account(1,1);
		ATM.addAccount(a);
		List<Account> accounts = ATM.getAccounts();
		assertTrue(accounts.size() == 1);
		assertTrue(accounts.get(0).getAccountNumber() == 1);
	}
	
	@Test
	public void testAddingExistingAcct() {
		Account a = new Account(1,1);
		assertTrue(ATM.addAccount(a));
		assertFalse(ATM.addAccount(a));
	}
	
	@Test
	public void testRemoveNonExistingAcct() {
		Account a = new Account(1,1);
		assertFalse(ATM.removeAccount(a));
	}
	
	/*
	* Testing card related
	*/
	@Test
	public void testRemoveCardNull() {
		assertFalse(ATM.removeCard(null), "Expected false on removing null card. Got true");
		
	}
	
	@Test
	public void testAddCardNull() {
		assertFalse(ATM.addCard(null), "Expected false on adding null card. Got true");
	}
	
	@Test
	public void testAddAndRemoveCardSuccess() {
		Card c = new Card(1,null);
		// adding
		assertTrue(ATM.addCard(c));
		// removing
		assertTrue(ATM.removeCard(c));
	}
	
	@Test
	public void testRemoveNonRecordedCard() {
		// removing should return false if the removeCard called 
		// but card's number not in the record of cards
		Card c = new Card(2, null);
		assertFalse(ATM.removeCard(c));
	}
	
	@Test
	public void testGetCardByNum() {
		Card c = new Card(2, 2, null);
		assertTrue(ATM.addCard(c));
		Card c2 = ATM.getCard(2);
		assertTrue(c2 != null);
		assertTrue(c2.getCardNumber() == 2);
		assertTrue(c2.getAccount() == null);
	}
	
	@Test
	public void testAddingExistingCard() {
		Card c = new Card(2, 2, null);
		assertTrue(ATM.addCard(c));
		assertFalse(ATM.addCard(c));
	}
	
	@Test
	public void getAllCards() {
		// creating cards
		Card c1 = new Card(1, 1, null);
		// adding cards
		ATM.addCard(c1);
		// getting cards
		List<Card> cards = ATM.getCards();
		assertTrue(cards.size() == 1);
		assertTrue(cards.get(0).getCardNumber() == c1.getCardNumber());
	}
	
	/*
	* test constructor
	*/
	@Test
	public void testConstructor() {
		ATM atm = new ATM();
		assertNotNull(atm);
		assertTrue(ATM.getCards().size() == 0);
		assertTrue(ATM.getAdmins().size() == 0);
		assertTrue(ATM.getAccounts().size() == 0);
	}

}
