package javaATM;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;


public class IO {

    public static final String FOLDER_PATH = "./data";
    public static final String ACCOUNTS_PATH = FOLDER_PATH + "/accounts.csv";
    public static final String CARDS_PATH = FOLDER_PATH + "/cards.csv";
    public static final String ATM_PATH = FOLDER_PATH + "/ATMstate.csv";
    public static final String TRANSACTIONS_PATH = FOLDER_PATH + "/transactions.csv";

    public static void deleteFiles(){
        File folder = new File(FOLDER_PATH);
        File accounts = new File(ACCOUNTS_PATH);
        File cards = new File(CARDS_PATH);
        File ATMstate = new File(ATM_PATH);
        File transactions = new File(TRANSACTIONS_PATH);


        if (accounts.delete()) {
            System.out.println("Successfully deleted accounts.csv");
        }
        else{
            System.out.println("Unable to delete accounts.csv");
        }

        if (cards.delete()) {
            System.out.println("Successfully deleted cards.csv");
        }
        else{
            System.out.println("Unable to delete cards.csv");
        }

        if (ATMstate.delete()) {
            System.out.println("Successfully deleted ATMstate.csv");
        }
        else{
            System.out.println("Unable to delete ATMstate.csv");
        }

        if (transactions.delete()) {
            System.out.println("Successfully deleted transactions.csv");
        }
        else{
            System.out.println("Unable to delete transactions.csv");
        }

        if (folder.delete()) {
            System.out.println("Successfully deleted data folder");
        }
        else{
            System.out.println("Unable to delete data folder");
        }
    }

    public static void createFiles()
    {
        InputStream accountReader = IO.class.getClassLoader().getResourceAsStream("accounts.csv");
        InputStream cardReader = IO.class.getClassLoader().getResourceAsStream("cards.csv");
        InputStream atmReader = IO.class.getClassLoader().getResourceAsStream("ATMstate.csv");
        InputStream transactReader = IO.class.getClassLoader().getResourceAsStream("transactions.csv");

        File folder = new File(FOLDER_PATH);

        File accounts = new File(ACCOUNTS_PATH);
        File cards = new File(CARDS_PATH);
        File ATMstate = new File(ATM_PATH);
        File transactions = new File(TRANSACTIONS_PATH);

        if(folder.mkdirs()){
            System.out.println("Folder 'data' created in: " + folder.getAbsolutePath());
        }


        if (!accounts.exists()){
            try{
                accounts.createNewFile();
                Scanner input = new Scanner(accountReader);
                FileWriter output = new FileWriter(accounts);
                while(input.hasNextLine()){
                    output.write(input.nextLine() + System.lineSeparator());
                }
                output.close();
                input.close();
            }
            catch(IOException e)
            {
                System.out.println("Unable to initialize 'accounts.csv' in: " + accounts.getAbsolutePath());
            }
        }

        if (!cards.exists()){
            try{
                cards.createNewFile();
                Scanner input = new Scanner(cardReader);
                FileWriter output = new FileWriter(cards);
                while(input.hasNextLine()){
                    output.write(input.nextLine() + System.lineSeparator());
                }
                output.close();
                input.close();
            }
            catch(IOException e)
            {
                System.out.println("Unable to initialize 'cards.csv' in: " + cards.getAbsolutePath());
            }
        }

        if (!ATMstate.exists()){
            try{
                ATMstate.createNewFile();
                Scanner input = new Scanner(atmReader);
                FileWriter output = new FileWriter(ATMstate);
                while(input.hasNextLine()){
                    output.write(input.nextLine() + System.lineSeparator());
                }
                output.close();
                input.close();
            }
            catch(IOException e)
            {
                System.out.println("Unable to initialize 'ATMstate.csv' in: " + ATMstate.getAbsolutePath());
            }
        }
        
        if (!transactions.exists()){
            try{
                transactions.createNewFile();
                Scanner input = new Scanner(transactReader);
                FileWriter output = new FileWriter(transactions);
                while(input.hasNextLine()){
                    output.write(input.nextLine() + System.lineSeparator());
                }
                output.close();
                input.close();
            }
            catch(IOException e)
            {
                System.out.println("Unable to initialize 'transactions.csv' in: " + transactions.getAbsolutePath());
            }
        }


    }

