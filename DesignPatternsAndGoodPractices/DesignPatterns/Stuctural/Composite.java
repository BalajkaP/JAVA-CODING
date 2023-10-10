/*
Záměr vzoru 
- Skládat objekty do stromových struktur a pracovat s těmito objekty jednotně pomocí abstrakce. 

Problém, který řeší 
- Reprezentace stromových a hierarchických struktur v kódu 

Kdy jej používáme 
- Musíme implementovat stromovou strukturu objektů 
- Chceme provádět stejné operace nad kontejnery i položkami 

----------------------------

Výhody 
- Snadno šiřitelné metody, které by měly fungovat pro kontejnery i jednotlivé položky 
- Snadná implementace nových položek, které lze uložit do schránky (nové listy) 

Nevýhody 
- Těžko se rozhoduje o společném rozhraní, které musí dodržovat jak položky, tak kontejnery






-------------------------------------Priklad-------------------------------------

- Tvorite software na zakazku pro zasilkovou spolecnost. 
- Ta se rozhodla balit objednavky do vetsich krabic, v jedne krabici tedy muze byt bud produkt, nebo dalsi krabice.
- Navrhnete reseni tohoto problemu s pouzitim patternu Composite.
*/

//------------------------Main------------------------

public class Main {
    public static void main(String[] args) {
        // Box
        // Product
        Product phone = new Product("phone", 12000);
        Product charger = new Product("charger", 300);
        Shipping shippingHome = new Shipping("shipping to address", 59);

        Box box1 = new Box();
        box1.addProduct(phone);
        box1.addProduct(charger);
        box1.addProduct(shippingHome);

        Box box2 = new Box();
        Product hammer = new Product("hammer", 600);
        box2.addProduct(hammer);

        box1.addProduct(box2);

        //phone.calculatePrice();
        //charger.calculatePrice();
        box1.calculatePrice();
    }
}

//------------------------Component(Interface)------------------------

public interface Component {

    void calculatePrice();

}

//------------------------Box------------------------

public class Box implements Component {

    private List<Component> components = new ArrayList<>();

    private String name;


    @Override
    public void calculatePrice() {
        for (Component component : components) {
            component.calculatePrice();
        }
    }

    public void addProduct(Component component) {
        components.add(component);
    }
}

//------------------------Shipping------------------------

public class Shipping implements Component {

    public Shipping(String name, double price) {
        this.name = name;
        this.price = price;
    }

    private String name;

    private double price;

    @Override
    public void calculatePrice() {
        System.out.println("Item: " + name + ", " + "price: " + price);
    }

}

//------------------------Product------------------------

public class Product implements Component {

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    private String name;

    private double price;

    @Override
    public void calculatePrice() {
        System.out.println("Item: " + name + ", " + "price: " + price);
    }

}
