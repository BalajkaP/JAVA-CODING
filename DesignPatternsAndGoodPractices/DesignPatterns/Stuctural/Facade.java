/*
Záměr tohoto vzoru 
- Poskytuje zjednodušené rozhraní pro knihovnu nebo jinou složitou sadu tříd. 

Problém, který tento vzor řeší 
- Těsné propojení obchodní logiky nebo knihovních tříd s klientským kódem

Kdy chceme tento vzor použít 
- Pokud potřebujeme pracovat se složitým subsystémem, ale nechceme náš kód znečišťovat obchodní logikou. 
- Pokud nám stačí jednoduché rozhraní, místo abychom pracovali přímo s mnoha třídami 
- Pokud chceme naši aplikaci strukturovat do vrstev 

----------------------------

Výhody 
- Izolování složitých částí kódu 

Nevýhody 
- Fasády se mohou poměrně rychle rozrůstat, mohou se z nich stát boží objekty

----------------------------

Příklad
- Řekněme, že si chcete půjčit peníze od banky.
- Proces je velmi složitý - musí si ověřit, že nemáte finanční potíže, musíte si založit účet a i převod peněz z banky na váš účet má spoustu úskalí.
- Máte však mocnou fasádu - svého bankovního poradce!
- Většinu složitých věcí udělá za vás, takže se nemusíte se vším trápit.




-------------------------------------Priklad-------------------------------------

- Zakaznik po nas chce, abychom mu implementovali bankovni system. 
- Mame uz hotove subsystemy pro zakaznika, banku a bakovni ucet. 
- Ty mu ale dat nechceme - jednak by mu implementace logiky udelala v systemu bordel, a jednak ho konkretni tridy nezajimaji, chce pouze funckionalitu.
- Ukol: implementujte pomoci facade patternu
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer("Michal", 123);
        Customer customer2 = new Customer("David", 345);


        // Instead of directly working with these subsystems, we can create a facade that gives us what we want!
        // But we still have to implement the functionality in the facade.

//        Account account1 = new Account(123, 5000);
//        Account account2 = new Account(345, 10000);
//
//        Bank bank = new Bank();
//
//        bank.verifyCustomer(customer);
//        bank.verifyCustomer(customer2);
//
//        bank.createAccount(customer);
//        bank.createAccount(customer2);

        BankingServiceFacade bankingServiceFacade = new BankingServiceFacadeImpl();

        bankingServiceFacade.deposit(customer, 1233, 200);
        bankingServiceFacade.withdraw(customer2, 234423, 300);
    }

}

//------------------------BankingServiceFacade(interface)------------------------

public interface BankingServiceFacade {

    void deposit(Customer customer, long accountNumber, double amount);

    void withdraw(Customer customer, long accountNumber, double amount);

}

//------------------------BankingServiceFacadeImpl------------------------

public class BankingServiceFacadeImpl implements BankingServiceFacade {
    @Override
    public void deposit(Customer customer, long accountNumber, double amount) {
        // interaction with bank, account, customer, ...
        System.out.println(String.format("Customer verified! Deposited %s to account %s", amount, accountNumber));
    }

    @Override
    public void withdraw(Customer customer, long accountNumber, double amount) {
        // interaction with bank, account, customer, ...
        System.out.println(String.format("Customer verified! Withdrawn %s from account %s", amount, accountNumber));
    }
}

//------------------------Account------------------------

public class Account {
    private long accountNumber;
    private double balance;

    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
}

//------------------------Bank------------------------

public class Bank {
    public boolean verifyCustomer(Customer customer) {
        // Simulate customer verification logic
        System.out.println("Verifying customer: " + customer.getName());
        return true;
    }

    public void createAccount(Customer customer) {
        // Simulate account creation logic
        long accountNumber = (long) (Math.random() * 100000);
        double initialBalance = 0.0;
        Account account = new Account(accountNumber, initialBalance);
        System.out.println("Account created for customer: " + customer.getName());
        System.out.println("Account Number: " + account.getAccountNumber());
    }
}

//------------------------Customer------------------------

public class Customer {
    private String name;
    private long customerId;

    public Customer(String name, long customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public long getCustomerId() {
        return customerId;
    }
}