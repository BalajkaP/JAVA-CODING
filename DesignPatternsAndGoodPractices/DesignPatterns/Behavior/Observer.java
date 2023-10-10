/*
Záměr vzoru 
- Definuje mechanismus odběru pro oznámení události více objektům. 
- Objekty se přihlásí k odběru jiného objektu, který pak posílá zprávy tomuto objektu. 

Problém, který tento vzor řeší 
- Místo sledování objektu a opakovaného dotazování se na jeho stav necháme sledovaný objekt, aby nás informoval o aktualizaci 
- Příklad: 
    - Hledáte na určitých webových stránkách nové nabídky domů a každý den je kontrolujete, zda se na nich neobjeví nějaká výhodná nabídka. 
    - Většina těchto kontrol je zbytečná, protože žádné nové nabídky nebyly vytvořeny. 
    - Místo toho se přihlásíte k odběru webové stránky a ta vás upozorní na všechny nové nabídky.

Kdy chceme tento vzor použít? 
- Pokud potřebujeme provádět operace v objektech, pokud se změní stav jiného objektu. 
- Pokud potřebujeme objekt sledovat ve zvláštních případech nebo po omezenou dobu 
 
----------------------------

Důležité věci: 
- Odběratelé se přihlásí k odběru objektu Publisher. 
- Vydavatel má důležitý stav, který chceme znát 
- Subcribers jsou objekty, které musí něco provést, pokud se změní stav objektu Publisher

----------------------------

Výhody 
- OCP => snadné přidávání nových účastníků 
- Odběry a odhlášení odběrů za běhu systému 

Nevýhody 
- Složitost kódu navíc





-------------------------------------Priklad-------------------------------------

- Problemova domena: Vytvorte system pro vypisovani a sledovani nemovitosti
- Ukol: Implementujte Observer pattern pro zasilani notifikaci uzivatelum
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        HouseListing houseListing = new HouseListing("Byt 3+kk", 3000000);
        HouseListing houseListing2 = new HouseListing("Byt 1+kk", 7000000);
        HouseListing houseListing3 = new HouseListing("Dum se zahradou v Usti", 450000);

        HouseListingService houseListingService = new HouseListingService();

        User user1 = new User("honza.mrazek@gmail.com");
        User user2 = new User("jan.novak@gmail.com");
        User user3 = new User("petr.novotny@gmail.com");

        houseListingService.subscribe(user1);
        houseListingService.subscribe(user2);

        houseListingService.addNewHouseListing(houseListing);

        houseListingService.unsubscribe(user2);

        System.out.println("_____________________________________________________");

        houseListingService.addNewHouseListing(houseListing2);
    }

}

//------------------------HouseListing(record)------------------------

public record HouseListing(String name, double price) {
}

//------------------------HouseSubscriber(interface)------------------------

public interface HouseSubscriber {

    void update(HouseListing houseListing);

}

//------------------------User------------------------

public class User implements HouseSubscriber {

    private String email;

    public User(String email) {
        this.email = email;
    }

    @Override
    public void update(HouseListing houseListing) {
        System.out.println("Sending house listing: " + houseListing.toString() + " to email: " + email);
    }
}

//------------------------HousePublisher(interface)------------------------

public interface HousePublisher {

    void subscribe(HouseSubscriber subscriber);

    void unsubscribe(HouseSubscriber subscriber);

    void notifySubscribers(HouseListing houseListing);

}

//------------------------HouseListingService------------------------

public class HouseListingService implements HousePublisher {

    private List<HouseSubscriber> subscriberList = new ArrayList<>();

    private List<HouseListing> houseListings = new ArrayList<>();

    void addNewHouseListing(HouseListing houseListing) {
        System.out.println("New house listing added!!!!");
        houseListings.add(houseListing);
        notifySubscribers(houseListing);
    }

    @Override
    public void subscribe(HouseSubscriber subscriber) {
        subscriberList.add(subscriber);
    }

    @Override
    public void unsubscribe(HouseSubscriber subscriber) {
        subscriberList.remove(subscriber);
    }

    @Override
    public void notifySubscribers(HouseListing houseListing) {
        for (HouseSubscriber houseSubscriber : subscriberList) {
            houseSubscriber.update(houseListing);
        }
    }
}