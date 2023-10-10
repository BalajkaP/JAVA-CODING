/*
Záměr vzoru 
- Rozdělit třídu nebo sadu tříd do dvou hierarchií 
- Tyto hierarchie lze rozšiřovat nezávisle na sobě 
- Nazýváme je abstrakce a implementace 

Problém, který tento vzor řeší
- Exploze tříd z použití dědičnosti pro podporu nových kombinací chování 
- Dědičnost => kompozice


Kdy chceme tento vzor použít? 
- Pokud můžeme problém rozdělit na řízení a implementaci 
- Pokud chceme rozšířit třídu v několika dimenzích 
- Pokud chceme mít možnost přepínat mezi těmito implementacemi za běhu systému 

----------------------------

Výhody 
- Pracujeme s abstrakcemi a implementační detaily schováváme do vlastních tříd 
- OCP => rozšíření chování znamená přidání nových tříd namísto úpravy stávajících tříd 
- SRP => abstrakce řeší ovládání, implementace řeší chování 

Nevýhody 
- Může způsobit, že kód bude složitější

----------------------------

Příklad
- Řekněme, že máme aplikaci s grafickým uživatelským rozhraním, která funguje v systémech Windows, Linux, iOS a Android.
- Najednou chceme přidat druhé GUI pro administrátory a třetí GUI pro VIP zákazníky
- Exponenciální růst tříd a změna základní třídy by byla fatální
- Řešení: Most, kde GUI je abstrakce a OS je implementace





-------------------------------------Priklad-------------------------------------

- Chceme naprogramovat situaci, kdy mame nejaky ovladac a nejake zarizeni.
- Vime od zakaznika, ze casem bude chtit vyrabet nove ovladace, ale chceme mit kompatibilitu se starymi zarizenimi.
- Taky vime, ze bude vyrabet i nove zarizeni. Pri pridani noveho ovladace nebo zarizeni nechceme rozbit kod.\
- Ukol: implementujte pomoci bridge patternu
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        Device television = new Television();
        Device radio = new Radio();
        Device console = new Console();

        TouchscreenRemoteControl remoteControlForTv = new TouchscreenRemoteControl(television);

        remoteControlForTv.tapOnScreen();

        RemoteControl remoteControlForRadio = new RemoteControl(radio);

        remoteControlForRadio.commandTurnOn();

        RemoteControl remoteControlForConsole = new RemoteControl(console);

        remoteControlForConsole.commandTurnOn();

    }

}

//------------------------Device(interface)------------------------

public interface Device {

    boolean turnOn();
    void turnOff();
    void increaseVolume();
    void decreaseVolume();

}

//------------------------RemoteControl------------------------

public class RemoteControl {

    protected final Device device;

    public RemoteControl(Device device) {
        this.device = device;
    }

    public boolean commandTurnOn() {
        return device.turnOn();
    }

    public void commandTurnOff() {
        device.turnOn();
    }
}

//------------------------TouchscreenRemoteControl------------------------

public class TouchscreenRemoteControl extends RemoteControl {
    public TouchscreenRemoteControl(Device device) {
        super(device);
    }

    public void tapOnScreen() {
        System.out.println("Tapping on screen!");
        device.turnOn();
    }
}

//------------------------Console------------------------

public class Console implements Device {
    @Override
    public boolean turnOn() {
        System.out.println("Turning on console");
        return true;
    }

    @Override
    public void turnOff() {
        System.out.println("Turning off console");
    }

    @Override
    public void increaseVolume() {
        System.out.println("Increasing volume on console");
    }

    @Override
    public void decreaseVolume() {
        System.out.println("Decreasing volume on console");
    }
}

//------------------------Radio------------------------

public class Radio implements Device {

    @Override
    public boolean turnOn() {
        System.out.println("Turning on radio");
        return true;
    }

    @Override
    public void turnOff() {
        System.out.println("Turning off radio");
    }

    @Override
    public void increaseVolume() {
        System.out.println("Increasing volume on radio");
    }

    @Override
    public void decreaseVolume() {
        System.out.println("Decreasing volume on radio");
    }
}

//------------------------Television------------------------

public class Television implements Device {
    @Override
    public boolean turnOn() {
        System.out.println("Turning on TV");
        return true;
    }

    @Override
    public void turnOff() {
        System.out.println("Turning off TV");
    }

    @Override
    public void increaseVolume() {
        System.out.println("Increasing volume on TV");
    }

    @Override
    public void decreaseVolume() {
        System.out.println("Decreasing volume on TV");
    }
}
