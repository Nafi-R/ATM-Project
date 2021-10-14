package javaATM;

// package javaATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/**
* a class which holds all of the operations
* the javaATM.ATM itself performs when a user wants to do a transaction
* at the javaATM.ATM.
* adminList: a HashMap mapping Integer: admin ID, to the Admin
* cardsList: a HashMap mapping Integer: the card's NUMBER, to the card
* currencies: an array of each currency denomination: $100, $50, $20 etc.
* currCounts: an array of the amount of each denomination: 100, 200 means $100 x 100, $50 x 200, etc.
* inputCard: the card that's been set as the card inserted
* stolenCards: a set of the card-numbers of stolen cards
*/

public class ATM {
	//maps *NUMBERS* to Admin objects
	//assume each adminID is unique
	private static HashMap<Integer, javaATM.Admin> adminList = new HashMap<Integer, Admin>(10);
	private static HashMap<Integer, Card> cardsList = new HashMap<Integer, Card>(500);
	private static HashMap<Integer, Account> accountsList = new HashMap<Integer, Account>(200);

	// total amount of money in the ATM
	private static float totalAmt = 0;

	public static float getFunds() {
		return totalAmt;
	}
	
	public static void deposit(int amt) {
		// add money to the total amount of money in system
		totalAmt += amt;
	}

	public static void setATMFunds(float funds) { totalAmt = funds; }

	public static int withdraw(Card c, float amt) {
		// card doesn't have enough money
		if (amt > c.getBalance()) {
			return 1;
		}
		// not enough money in ATM
		if (amt > totalAmt) {
			return -1;
		}
		
		// successful withdrawal
		c.deductMoney(amt);
		totalAmt -= amt;
		return 0;
	}


	public static double checkBalance(Card c) {
		return c.getBalance();
	}

	
	public static boolean addAdmin(Admin admin) {
		if (admin != null && !(adminList.containsKey(admin.getAdminID())) ) {
			adminList.put(admin.getAdminID(), admin);
			return true;
		}
		return false;
	}
	
	public static boolean removeAdmin(Admin admin) {
		if (admin == null) return false;
		if (adminList.containsKey(admin.getAdminID()) ) {
			adminList.remove(admin.getAdminID());
			return true;
		}
		return false;
	}
	
	
	public static boolean addCard(Card c) {
		if (c != null && !cardsList.containsKey(c.getCardNumber())) {
			cardsList.put(c.getCardNumber(), c);
			return true;
		}
		return false;
	}

	public static boolean removeCard(Card c) {
		if (c != null && cardsList.containsKey(c.getCardNumber()) ) {
			cardsList.remove(c.getCardNumber());
			return true;
		}
		return false;
	}

	public static boolean addAccount(Account acc) {
		if (acc != null && !accountsList.containsKey(acc.getAccountNumber())) {
			accountsList.put(acc.getAccountNumber(), acc);
			return true;
		}
		return false;
	}

	public static boolean removeAccount(Account acc) {
		if (acc != null && accountsList.containsKey(acc.getAccountNumber())) {
			accountsList.remove(acc.getAccountNumber());
			for(Card c : acc.getCards())
				removeCard(c);
			return true;
		}
		return false;
	}

	public static Card getCard(int cardNumber){
		return cardsList.getOrDefault(cardNumber, null);
	}

	public static Account getAccount(int accountNumber){
		return accountsList.getOrDefault(accountNumber, null);
	}

	public static Admin getAdmin(int adminID){
		return adminList.getOrDefault(adminID, null);
	}

	public static List<Admin> getAdmins()
	{
		List<Admin> output = new ArrayList<>();
		for(int adminNum : adminList.keySet())
			output.add(adminList.get(adminNum));
		return output;
	}


	public static List<Card> getCards()
	{
		List<Card> output = new ArrayList<>();
		for(int cardNum : cardsList.keySet())
			output.add(cardsList.get(cardNum));
		return output;
	}

	public static List<Account> getAccounts()
	{
		List<Account> output = new ArrayList<>();
		for(int accNum : accountsList.keySet())
			output.add(accountsList.get(accNum));
		return output;
	}
}
