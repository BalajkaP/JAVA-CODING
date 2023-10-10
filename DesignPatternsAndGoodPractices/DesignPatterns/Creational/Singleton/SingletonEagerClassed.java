/*
- Abychom mohli vytvořit eager singleton založený na třídě, musíme si zapamatovat všechny konstrukční body. 
- Následující příklad ukazuje, jak takový singleton definovat a jak jej následně použít.
*/

//------------------------SimpleCounterUsage(main)------------------------

public class SimpleCounterUsage {

    public static void main(String[] args) {

        SimpleCounter simpleCounterA = SimpleCounter.getInstance();
        SimpleCounter simpleCounterB = SimpleCounter.getInstance();
        System.out.println(simpleCounterA == simpleCounterB); // true -> both references point to the same object

        simpleCounterA.increment();
        simpleCounterB.increment();
        System.out.println(simpleCounterA.getCurrentCount()); // 2
    }
}

//------------------------SimpleCounter------------------------

public class SimpleCounter {

    // static field in which we keep the singleton reference
    // it is an eager singleton so we create an instance by assigning it to the field
    private static final SimpleCounter INSTANCE = new SimpleCounter();

    // getter for singleton reference
    public static SimpleCounter getInstance() {
        return INSTANCE;
    }

    // hidden constructor
    private SimpleCounter() {}

    private int currentCount = 0;

    public int getCurrentCount() {
        return currentCount;
    }

    public void increment() {
        currentCount++;
    }
}