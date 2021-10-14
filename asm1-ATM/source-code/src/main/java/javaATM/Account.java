package javaATM;
// package javaATM;

import java.util.ArrayList;

/**
* Abstraction of the account associated
* with a bank
* List<javaATM.Card> cards ==> a list of credit cards associated with the account
* List<Transaction> transactions ==> a list of transactions associated with the account
*/
public class Account {
	private double balance = 0;
	private int accountNumber;
	private ArrayList<Card> cards;
	private ArrayList<Transaction> transactions;
	
	public Account() {
		int accNum = (int) Math.round(Math.random()*100000000); // 8 digit integer that identifies the unique account
		while(ATM.getAccount(accNum) != null || accNum < 10000000) {
			accNum = (int) Math.round(Math.random()*100000000);
		}
		this.accountNumber = accNum;
		transactions = new ArrayList<Transaction>();
		cards = new ArrayList<Card>();
	}

	public Account(int accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.transactions =  new ArrayList<Transaction>();
		this.cards = new ArrayList<Card>();
	}

	public void addMoney(int amt) { this.balance += amt; }

	public double getBalance() { return this.balance; }

	public void deductMoney(double amt) {
		this.balance -= amt;
	}

	public void addCard(Card newCard) {
		cards.add(newCard);
	}

	public ArrayList<Card> getCards(){ return cards;}

	public void addTransaction(Transaction invoice){ this.transactions.add(invoice); }

	public int getAccountNumber(){ return this.accountNumber;}
	
	public ArrayList<Transaction> getInvoices() {
		return this.transactions;
	}

}

