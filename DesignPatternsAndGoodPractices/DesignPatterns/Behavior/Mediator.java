/*
Záměr vzoru 
- Umožňuje objektům spolupracovat prostřednictvím zprostředkujícího objektu namísto vzájemné komunikace. 
- Snižuje chaotické závislosti 
- Zapouzdřuje složitou síť vztahů do jediného objektu zprostředkovatele 

Problém, který tento vzor řeší 
- Spojování tříd dohromady způsobené režií komunikace 
- Příklad: Máte textové pole, které se vykreslí pouze v případě, že zaškrtnete políčko => dvě spojené třídy

Kdy chceme tento vzor použít? 
- Pokud je obtížné změnit nebo znovu použít komponenty kvůli jejich těsnému propojení. 
- Pokud začnete vytvářet mnoho podtříd jen proto, abyste mohli znovu použít některé společné chování 

----------------------------

Důležité věci: 
- Mediátor je rozhraní, takže tyto komponenty můžete opakovaně použít tak, že je propojíte s jiným konkrétním objektem Mediátoru. 
- KonkrétníMediátor sleduje vztahy a rozhoduje o tom, co se má stát, pokud je vyvolána funkce notify(). 
- Třídy komponent nevědí o ničem jiném než o mediátoru

----------------------------

Výhody 
- SRP => komunikační režie žije ve vlastní třídě 
- OCP => nový způsob komunikace = vytvoření nového prostředníka namísto vytváření podtříd nebo úprav spousty komponent 
- Volná vazba 
- Snadné opakované použití komponent, které závisí pouze na mediátoru 

Nevýhody 
- Mediátor má tendenci zpracovávat příliš mnoho věcí najednou

----------------------------

Příklad 
- Řekněme, že máme na obrazovce nějaká tlačítka, dialogová okna, zaškrtávací políčka, vstupy. 
- Pokud klikneme na jedno tlačítko, chceme vynulovat všechna zaškrtávací políčka a spustit dialogové okno 
- Pokud do jednoho pole napíšeme nějaký vstup, chceme zobrazit nápovědu 
- To znamená spoustu chaotických vzájemných závislostí 
- Řešení: Použijte objekt prostředníka!




-------------------------------------Priklad-------------------------------------

- Vasim ukolem je implementovat komunikaci mezi letadly. 
- Protoze letadel je spousta a komunikace kazdy s kazdym by byla chaoticka, vymyslete reseni, jak komunikaci dat stabni kulturu.
- Ukol: Implementujte komunikaci mezi letadly pomoci Mediator patternu
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        AirTrafficControlTower tower = new AirTrafficControlTower();

        Aircraft plane = new Aircraft(tower, "ALPHA-1");
        Aircraft chopper = new Aircraft(tower, "OMEGA-3");
        Aircraft zeppelin = new Aircraft(tower, "BETA-24");


        tower.registerAircraft(plane);
        tower.registerAircraft(chopper);
        tower.registerAircraft(zeppelin);

        plane.sendMessage("Hello world!");
        System.out.println("___________________________________________________________________");
        chopper.sendMessage("What's up?");
        System.out.println("___________________________________________________________________");
        zeppelin.sendMessage("Crash imminent.");
    }
}

//------------------------AirTrafficMediator(interface)------------------------

public interface AirTrafficMediator {

    void registerAircraft(Aircraft aircraft);

    void forwardMessage(Aircraft sender, String message);

}

//------------------------AirTrafficControlTower------------------------

public class AirTrafficControlTower implements AirTrafficMediator {

    List<Aircraft> registeredAircrafts = new ArrayList<>();

    @Override
    public void registerAircraft(Aircraft aircraft) {
        registeredAircrafts.add(aircraft);
    }

    @Override
    public void forwardMessage(Aircraft sender, String message) {
        for (Aircraft aircraft : registeredAircrafts) {
            if (!aircraft.equals(sender)) {
                aircraft.receiveMessage(sender, message);
            }
        }
    }
}

//------------------------Aircraft------------------------

public class Aircraft {

    private AirTrafficMediator airTrafficMediator;

    private String id;

    public Aircraft(AirTrafficMediator airTrafficMediator, String id) {
        this.airTrafficMediator = airTrafficMediator;
        this.id = id;
    }

    public void sendMessage(String message) {
        System.out.println("Sending message from " + id + ": " + message);
        airTrafficMediator.forwardMessage(this, message);
    }

    public void receiveMessage(Aircraft sender, String message) {
        System.out.println("Receiving message from " + id + ": " + message);
    }

    public void setAirTrafficMediator(AirTrafficMediator airTrafficMediator) {
        this.airTrafficMediator = airTrafficMediator;
    }
}