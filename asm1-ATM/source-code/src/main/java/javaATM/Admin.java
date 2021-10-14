package javaATM;

public class Admin{
    private String name;
    private int adminID;
    private int passcode;

    public Admin(String name, int adminID, int passcode){
        this.name = name;
        this.adminID = adminID;
        this.passcode = passcode;
    }
    public void setName(String n){
        name = n;
    }
    public String getName(){
        return name;
    }
    public void setAdminID(int x){
        adminID = x;
    }
    public int getAdminID(){
        return adminID;
    }
    public void setPasscode(int x){
        passcode = x;
    }
    public int getPasscode(){
        return passcode;
    }

    public static void main(String[] args){
    }
}
