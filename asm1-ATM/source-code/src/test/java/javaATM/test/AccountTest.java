package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javaATM.*;
import java.util.ArrayList;

class AccountTest {
    static 
    // test balance getter
    @Test
    void testGetBalance() {
    	Account acc = new Account(1, 10.5);
    	String errormsg = String.format("Expected 10.5 as balance, got %f", acc.getBalance());
    	assertEquals(acc.getBalance(), 10.5, errormsg);
    }

    // test money adder
    @Test
    void testAddMoney() {
	Account acc = new Account(1, 10.5);
    	acc.addMoney(50);
    	String errormsg = String.format("Failed to add money correctly to acct: expected 60.5 as balance, got %f", acc.getBalance());
    	assertEquals(acc.getBalance(), 60.5, errormsg);
    }
    
    // test money deduct. money currently 60.5
    @Test
    void testDeductMoney() {
	Account acc = new Account(1, 10.5);
    	acc.deductMoney(5);
    	assertEquals(acc.getBalance(), 5.5, "Failed to deduct money correctly from acct.");
    }
    
    // test getting acct. num
    @Test
    void testGetAcctNum() {
    	Account acc = new Account(1, 10.5);
    	assertEquals(acc.getAccountNumber(), 1, "Didn't correctly retrieve account number");
    }

    // test card get - empty
    @Test
    void testGetCardEmpty() {
    	Account acc = new Account(1, 10.5);
	ArrayList<Card> expected = new ArrayList<Card>();
	assertTrue(acc.getCards().equals(expected),"failed to retrieve cards correctly - expected empty cards list");
    }
    
    // test card add
    @Test
    void testAddCard() {
    	Account acc = new Account(1, 10.5);
    	Card c = new Card(2, 3, acc);
    	
    	acc.addCard(c);
    	
    	Card result = acc.getCards().get(0);
    	assertEquals(result, c, "failed to add card correctly");
    }
    
    // test card get - nonempty
    @Test
    void testGetCard() {
    	Account acc = new Account(1, 10.5);
    	Card c = new Card(2, 3, acc);

	// add card to acct. list
    	acc.addCard(c);
    	ArrayList<Card> expected = new ArrayList<Card>();
    	// add card to expected list
    	expected.add(c);
	
    	// using the `equals` comparator in ArrayList, this should return true
    	assertTrue(acc.getCards().equals(expected),"failed to retrieve cards correctly - cards list doesn't match expected list");
    }
    
    // test invoice get - empty
    @Test
    void testGetInvoicesEmpty() {
    	Account acc = new Account(1, 10.5);
    	ArrayList<Transaction> expected = new ArrayList<Transaction>();
    	assertTrue(acc.getInvoices().equals(expected), "failed to return invoices correctly - expected empty invoices");
    }
    
    // test invoice get - nonempty
    @Test
    void testAddTransaction() {
    	// Account acc,String tty, int tnum, double tamt
    	Account acc = new Account(1, 10.5);
    	Transaction t = new Transaction(acc, "deposit", 2, 2);
    	ArrayList<Transaction> expected = new ArrayList<>();
    	expected.add(t);
    	acc.addTransaction(t);
    	assertTrue(acc.getInvoices().equals(expected),"failed to return invoices correctly - invoice list doesn't match expected list");
    }
    
    // test default constructor
    @Test
    void testDefaultConstructor() {
    	Account accdef = new Account();
    	ArrayList<Card> expectedCards = new ArrayList<Card>();
    	ArrayList<Transaction> expectedTrans = new ArrayList<Transaction>();
    	assertEquals(accdef.getBalance(), 0, "didn't retrieve balance correctly - default balance was non-zero");
    	assertTrue(accdef.getCards().equals(expectedCards), "didn't retrieve balance correctly - expected empty card set by default");
    	assertTrue(accdef.getInvoices().equals(expectedTrans), "didn't retrieve balance correctly - expected empty invoice set by default");
    }
    
    // test default constructor's ability to 
    // generate 8 digit account number
    @Test
    void testConstructorDigitGen() {
    	Account acc = new Account();
    	int accnum = acc.getAccountNumber();
    	String errormsg = String.format("Expected 8 digit number. Got %d, which is not 8-digits",accnum);
    	assertTrue( (accnum > 9999999 && accnum <= 99999999) , errormsg) ;
    }
}







