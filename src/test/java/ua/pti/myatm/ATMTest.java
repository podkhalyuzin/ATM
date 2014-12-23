/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

/**
 *
 * @author andrii
 */
public class ATMTest {

    @Test
    public void testGetMoneyInATM() {
        System.out.println("getMoneyInATM");
        Card card=null;
        Account account=null;
        ATM instance =new ATM(12000,card,account);
        double expResult = 12000;
        double result = instance.getMoneyInATM();
        assertEquals(expResult,result,0.01); 
    }

    @Test
    public void testValidateCardWhenCardIsBlocked() {
        System.out.println("validateCard");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        int pinCode = 4352;
        ATM instance = new ATM(4000,card,account);
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(anyInt())).thenReturn(true);
        boolean result=instance.validateCard(card,pinCode);
        assertFalse(result);
    }
    
     @Test
    public void testValidateCardWithWrongPinCode() {
        System.out.println("validateCard");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        int pinCode = 1731;
        ATM instance = new ATM(4000,card,account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        boolean result=instance.validateCard(card,pinCode);;
        assertFalse(result);

    }
    
        public void testValidateCardWithRightCredentionals() {
        System.out.println("validateCard");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        int pinCode = 3435;
        ATM instance = new ATM(4000,card,account);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(true);
        boolean result=instance.validateCard(card,pinCode);
        verify(card,times(1)).isBlocked();
        verify(card,times(1)).checkPin(3435);
        assertTrue(result);
        
    }

    @Test
    public void testCheckBalance() {
        System.out.println("checkBalance");
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        ATM instance = new ATM(23000,card,account);
        when(instance.checkBalance()).thenReturn(1032.00);
        double ExpResult=1032.00;
        double result=instance.checkBalance();
        verify(account,times(1)).getBalance();
        assertEquals(ExpResult,result,0.01);
    }

    @Test 
    public void testGetCashWhenInATMandInAccountEnougthMoney() throws NotEnoughMoneyInATM,NotEnoughMoneyInAccount {
        System.out.println("getCash");
        Account account=mock(Account.class);       
        Card card = mock(Card.class);
        double amount = 3800.00;
        ATM instance = new ATM(43000,card,account);
        when(account.withdrow(amount)).thenReturn(amount);
        when(account.getBalance()).thenReturn(4000.00);      
        double result=instance.getCash(amount);
        double ExpResult=200.00;
        InOrder inOrder = inOrder(account,card);
        inOrder.verify(account,times(2)).getBalance();
        inOrder.verify(account,times(1)).withdrow(amount);
        assertEquals(ExpResult,result,0.01);
    }
  
  @Test (expected=NotEnoughMoneyInATM.class)
    public void testGetCashWhenNotEnoughMoneyInATM() throws NotEnoughMoneyInATM,NotEnoughMoneyInAccount{
        System.out.println("getCash");
        Account account=mock(Account.class);       
        Card card = mock(Card.class);
        ATM instance= new ATM(200,card,account);
        double amount = 400;
        instance.getCash(amount);
 }
 

  @Test (expected=NotEnoughMoneyInAccount.class)
    public void testGetCashWhenNotEnoughMoneyInAccount() throws NotEnoughMoneyInATM,NotEnoughMoneyInAccount{
        System.out.println("getCash");
        Account account=mock(Account.class);       
        Card card = mock(Card.class);
        ATM instance= new ATM(900,card,account);
        when(account.getBalance()).thenReturn(130.00);
        double amount = 400;
        instance.getCash(amount);
 }
 
@Test (expected=NullPointerException.class)
public void testGetCashWhenAccountIsNotLogin() throws NullPointerException,NotEnoughMoneyInAccount,NotEnoughMoneyInATM
    {
     Card card = mock(Card.class);
     ATM instance = new ATM(43000,card,null); 
     instance.getCash(12000.00);
    }

@Test (expected=NullPointerException.class)
public void testCheckBalanceWhenAccountIsNotLogin() throws NullPointerException,NotEnoughMoneyInAccount,NotEnoughMoneyInATM
    {
     Card card = mock(Card.class);
     ATM instance = new ATM(43000,card,null); 
     instance.checkBalance();
    }

@Test
public void testGetCashWhenAmountEqualsCash() throws NullPointerException,NotEnoughMoneyInATM,NotEnoughMoneyInAccount
    {
        Card card = mock(Card.class);
        Account account = mock(Account.class);
        ATM instance = new ATM(30000,card,account);
        double amount = 300.00;
        when(account.getBalance()).thenReturn(300.00);
        when(account.withdrow(amount)).thenReturn(amount);
       double result= instance.getCash(amount);
       double ExpResult = 0.00;
       assertEquals(result,ExpResult,0.01);   
    }



}