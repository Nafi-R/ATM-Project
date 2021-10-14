package javaATM.GUILogic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javaATM.ATM;
import javaATM.Admin;
import javaATM.Card;
import javafx.scene.control.TextField;

public class guiLogic {
    public ArrayList<Integer> deposits = new ArrayList<>();

    public static int checkCard(Card card, String inputPin){
        /* return 0 -> null card
        *  return 1 -> valid card
        *  return -1 -> incorrect pin
        *  return -2 -> Expired card
        *  return -3 -> card not activated yet
        *  return -4 -> card stolen/lost/blocked/other
        */
        if(card == null) return 0;
        if(card.getStatus().equals("Valid")) {
            try{
                if (inputPin.equals("")) return -1;
                int pin = Integer.parseInt(inputPin);
                if(card.getPin() == pin) return 1;
                else return -1;
            }catch(NumberFormatException e){
                return -1;
            }
        }
        if(card.getStatus().equals("Expired")) return -2;
        if(card.getStatus().equals("Not activated yet")) return -3;
        return -4;
    }



    public static boolean isDateValid(LocalDate issue, LocalDate expiry) {
        int compareValIssue = issue.compareTo(LocalDate.now());
        int compareValExpiry = expiry.compareTo(LocalDate.now());
        if(compareValIssue < 0 && compareValExpiry > 0) {
            return true;
        }
        return false;
    }

    //methods in adminLoginScreen
    public static boolean isInt(String input){
        try{
            int result = Integer.parseInt(input);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean checkValidAdmin(int id, int passcode) {
        boolean verified = false;
        for (Admin admin: ATM.getAdmins()) {
            if (admin.getAdminID() == id && admin.getPasscode() == passcode) {
                verified = true;
            }
        }
        return verified;
    }



    public void addToDeposit(int i) {
        System.out.println("hmm");
        deposits.add(i);
    }

    public int addAmount() {
        int total = 0;
        for(int i =0; i < deposits.size(); i++) {
            total+=deposits.get(i);
        }
        return total;
//        amountText.setText(Integer.toString(total));
    }
    public int depositsSize() {
        return deposits.size();
    }
    public int undoAmount() {
        if(deposits.size() > 0) {
            deposits.remove(deposits.size() - 1);
            return addAmount();
        }
        return 0;
    }
    public int resetAmount() {
        deposits.clear();
        return addAmount();
    }
}
