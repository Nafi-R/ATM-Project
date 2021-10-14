package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javaATM.Account;
import javaATM.Card;
import javaATM.GUI.homeScreen;
import javaATM.GUILogic.guiLogic;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class homeScreenTest {

    static Account acc = new Account(50, 500);
    static Card card = new Card(1000, 150, acc);
    static List<Card> cardsList = new ArrayList<Card>();
    //static homeScreen home = new homeScreen(cardsList);
    
    //logic tests
    @Test
    void testCardValid() {
        Card validCard = new Card(1234,1234,acc,LocalDate.of(2020,5,20), LocalDate.of(2023,7,15), "Valid");
        Card expiredCard = new Card(1234,1234,acc,LocalDate.of(2020,5,20), LocalDate.of(2020,7,15), "Valid");
        Card unactivatedCard = new Card(1234,1234,acc,LocalDate.of(2023,5,20), LocalDate.of(2024,7,15), "Valid");
        Card stolenCard = new Card(1234,1234,acc,LocalDate.of(2020,5,20), LocalDate.of(2020,7,15), "Stolen");
        Card lostCard = new Card(1234,1234,acc,LocalDate.of(2020,5,20), LocalDate.of(2020,7,15), "Lost");
        Card otherCard = new Card(1234,1234,acc,LocalDate.of(2020,5,20), LocalDate.of(2020,7,15), "Blocked");

        //Correct Passcode
        assertEquals(1, guiLogic.checkCard(validCard,"1234"));
        //Wrong Passcode
        assertEquals(-1, guiLogic.checkCard(validCard,"12343"));
        //Expired Card
        assertEquals(-2, guiLogic.checkCard(expiredCard,"1234"));
        //Unactivated card
        assertEquals(-3, guiLogic.checkCard(unactivatedCard,"1234"));
        //Stolen Card
        assertEquals(-4, guiLogic.checkCard(stolenCard,"1234"));
        //Lost card
        assertEquals(-4, guiLogic.checkCard(lostCard,"1234"));
        //Other card
        assertEquals(-4, guiLogic.checkCard(otherCard,"1234"));

    }

    @Test
    void testDateValid() {
        LocalDate issue = LocalDate.of(2021, 10, 1);
        LocalDate expiry = LocalDate.of(2022, 9, 1);
        assertEquals(false, guiLogic.isDateValid(issue, expiry));

        issue = LocalDate.of(2021, 9, 1);
        expiry = LocalDate.of(2022, 9, 1);
        assertEquals(true, guiLogic.isDateValid(issue, expiry));
    }

    //gui tests
    //@Test
    //void testNodesAreLoaded() {
    //    assertEquals(7, home.getHomeScreen().getChildren().size());
    //}

}
