/*
Záměr tohoto vzoru 
- Procházet kolekci prvků bez odhalení vnitřní reprezentace 

Problém, který tento vzor řeší 
- Prvky můžeme ukládat do různých struktur - seznamů, front, stromů, grafů, ale logika procházení těchto struktur se liší 
- Vzor Iterátor nám pomáhá oddělit logiku procházení od dat uvnitř a kódu klienta

Kdy chceme tento vzor použít? 
- Pokud máme složitou strukturu, chceme ji procházet více způsoby a izolovat logiku od klientského kódu. 
- Snížit počet duplicitních kódů pro procházení složité struktury. 

----------------------------

Výhody 
- SRP => oddělení algoritmů procházení do vlastních tříd 
- OCP => nový způsob procházení kolekce znamená přidání nové třídy, žádné úpravy klientského kódu 
- Paralelní procházení - každý iterátor má svůj vlastní stav iterace 

Nevýhody 
- Ve skutečnosti není potřeba, pokud aplikace nemá složité kolekce, které potřebujeme procházet různými způsoby





-------------------------------------Priklad-------------------------------------

- Ukol: Iterator pattern je v Jave hodne rozsireny. 
- Demonstrujte jeho pouziti na tridach List a Set.
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();

        stringList.add("Red");
        stringList.add("Green");
        stringList.add("Blue");

        stringSet.add("Michal");
        stringSet.add("Jarek");
        stringSet.add("Eva");

        iterateCollection(stringList);
    }

    public static void iterateCollection(Collection<String> collection) {
        Iterator<String> collectionIterator = collection.iterator();

        while (collectionIterator.hasNext()) {
            String element = collectionIterator.next();
            System.out.println("element: " + element);
        }
    }

}