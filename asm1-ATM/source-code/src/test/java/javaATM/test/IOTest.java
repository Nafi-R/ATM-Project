package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javaATM.*;
import java.time.LocalDate;

public class IOTest {
    @AfterAll
    static void deleteAll(){
        IO.deleteFiles();
    }

    @BeforeEach
    void deleteAfter(){
        IO.deleteFiles();
        IO.createFiles();
    }

    public void resetATM() {
        ATM.setATMFunds(0);
        for (Card c : ATM.getCards()) {
            ATM.removeCard(c);
        }
        for (Admin a : ATM.getAdmins()) {
            ATM.removeAdmin(a);
        }
        for (Account a : ATM.getAccounts()) {
            ATM.removeAccount(a);
        }
    }

    @Test
    public void loadTest(){
        IO.load();
        assertNotNull(ATM.getAccount(75339922));
        assertNotNull(ATM.getAccount(56632412));
        assertNotNull(ATM.getAccount(15384219));
        assertNotNull(ATM.getAccount(21354356));
        assertNull(ATM.getAccount(45352424));

        assertEquals(123342.54,ATM.getAccount(75339922).getBalance());
        assertEquals(213543.22,ATM.getAccount(56632412).getBalance());
        assertEquals(120.00,ATM.getAccount(15384219).getBalance());
        assertEquals(624124.01,ATM.getAccount(21354356).getBalance());

        assertNotNull(ATM.getCard(54323242));
        assertNotNull(ATM.getCard(65312422));
        assertNotNull(ATM.getCard(76524234));

        assertEquals(99999.50, ATM.getFunds());
    }

    @Test
    public void saveAccountsTest(){
        resetATM();
        ATM.addAccount(new Account(11111111,333333));
        ATM.addAccount(new Account(22222222,555555));
        ATM.addAccount(new Account(33333333,666666));
        ATM.addAccount(new Account(44444444,222222));

        IO.save();
        resetATM();
        IO.load();

        assertNotNull(ATM.getAccount(11111111));
        assertNotNull(ATM.getAccount(22222222));
        assertNotNull(ATM.getAccount(33333333));
        assertNotNull(ATM.getAccount(44444444));

        // Default accounts should not be loaded
        assertNull(ATM.getAccount(75339922));
        assertNull(ATM.getAccount(56632412));
        assertNull(ATM.getAccount(15384219));
        assertNull(ATM.getAccount(21354356));
    }

    @Test
    public void saveCardsTest(){
        //Start with clean ATM
        resetATM();
        Account testAccount = new Account(22222222,555555);
        ATM.addAccount(testAccount);
        ATM.addCard(new Card(11111111,1111,testAccount, LocalDate.now(), LocalDate.now(),"Valid"));
        ATM.addCard(new Card(12345678,1111,testAccount, LocalDate.now(), LocalDate.now(),"Valid"));
        ATM.addCard(new Card(98765432,1111,testAccount, LocalDate.now(), LocalDate.now(),"Valid"));
        //Save to file
        IO.save();
        //Reset ATM
        resetATM();
        //Load from files
        IO.load();
        //Check if added cards saved and loaded correctly in file
        assertNotNull(ATM.getCard(11111111));
        assertNotNull(ATM.getCard(12345678));
        assertNotNull(ATM.getCard(98765432));

        // Default cards should not be loaded
        assertNull(ATM.getAccount(54323242));
        assertNull(ATM.getAccount(65312422));
        assertNull(ATM.getAccount(76524234));
    }

    @Test
    public void saveATMTest(){
        resetATM();

        Admin admin1 = new Admin("John",101010,101012);
        Admin admin2 = new Admin("Bill",121212,232323);

        ATM.setATMFunds(9191919);
        ATM.addAdmin(admin1);
        ATM.addAdmin(admin2);

        IO.save();
        resetATM();
        IO.load();

        //Funds should be set to funds loaded from file
        assertEquals(9191919, ATM.getFunds());

        //Admins should be loaded in
        assertNotNull(ATM.getAdmin(101010));
        assertNotNull(ATM.getAdmin(121212));

    }


    @Test
    public void saveTransactionsTest()
    {
        resetATM();

        Account testAccount1 = new Account(22222222,555555);
        Account testAccount2 = new Account(33333333,555555);

        Transaction t1 = new Transaction(testAccount1,"withdraw",121212,100);
        Transaction t2 = new Transaction(testAccount2,"withdraw",121213,250);
        Transaction t3 = new Transaction(testAccount1,"deposit",121214,700);
        Transaction t4 = new Transaction(testAccount2,"deposit",121215,650);

        testAccount1.addTransaction(t1);
        testAccount1.addTransaction(t3);
        testAccount2.addTransaction(t2);
        testAccount2.addTransaction(t4);

        //Only add testAccount1 to the ATM.
        ATM.addAccount(testAccount1);

        IO.save();
        resetATM();
        IO.load();

        //Only two transactions should be loaded from testAccount1
        for(Account acc : ATM.getAccounts()){
            assertTrue(acc.getInvoices().contains(t1));
            assertTrue(acc.getInvoices().contains(t3));
        }
    }
}
