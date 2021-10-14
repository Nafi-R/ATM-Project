package javaATM;

import java.time.LocalDate;

public class Card {

	Account associatedAccount;
	int cardNumber;
	private int pinNumber;
	String cardStatus;
	LocalDate startDate;
	LocalDate expiryDate;
	

	/**
	* javaATM.Card constructor
	* javaATM.Card(int cardNumber, int initialBalance, int pinNumber, javaATM.Account associatedAccount)
	*/
	public Card(int pin, Account acct) {
		int cardNum = (int) Math.round(Math.random()*100000); // 8 digit integer that identifies the unique card
		while(ATM.getCard(cardNum) != null && cardNum <= 99999 && cardNum >= 10000) {
			cardNum = (int) Math.round(Math.random()*100000);
		}
		this.cardNumber = cardNum;
		this.associatedAccount = acct;
		this.pinNumber = pin;
		this.startDate = LocalDate.now(); // set as the current day of when the new card is created
		this.expiryDate = LocalDate.of(startDate.getYear() + 3, startDate.getMonthValue(), startDate.getDayOfMonth()); // expiry date will be 3 years from when the card is opened
		this.cardStatus = "Valid";
	}

	public Card(int cardNumber, int pinNumber, Account acct) {
		this.associatedAccount = acct;
		this.cardNumber = cardNumber;
		this.pinNumber = pinNumber;
		this.startDate = LocalDate.now(); // set as the current day of when the new card is created
		this.expiryDate = LocalDate.of(startDate.getYear() + 3, startDate.getMonthValue(), startDate.getDayOfMonth()); // expiry date will be 3 years from when the card is opened
		this.cardStatus = "Valid";
	}

	public Card(int cardNumber, int pinNumber, Account acct, LocalDate startDate, LocalDate expiryDate, String status) {
		this.associatedAccount = acct;
		this.cardNumber = cardNumber;
		this.pinNumber = pinNumber;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.cardStatus = status;
	}



	public double getBalance() {
		return this.associatedAccount.getBalance();
	}

	public void addMoney(int amt) { this.associatedAccount.addMoney(amt); }

	public void deductMoney(double amt) { this.associatedAccount.deductMoney(amt); }

	public int getCardNumber() {
		return cardNumber;
	}

	public int getPin() {
		return pinNumber;
	}

	public void setPin(int newPin) {
		this.pinNumber = newPin;
	}
	
	public void setCardNumber(int number) {
		this.cardNumber = number;
	}
	
	public void changeAccount(Account newAcct) {
		this.associatedAccount = newAcct;
	}

	public void setStatus(String status) { this.cardStatus = status; }

	public Account getAccount() { return this.associatedAccount; }

	public LocalDate getStartDate() { return this.startDate; }

	public LocalDate getExpiryDate() {
		return this.expiryDate;
	}

	public String getStatus() {
		if (this.cardStatus == null) return null;
		if (this.cardStatus.equals("Valid")) {
			if (LocalDate.now().isBefore(startDate)){this.cardStatus = "Not activated yet";	}
			else if(LocalDate.now().isAfter(expiryDate)){this.cardStatus = "Expired"; }
		}
		else if(this.cardStatus.equals("Not activated yet") || this.cardStatus.equals("Expired")){
			if (LocalDate.now().isAfter(startDate) && LocalDate.now().isBefore(expiryDate)) {
				this.cardStatus = "Valid";
			}
		}
		return this.cardStatus;
	}
}



