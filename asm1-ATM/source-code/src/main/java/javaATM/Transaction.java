 package javaATM;

/**
* transType (String) : the type of transaction
* transactNumber (int) : the unique ID of the transaction
*/

import java.io.PrintWriter;
import java.io.File;

public class Transaction{
	Account account;
	String transType;
	int transactNumber;
	double transactAmt;
	
	public Transaction(Account acc,String tty, int tnum, double tamt) {
		this.account = acc;
		this.transType = tty;
		this.transactNumber = tnum;
		this.transactAmt = tamt;
	}

	public Account getAccount() { return this.account; }
	public String getTransactionType(){ return this.transType; }
	public int getTransactionNumber() { return this.transactNumber; }
	public double getTransactionAmount() { return this.transactAmt; }

	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Transaction)) return false;
		Transaction invoice = (Transaction) o;
		if (invoice.getAccount().getAccountNumber() != this.account.getAccountNumber()) return false;
		if (!invoice.getTransactionType().equals(this.transType)) return false;
		if (invoice.getTransactionNumber() != this.transactNumber) return false;
		if (invoice.getTransactionAmount() != this.transactAmt) return false;

		return true;
	}
}
