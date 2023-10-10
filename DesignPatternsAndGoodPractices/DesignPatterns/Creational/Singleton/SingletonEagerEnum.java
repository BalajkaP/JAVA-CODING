/*
- Pro tento účel by měl mít takový objekt k dispozici jednu hodnotu. 
- Hodnota objektu enum se inicializuje při spuštění JVM a můžeme si být jisti, že se tak stane přesně jednou. 
- Objekt enum může mít pouze soukromé konstruktory, takže jej nemusíme definovat. 
- Pro získání referenční hodnoty nemusíme vytvářet další getter, protože enum umožňuje přístup k hodnotě pomocí tečkové notace. 
- Nejjednodušší singleton založený na enumu tedy může vypadat takto:
*/

//------------------------SimpleCounterUsage(main)------------------------

public class SimpleCounterUsage {

    public static void main(String[] args) {
        SimpleCounter simpleCounterA = SimpleCounter.INSTANCE;
        SimpleCounter simpleCounterB = SimpleCounter.INSTANCE;
        System.out.println(simpleCounterA == simpleCounterB); // also true

        simpleCounterA.increment();
        simpleCounterB.increment();
        System.out.println(simpleCounterA.getCurrentCount()); // 2
    }
}

//------------------------SimpleSingletonExample(enum)------------------------

public enum SimpleSingletonExample {
    INSTANCE;
}

//------------------------SimpleCounter(enum)------------------------

public enum SimpleCounter {
    INSTANCE;

    private int currentCount = 0;

    public int getCurrentCount() {
        return currentCount;
    }

    public void increment() {
        currentCount++;
    }
}
