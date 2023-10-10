/*
Záměr tohoto vzoru 
- Zajistit, aby třída měla pouze jednu instanci, a poskytnout k ní přístup. 

Problém, který řeší 
- Některé objekty jsou v aplikaci potřeba na více místech 
- Některé objekty by měly mít pouze jednu instanci (například DB konektory, cache, služby, pomocné třídy atd.). 

Kontroverze
- Vzor Singleton je mnohými považován za anti-vzor 
- Vývojáři měli tendenci vzor Singleton používat nadměrně, což vedlo ke vzniku mnoha tříd, které se obtížně ladily, testovaly a rozšiřovaly. 
- Porušuje SRP (má mnoho odpovědností) 
- Může sloužit jako náplast na špatný návrh 
- Vyžaduje speciální implementaci ve vícevláknových aplikacích 
- Mocking se stává obtížným

----------------------------

Typy:
Eager: inicializace objektu rovnou pri startu aplikace (vhodné pro vícevláknové aplikace)
Lazy: inicializace objektu az pri pozadavku

Způsoby, jak vytvořit Eager singleton:
- eager s použitím třídy
- eager s použitím enumu.

Způsoby, jak vytvořit Lazy singleton:
- lazy, který není vhodný pro použití ve vícevláknových aplikacích.
- lazy double checked, který lze použít ve vícevláknových aplikacích.


-------------------------------------Priklad-------------------------------------

- Ukol 1: Definuj tridu Database, ktera bude singletonem typu lazy a bude mit metody:
    - connect(), ktera vypise do konzole "Successfully connected to DB!"
    - close(), ktera vypise do konzole "Connection was closed."
- Ukol 2: Zmen implementaci Singletonu na variantu eager.
- Ukol 3: Udelej tento singleton thread-safe.
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        Database database = Database.getInstance();

        database.connect();
        database.close();

        Database database2 = Database.getInstance();
    }

}

//------------------------Database------------------------

public class Database {

    // Eager: inicializace objektu rovnou (pri startu aplikace)
    // Lazy: inicializace objektu az pri pozadavku

    private static Database instance;

    private Database() {

    // Ukol 2
    // private static Database instance = new Database();

    }

    // Ukol 1
    public static Database getInstance() {
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        }
    }
    
    /* Ukol 2
    public static Database getInstance() {
            return instance;
        }
    }

   // Ukol 3
    public static Database getInstance() {
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }
            return instance;
        }
    }
    */

    void connect() {
        System.out.println("Successfully connected to DB!");
    }

    void close() {
        System.out.println("Connection was closed.");
    }
}