    static public void load()
    {
        createFiles();

        File accountsFile = new File(ACCOUNTS_PATH);
        File cardsFile = new File(CARDS_PATH);
        File ATMFile = new File(ATM_PATH);
        File transactionFile = new File(TRANSACTIONS_PATH);

        /*Accounts File format
            accountNumber,accountBalance
        e.g. 125324231,21421.50
        */

        HashMap<Integer,Account> accountList = new HashMap<>();
        int lineNumber = 1;
        try{
            Scanner input = new Scanner(accountsFile);
            while (input.hasNextLine()) {
                try {
                    String line = input.nextLine();
                    String[] values = line.split(",");
                    int accountNumber = Integer.parseInt(values[0]);
                    double accountBalance = Double.parseDouble(values[1]);
                    Account acc = new Account(accountNumber, accountBalance);
                    accountList.put(accountNumber, acc);
                    ATM.addAccount(acc);
                    lineNumber++;
                }
                catch(NumberFormatException | ArrayIndexOutOfBoundsException ex)
                {
                    System.out.println("Error occurred on line number " + lineNumber + " in 'accounts.csv'");
                    lineNumber++;
                }
            }
            input.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Could not read 'account.csv' in " + ACCOUNTS_PATH);
        }
    

        /*Cards File format
            cardNumber,cardPin,accountNumber,startDate,expiryDate,cardStatus
        e.g. 2432123,12456,125324231,1-11-2021,1-12-2023,Valid
        */

        lineNumber = 1;
        try{
            Scanner input = new Scanner(cardsFile);
            while (input.hasNextLine()) {
                try{
                    String line = input.nextLine();
                    String[] values = line.split(",");
                    int cardNumber = Integer.parseInt(values[0]);
                    int cardPIN = Integer.parseInt(values[1]);
                    int accountNumber = Integer.parseInt(values[2]);
                    if (!accountList.containsKey(accountNumber)){
                        System.out.println("Error occurred on line number: " + lineNumber);
                        System.out.println("No account found with account number '" + accountNumber + "' found!");
                        lineNumber++;
                        continue;
                    }
                    Account account = accountList.get(accountNumber);
                    String[] startDate_str = values[3].split("-");
                    int sDay = Integer.parseInt(startDate_str[0]);
                    int sMonth = Integer.parseInt(startDate_str[1]);
                    int sYear = Integer.parseInt(startDate_str[2]);
                    LocalDate startDate = LocalDate.of(sYear,sMonth,sDay);
                    String[] expiryDate_str = values[4].split("-");
                    int eDay = Integer.parseInt(expiryDate_str[0]);
                    int eMonth = Integer.parseInt(expiryDate_str[1]);
                    int eYear = Integer.parseInt(expiryDate_str[2]);
                    LocalDate expiryDate = LocalDate.of(eYear,eMonth,eDay);
                    String cardStatus = values[5];
                    Card c = new Card(cardNumber,cardPIN,account,startDate,expiryDate,cardStatus);
                    account.addCard(c);
                    ATM.addCard(c);
                    lineNumber++;
                }
                catch (NumberFormatException ex){
                    System.out.println("Invalid number on line number " + lineNumber + " in 'cards.csv'");
                    lineNumber++;
                }
                catch(ArrayIndexOutOfBoundsException ax){
                    System.out.println("Invalid format on line number " + lineNumber + " in 'cards.csv'");
                    lineNumber++;
                }
                catch(DateTimeException dx){
                    System.out.println("Invalid date entry on line number " + lineNumber + " in 'cards.csv'");
                    lineNumber++;
                }
            }
            input.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Could not read 'cards.csv' in " + CARDS_PATH);
        }
        /*ATM File format
            Line 1: ATMBalance
            Line 2: adminName1,adminID1,adminPassword1
            ...
            Line N: adminNameN,adminIDN, adminPasswordN
        e.g.
        */

        lineNumber = 1;
        try{
            Scanner input = new Scanner(ATMFile);
            while(input.hasNextLine())
            {
                try {
                    String line = input.nextLine();
                    if (lineNumber == 1) {
                        float balance = Float.parseFloat(line);
                        ATM.setATMFunds(balance);
                    }
                    else{
                        String[] values = line.split(",");
                        String adminName = values[0];
                        int adminID = Integer.parseInt(values[1]);
                        int passCode = Integer.parseInt(values[2]);
                        Admin admin = new Admin(adminName,adminID,passCode);
                        ATM.addAdmin(admin);
                    }
                    lineNumber++;
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("Error occurred on line number " + lineNumber + " in 'ATMstate.csv'");
                    lineNumber++;
                }
                catch(ArrayIndexOutOfBoundsException ex)
                {
                    System.out.println("Invalid entry on line number " + lineNumber + " in 'ATMstate.csv'");
                    lineNumber++;
                }
            }
            input.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Could not read 'ATMstate.csv' in " + ATM_PATH);
        }

        /* Transaction File format

        accountNumber,transactionNumber,transactionType,transactionAmount

        */

        lineNumber = 1;
        try{
            Scanner input = new Scanner(transactionFile);
            while(input.hasNextLine())
            {
                try {
                    String line = input.nextLine();
                    String[] values = line.split(",");
                    int accountNumber = Integer.parseInt(values[0]);
                    if(!accountList.containsKey(accountNumber)) {
                        System.out.println("Error occurred on line number " + lineNumber + " in transactions.csv");
                        System.out.println("No account found with account number '" + accountNumber + "' found!");
                        lineNumber++;
                        continue;
                    }
                    int transactionNumber = Integer.parseInt(values[1]);
                    String transactionType = values[2];
                    double transactionAmt = Double.parseDouble(values[3]);
                    Account acc = accountList.get(accountNumber);
                    Transaction t = new Transaction(acc,transactionType,transactionNumber,transactionAmt);
                    acc.addTransaction(t);
                    lineNumber++;
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("Error occurred on line number " + lineNumber + " in 'transactions.csv'");
                    lineNumber++;
                }
                catch(ArrayIndexOutOfBoundsException ex)
                {
                    System.out.println("Invalid entry on line number " + lineNumber + " in 'transactions.csv'");
                    lineNumber++;
                }
            }
            input.close();
        }
        catch(FileNotFoundException fnfe){
            System.out.println("Could not read 'transactions.csv' in " + TRANSACTIONS_PATH);
        }
    }

