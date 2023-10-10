/*
Záměr 
- Připojení nového chování k objektům jejich zabalením do objektů, které nové chování provádějí. 

Problém, který řeší 
- Podtřída není vždy proveditelná => pouze jedna nadřazená třída a nemůžeme změnit chování existujícího objektu za běhu, museli bychom nahradit celý objekt 

Kdy ji používáme 
- Pokud je potřeba přiřadit další chování za běhu, aniž by se změnila struktura kódu. 
- Pokud dědičnost není dostatečně flexibilní 

----------------------------

Výhody 
- Přidání chování k objektu bez nutnosti přidávat k němu přímo podtřídu 
- Přidávání a odebírání dalších chování za běhu objektu 
- Kombinování těchto chování 

Nevýhody 
- Obtížné rozbalování objektů 
- Pořadí dekorátorů může být důležité 
- Mnoho "konfiguračních" tříd 



-------------------------------------Priklad-------------------------------------

- Vyrabite software pro autobusovou spolecnost. 
- Ta vam chce v pripade zpozdeni vaseho autobusu zaslat notifikaci do SMS, aplikace, na email, podle toho, co si vyberete pri objednani autobusu.
- Vyreste tento problem s pouzitim patternu Decorator.
*/

//------------------------Main------------------------

public class Main {
    public static void main(String[] args) {
        // SMS, email, aplikace, decorator

        // bus ma zpozdeni, posli notifikaci

        NotificationSender notificationSender = new EmailNotificationDecorator(new SmsNotificationDecorator(new AppNotificationDecorator()));
        notificationSender.sendNotification();

        notificationSender = new SmsNotificationDecorator(new AppNotificationDecorator());
    }
}

//------------------------NotificationSender(Interface)------------------------

public interface NotificationSender {

    void sendNotification();

}

//------------------------NotificationDecorator------------------------

public class NotificationDecorator implements NotificationSender {

    private NotificationSender wrapee;

    public NotificationDecorator(NotificationSender wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public void sendNotification() {
        wrapee.sendNotification();
    }
}

//------------------------AppNotificationDecorator------------------------

public class AppNotificationDecorator implements NotificationSender {

    @Override
    public void sendNotification() {

        // integrace na mobilni aplikaci

        System.out.println("Sending app notification to client..");

    }
}

//------------------------EmailNotificationDecorator------------------------

public class EmailNotificationDecorator extends NotificationDecorator {
    public EmailNotificationDecorator(NotificationSender wrapee) {
        super(wrapee);
    }

    @Override
    public void sendNotification() {

        // integrace na smtp server ktery posila emaily...
        super.sendNotification();

        System.out.println("Sending email to client..");
    }
}

//------------------------SmsNotificationDecorator------------------------

public class SmsNotificationDecorator extends NotificationDecorator {
    public SmsNotificationDecorator(NotificationSender wrapee) {
        super(wrapee);
    }

    @Override
    public void sendNotification() {

        super.sendNotification();

        // integrace na nejakou sms branu...

        System.out.println("Sending SMS to client..");
    }
}