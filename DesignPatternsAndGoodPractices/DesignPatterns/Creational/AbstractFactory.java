/*
Záměr vzoru 
- Vytvořit rodiny příbuzných objektů bez specifikace konkrétních tříd 

Problém, který řeší 
- Způsob vytváření objektů tak, aby odpovídaly jiným objektům stejné rodiny 

Kdy jej používáme 
- Když kód potřebuje pracovat s různými rodinami příbuzných produktů a nechceme být závislí na konkrétních třídách těchto produktů 

----------------------------

Mame domenu: 
Chceme vyrabet ruzne druhy nabytku (zidle, gauce) 
Chceme je vyrabet ve stylu viktorianske anglie a ve stylu gotiky 

AbstractProducts budou zidle a gauce 

ConcreteProductA1 je viktorianska zidle 
ConcreteProductA2 je goticka zidle 

ConcreteFactory1 Viktorianska tovarna 
ConcreteFactory2 bude Goticka tovarna 

----------------------------

Výhody 
- Pokud používáme jednu betonárnu, můžeme si být jisti, že výrobky jsou kompatibilní. 
- Žádné těsné spoje 
- SRP => tvorba výrobku je izolovaná 
- OCP => můžeme zavádět nové produkty a továrny, aniž bychom měnili stávající kód 

Nevýhody:
- Mnoho tříd k implementaci 



-------------------------------------Priklad-------------------------------------

- Chceme vyrabet ruzne druhy nabytku (zidle, gauce)
- Chceme je vyrabet ve stylu viktorianske anglie a ve stylu gotiky
*/

//------------------------Main------------------------

public class Main {


    public static void main(String[] args) {

        //AbstractFurnitureFactory factory = new GothicFurnitureFactory();
        AbstractFurnitureFactory factory = new VictorianFurnitureFactory();

        Chair gothicChair = factory.makeChair();
        Chair victorianChair = factory.makeChair();

        gothicChair.printInfo();
        victorianChair.printInfo();
    }

}

//------------------------Chair(abstract)------------------------

public abstract class Chair {

    abstract void printInfo();

}

//------------------------Sofa(abstract)------------------------

public abstract class Sofa {

    abstract void printInfo();

}

//------------------------GothicChair------------------------

public class GothicChair extends Chair {
    @Override
    void printInfo() {
        System.out.println("printing gothic chair");
    }
}

//------------------------GothicSofa------------------------

public class GothicSofa extends Sofa {
    @Override
    void printInfo() {
        System.out.println("printing gothic sofa");
    }
}

//------------------------VictorianChair------------------------

public class VictorianChair extends Chair {
    @Override
    void printInfo() {
        System.out.println("printing victorian chair");
    }
}

//------------------------VictorianSofa------------------------

public class VictorianSofa extends Sofa {
    @Override
    void printInfo() {
        System.out.println("printing victorian sofa");
    }
}

//------------------------AbstractFurnitureFactory(Interface)------------------------

public interface AbstractFurnitureFactory {

    Chair makeChair();

    Sofa makeSofa();
}

//------------------------GothicFurnitureFactory------------------------

public class GothicFurnitureFactory implements AbstractFurnitureFactory {
    @Override
    public Chair makeChair() {
        return new GothicChair();
    }

    @Override
    public Sofa makeSofa() {
        return new GothicSofa();
    }
}

//------------------------VictorianFurnitureFactory------------------------

public class VictorianFurnitureFactory implements AbstractFurnitureFactory {
    
    @Override
    public Chair makeChair() {
        return new VictorianChair();
    }

    @Override
    public Sofa makeSofa() {
        return new VictorianSofa();
    }
}


