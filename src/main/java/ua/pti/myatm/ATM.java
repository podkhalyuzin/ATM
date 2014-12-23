package ua.pti.myatm;

public class ATM {
        
    private double moneyInATM;
    private final Card card;
    private Account account;
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM,Card card,Account account){
         this.moneyInATM=moneyInATM;
         this.card=card;
         this.account=account;
    }
    
    // Возвращает каоличестов денег в банкомате
    public double getMoneyInATM() {
         return this.moneyInATM;
    }
        
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    public boolean validateCard(Card card, int pinCode){
        if(card.isBlocked())
          {  
              this.account=null;
              return false;
          }
        if(!card.checkPin(pinCode)) 
          {  
             this.account=null;
             return false;
          }
        this.account=card.getAccount();
        return true;        
    }
    
    //Возвращает сколько денег есть на счету
    public double checkBalance()throws NullPointerException{
        if (this.account!=null)
         return  account.getBalance();
         throw new NullPointerException() ;
    }
    
    //Метод для снятия указанной суммы
    //Метод возвращает сумму, которая у клиента осталась на счету после снятия
    //Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
    //Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
    //Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
    //При успешном снятии денег, указанная сумма должна списываться со счета, и в банкомате должно уменьшаться количество денег
    public double getCash(double amount) throws NullPointerException,NotEnoughMoneyInATM,NotEnoughMoneyInAccount{
        double exp=account.getBalance();
        if (this.account==null)
            throw new NullPointerException();
        if (moneyInATM<amount) 
         throw new NotEnoughMoneyInATM();
        if (amount>account.getBalance())
         throw new NotEnoughMoneyInAccount();
        account.withdrow(amount);
        return exp-amount; 
    }
}

class NotEnoughMoneyInATM extends Exception {
    public NotEnoughMoneyInATM(){};
}

class NotEnoughMoneyInAccount extends Exception {
    public NotEnoughMoneyInAccount() {};
}



