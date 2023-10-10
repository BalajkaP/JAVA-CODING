/*
Předpokládejme, že:
    - v terminálu, v bash shellu, provedeme příkazyls -ltr | grep -i sda 
    - v python konzoli provedeme příkaz 3 ** 3 + 1 
    - v jshellu konzoli provedeme příkaz Math.abs (Math.pow (2, 3)). 

- Každý z výše uvedených příkladů přijímá jako vstup nějakou informaci, obvykle ve formě řetězce. 
- Tyto znaky jsou pak určitým dohodnutým způsobem (tj. v závislosti na smlouvě a možnostech technologie) interpretovány a dávají konkrétní výsledek. 
- Toto chování popisuje použití vzoru interpretu.


Konstrukce K použití vzoru potřebujeme následující prvky:
    - společné rozhraní, které má metodu pro interpretaci určitého objektu. 
        Tento objekt se často nazývá kontextový objekt. 
    - implementace rozhraní, která mohou interpretovat kontext různými způsoby.



Vzor neobsahuje mnoho prvků a je stejně složitý jako výklad kontextu.



Příklad: 
- Příklad ukazuje jednoduchý interpret matematických operací. 
- Je založen na rozhraní Interpreter, které má dvě implementace:
    - PythonStyleWithoutOrderMathOperationsInterpreter - má schopnost provádět sčítání, odčítání, násobení, dělení (beze zbytku) a umocňování (ve stylu Pythonu, tj. že pro tento účel používá **) WordsWithoutOrderMathOperationsInterpreter - stejně jako výše uvedený provádí matematické operace, ale je založen na slovech ADD,SUBTRACT, MULTIPLY, DIVIDE a EXPONENTIATION. 
    
Třída InterpreterUsage ukazuje, jak tyto implementace používat:


-------------------------------------Priklad-------------------------------------

- Pracujeme ve firme, kde si mohou zamestnanci zadat o koupi pracovnich produktu. 
- Tyto produkty musi schvalit bud manazer, reditel nebo CEO podle ceny produktu.
- Ukol: Implementujte pomoci patternu chain of responsibility
*/

//------------------------InterpreterUsage(main)------------------------

public class InterpreterUsage {
    
    public static void main(String[] args) {
        final MathOperationApplier mathOperationApplier = new MathOperationApplier();
        Interpreter interpreter = new PythonStyleWithoutOrderMathOperationsInterpreter(mathOperationApplier);

        String result = interpreter.interpret(args[0]);
        System.out.println(result);

        interpreter = new WordsWithoutOrderMathOperationsInterpreter(mathOperationApplier);
        result = interpreter.interpret(args[1]);
        System.out.println(result);
    }
}

//------------------------Interpreter(interface)------------------------

public interface Interpreter {
    String interpret(String context);
}

//------------------------MathOperation(enum)------------------------

public enum MathOperation {
	ADD,
	SUBTRACT,
	MULTIPLY,	
	DIVIDE,
} 	

//------------------------PythonStyleWithoutOrderMathOperationsInterpreter------------------------

public class PythonStyleWithoutOrderMathOperationsInterpreter implements Interpreter {

    private final MathOperationApplier mathOperationApplier;

    private static final String INCORRECT_SYNTAX = "Expression is incorrect";

    public PythonStyleWithoutOrderMathOperationsInterpreter(final MathOperationApplier mathOperationApplier) {
        this.mathOperationApplier = mathOperationApplier;
    }

    @Override
    public String interpret(final String context) {
        final String[] splitData = context.trim().split(" ");

        if (splitData.length % 2 == 0) {
        return INCORRECT_SYNTAX;
        }

        Double value = Double.valueOf(splitData[0]);
        for (int idx = 1; idx < splitData.length - 1; idx += 2) {
            value = mathOperationApplier.apply(extractOperation(splitData[idx]), value, Double.valueOf(splitData[idx + 1]));
        }
        return value.toString();
    }

    private MathOperation extractOperation(final String operation) {
        switch (operation) {
            case "+":
                return MathOperation.ADD;
            case "-":
                return MathOperation.SUBTRACT;
            case "*":
                return MathOperation.MULTIPLY;
            case "/":
                return MathOperation.DIVIDE;
            case "**":
                return MathOperation.EXPONENTIATION;
            }
            throw new UnsupportedOperationException();
        }
    }

//------------------------WordsWithoutOrderMathOperationsInterpreter------------------------

public class WordsWithoutOrderMathOperationsInterpreter implements Interpreter {
    private final MathOperationApplier mathOperationApplier;

    private static final String INCORRECT_SYNTAX = "Expression is incorrect";

    public WordsWithoutOrderMathOperationsInterpreter(final MathOperationApplier mathOperationApplier) {
        this.mathOperationApplier = mathOperationApplier;
    }

    @Override
    public String interpret(final String context) {
        final String[] splitData = context.trim().split(" ");

        if (splitData.length % 2 == 0) {
            return INCORRECT_SYNTAX;
        }

        Double value = Double.valueOf(splitData[0]);
        for (int idx = 1; idx < splitData.length - 1; idx += 2) {
            value = mathOperationApplier.apply(extractOperation(splitData[idx]), value, Double.valueOf(splitData[idx + 1]));
        }
        return value.toString();
    }

    private MathOperation extractOperation(final String operation) {
        return MathOperation.valueOf(operation);
    }
}