    public static void save()
    {
        createFiles();
        /*Accounts File format
            accountNumber,accountBalance
        e.g. 125324231,21421.50
        */
        try{
            File accountFile = new File(ACCOUNTS_PATH);

            FileWriter fw = new FileWriter(accountFile,false);
            for(Account acc : ATM.getAccounts())
            {
                String s = acc.getAccountNumber() + "," + acc.getBalance() + System.lineSeparator();
                fw.append(s);
            }
            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to write to file at: " + ACCOUNTS_PATH);
        }

        /*Cards File format
            cardNumber,cardPin,accountNumber,startDate,expiryDate,cardStatus
        e.g. 2432123,12456,125324231,1-11-2021,1-12-2023,Valid
        */
        try{
            File cardFile = new File(CARDS_PATH);

            FileWriter fw = new FileWriter(cardFile,false);
            for(Card c : ATM.getCards())
            {
                String startDate_str = c.getStartDate().getDayOfMonth() + "-" + c.getStartDate().getMonthValue() + "-" + c.getStartDate().getYear();
                String expiryDate_str = c.getExpiryDate().getDayOfMonth() + "-" + c.getExpiryDate().getMonthValue() + "-" + c.getExpiryDate().getYear();

                String s = c.getCardNumber() + "," + c.getPin() + "," + c.getAccount().getAccountNumber() + "," +
                        startDate_str + "," + expiryDate_str + "," + c.getStatus() +  System.lineSeparator();
                fw.append(s);
            }
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Unable to write to file at: " + CARDS_PATH);
        }

        /*ATM File format
            Line 1: ATMBalance
            Line 2: adminName1,adminID1,adminPassword1
            ...
            Line N: adminNameN,adminIDN, adminPasswordN
        e.g.*/
        try{
            File ATMFile = new File(ATM_PATH);
            FileWriter fw = new FileWriter(ATMFile,false);
            fw.append(String.valueOf(ATM.getFunds())).append(System.lineSeparator());
            for(Admin admin : ATM.getAdmins())
            {
                String s = admin.getName() + "," + admin.getAdminID() + "," + admin.getPasscode() + System.lineSeparator();
                fw.append(s);
            }
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Unable to write to file at: " + ATM_PATH);
        }

        /* Transaction File format

        accountNumber,transactionNumber,transactionType,transactionAmount

        */

        try{
            File transFile = new File(TRANSACTIONS_PATH);
            FileWriter fw = new FileWriter(transFile,false);
            for(Account acc : ATM.getAccounts())
            {
                for(Transaction t : acc.getInvoices()) {
                    String s = acc.getAccountNumber() + "," + t.getTransactionNumber() + "," + t.getTransactionType() + ","
                            + t.getTransactionAmount() + System.lineSeparator();
                    fw.append(s);
                }
            }
            fw.close();
        }
        catch(IOException e)
        {
            System.out.println("Unable to write to file at: " + TRANSACTIONS_PATH);
        }
    }

}