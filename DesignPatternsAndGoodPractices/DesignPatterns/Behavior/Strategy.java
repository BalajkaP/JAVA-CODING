/*
Záměr vzoru 
- Definuje rodinu algoritmů 
- Každý z těchto algoritmů žije ve své vlastní třídě 
- Algoritmy můžeme za běhu měnit 

Problém, který tento vzor řeší 
- Pokud třída přímo závisí na algoritmu, bude jeho změna nebo přidání nového algoritmu obtížné

Kdy chceme tento vzor použít? 
- Pokud chceme problém řešit různými způsoby a přepínat mezi těmito strategiemi za běhu. 
- Pokud máme mnoho podobných tříd, které řeší problém, a liší se pouze způsobem řešení problému 
- Pokud chceme oddělit implementační detaily algoritmu a třídy, která tento algoritmus používá. 

----------------------------

Důležité věci: 
- Kontext je třída, která se chová jinak, pokud přepneme algoritmus. 
- ConcreteStrategy je třída, ve které implementujeme algoritmus - pouze tato třída se musí změnit, pokud potřebujeme změnit algoritmus 
- Klient rozhodne, která Strategie je vhodná, a nastaví ji do třídy Context

----------------------------

Výhody 
- Mezi strategiemi můžeme za běhu přepínat 
- SRP => kód pro provádění strategie je izolovaný 
- Nahrazuje dědičnost kompozicí 
- OCP => nová strategie = nová třída 

Nevýhody 
- Přináší do kódu složitost 
- Klienti musí znát strategie a vybrat vhodnou strategii



-------------------------------------Priklad-------------------------------------

- Vytvorte eshop, kde bude mozne si zakoupit produkt a zaplatit jej pres Apple Pay, kartou nebo nebo PayPalem
- Ukol: Implementujte Strategy pattern pro podporu vice metod zaplaceni produktu
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        Eshop eshop = new Eshop();

        // check what payment method was selected in Order
        eshop.setPaymentMethod(PaymentMethod.CARD);

        Product phone = new Product("Samsung Galaxy S10", 12000);

        eshop.buyProduct(phone);
    }

}

//------------------------Eshop------------------------

public class Eshop {

    PaymentService paymentService = new PaymentServiceImpl();

    void buyProduct(Product product) {
        System.out.println("Buying product: " + product.toString());
        paymentService.pay(product.price());
    }

    void setPaymentMethod(PaymentMethod paymentMethod) {
        switch (paymentMethod) {
            case PAYPAL -> paymentService.setStrategy(new PaymentByPayPalStrategy());
            case CARD -> paymentService.setStrategy(new PaymentByCardStrategy());
            case APPLE_PAY -> paymentService.setStrategy(new PaymentByApplePayStrategy());
        }
    }
}

//------------------------Product(record)------------------------

public record Product(String name, double price) {
}

//------------------------PaymentService(interface)------------------------

public interface PaymentService {

    void pay(double amount);

    void setStrategy(PaymentStrategy paymentStrategy);

}

//------------------------PaymentServiceImpl------------------------

public class PaymentServiceImpl implements PaymentService {

    PaymentStrategy paymentStrategy;

    @Override
    public void pay(double amount) {
        // ... misto ifovani toho, co mame zrovna za platebni pozadavek (apple pay, karta, ...)
        // proste zavolame strategii!
        paymentStrategy.pay(amount);
    }

    @Override
    public void setStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
}

//------------------------PaymentStrategy------------------------

public interface PaymentStrategy {

    void pay(double amount);

}

//------------------------PaymentByApplePayStrategy------------------------

public class PaymentByApplePayStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " by Apple Pay!");
    }
}

//------------------------PaymentByCardStrategy------------------------

public class PaymentByCardStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " by card!");
    }
}

//------------------------PaymentByPayPalStrategy------------------------

public class PaymentByPayPalStrategy implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " by PayPal!");
    }

}

//------------------------PaymentMethod(enum)------------------------

public enum PaymentMethod {
    CARD,
    APPLE_PAY,
    PAYPAL
}
