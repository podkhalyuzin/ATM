package ua.pti.myatm;

public class MyATM {

    public static void main(String[] args)throws NotEnoughMoneyInAccount,NotEnoughMoneyInATM,NullPointerException {
        double moneyInATM = 1000;
        Card card = null;
        Account account=null;
        ATM atm = new ATM(moneyInATM,card,account);
        atm.validateCard(card, 1234); 
        atm.checkBalance();
        atm.getCash(999.99);        
    }
}
