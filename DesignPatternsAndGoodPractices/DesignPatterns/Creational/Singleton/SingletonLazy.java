/*
- Lazy singleton musí být na rozdíl od eager singletonů vytvořen při prvním převzetí odkazu na takový singleton, např.:
*/

//------------------------CommonStorageSampleUsage(main)------------------------

public class CommonStorageSampleUsage {
    
    public static void main(String[] args) {

        CommonStorage commonStorageA = CommonStorage.getInstance(); // the instance is CREATED at this time
        CommonStorage commonStorageB = CommonStorage.getInstance(); // second access to previously created instance
        System.out.println(commonStorageA == commonStorageB); // true

        commonStorageA.addValue(1);
        commonStorageB.addValue(2);
        System.out.println(commonStorageA.getValues().size()); // list size is 2
    }
}

//------------------------CommonStorage------------------------

import java.util.ArrayList;
import java.util.List;

public class CommonStorage {

    private static CommonStorage instance;

    public static CommonStorage getInstance() {
        if (instance == null) { // (1)
            instance = new CommonStorage(); // (2)
        }
        return instance;
    }

    private List<Integer> values = new ArrayList<>();

    private CommonStorage() {
    }

    public void addValue(final int value) {
        values.add(value);
    }

    public List<Integer> getValues() {
        return values;
    }
}

/*
Ve výše uvedeném příkladu uvažujte řádky kódu označené jako (1) a (2). 
Předpokládejme, že dvě vlákna (říkejme jim vlákna A a B) současně zavolala metodu getInstance() na singletonu CommonStorage.

Je možná následující situace:
Vlákno A provede řádek (1), vstoupí do bloku if, ale ještě neprovede řádek kódu (2), tj. pole instance je stálenull.
vlákno B provede řádek (1), vstoupí do bloku if
vlákno A provede řádek (2), vytvoří první instanci singletonu
vlákno B provede řádek (2) a vytvoří druhou instanci singletonu, což je v rozporu s předpoklady tohoto návrhového vzoru.

Výše popsaný problém lze odstranit použitím tzv. dvojitě kontrolovaného singletonu, který používá [synchronizovaný blok. 
Kromě toho tento singleton dvakrát kontroluje, zda je pole instance rovno nule - poprvé bez synchronizovaného bloku, podruhé v synchronizovaném bloku. 
Účelem dvojí kontroly je eliminovat potřebu vytvářet nákladný synchronizovaný blok, když instance již byla inicializována. 

Definice takového singletonu může vypadat takto:
*/

//------------------------CommonStorage------------------------

import java.util.ArrayList;
import java.util.List;

public class CommonStorage {

    private static CommonStorage instance;

    public static CommonStorage getInstance() {
        if (instance == null) { // (1)
            synchronized (CommonStorage.class) {
                if (instance == null) { // (2)
                    instance = new CommonStorage(); 
                }
            }
        }
        return instance;
    }

    private List<Integer> values = new ArrayList<>();

    private CommonStorage() {
    }

    public void addValue(final int value) {
        values.add(value);
    }

    public List<Integer> getValues() {
        return values;
    }
}

/*
Ve výše uvedeném příkladu, pokud dvě vlákna současně volají metodu getInstance, zaručujeme, že reference bude inicializována přesně jednou.
*/