/*
Záměr vzoru 
- Zapouzdřit požadavek do objektu, který obsahuje všechny informace o požadavku. 
- Umožňuje předat tento objekt jako argument metody, odložit nebo zařadit provedení požadavku do fronty a provést vrácení požadavku zpět. 

Problém, který tento vzor řeší 
- Vysoký počet podtříd, které by se mohly rozbít, pokud bychom upravili základní třídu 
- Kód grafického uživatelského rozhraní závisí na obchodní logice 
- Duplikace kódu, pokud potřebujeme vyvolat stejnou funkci z různých částí programu

Kdy chceme tento vzor použít? 
- Parametrizování objektů pomocí operací 
- Řazení operací do fronty nebo jejich plánování či vzdálené provádění 
- Implementace reverzibilních operací (nepovinné) 

----------------------------

Výhody 
- SRP => kód pro vyvolání operací je oddělen od kódu, který operace provádí 
- OCP => nový příkaz znamená, že vytvoříme novou třídu místo úpravy jiného kódu 
- Sestavení více příkazů 

Nevýhody 
- Vzory příkazů zavádějí novou vrstvu složitosti mezi odesílateli a příjemci





-------------------------------------Priklad-------------------------------------

- Mame cisnika, se kterym komunikuji zakaznici a davaji mu prikazy jako pripravit pizzu, pripravit burger, atd.
- Cisnik ma pristup ke kuchyni a dokaze tyto prikazy vykonavat.
- Ukol: Simulujte pomoci Command patternu.
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();

        Command preparePizzaCommand = new PreparePizzaCommand(kitchen, 42);
        Command prepareBurgerCommand = new PrepareBurgerCommand(kitchen, "chicken");

        List<Command> commandQueue = new ArrayList<>();

        commandQueue.add(prepareBurgerCommand);
        commandQueue.add(prepareBurgerCommand);

        while (!commandQueue.isEmpty()) {
            // pick a command and execute it
        }
    }

}

//------------------------Command(interface)------------------------

public interface Command {

    void execute();

}

//------------------------Kitchen------------------------

public class Kitchen {

    void prepareBurger(String meat) {
        System.out.println("Preparing burger with " + meat);
    }

    void preparePizza(int diameter) {
        System.out.println("Preparing pizza with " + diameter + " diameter");
    }

}

//------------------------PrepareBurgerCommand------------------------

public class PrepareBurgerCommand implements Command {

    private final Kitchen kitchen;

    private final String meat;

    public PrepareBurgerCommand(Kitchen kitchen, String meat) {
        this.kitchen = kitchen;
        this.meat = meat;
    }

    @Override
    public void execute() {
        kitchen.prepareBurger(meat);
    }
}

//------------------------PreparePizzaCommand------------------------

public class PreparePizzaCommand implements Command {

    private final Kitchen kitchen;

    private final int diameter;

    public PreparePizzaCommand(Kitchen kitchen, int diameter) {
        this.kitchen = kitchen;
        this.diameter = diameter;
    }

    @Override
    public void execute() {
        kitchen.preparePizza(diameter);
    }
}

