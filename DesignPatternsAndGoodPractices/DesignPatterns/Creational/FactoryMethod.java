/*
- Návrhový vzor Factory Method slouží k vytvoření potenciálně složitého objektu na základě nějaké vlastnosti (například názvu nebo typu). 
- Vzor se zaměřuje na vytvoření nějakého objektu z určité rodiny objektů (tj. těch, které implementují stejné rozhraní nebo rozšiřují určitou základní třídu). 
- Navíc předpokládá existenci rozhraní nebo abstraktní třídy, která bude zodpovědná za vytváření takových objektů.

- Vzor Factory Method používáme tehdy, když chceme často vytvořit objekt z určité rodiny 
    (která je potenciálně komplikovaná, tj. skládá se z mnoha polí nebo se obtížně konstruuje). 
- Umožňuje nám také skrýt detaily struktury daného objektu, které jsou z pohledu uživatele irelevantní. 
- Tento vzor se řídí principem SOLID, ale může se potenciálně skládat z mnoha tříd.


-------------------------------------Priklad-------------------------------------

- V následujícím příkladu rodina objektů představuje rozhraní Game a jeho dvě implementace - PCGame a BoardGame. 
- GameFactory je společné rozhraní pro vytváření objektů typu Game. 
- Existují dvě implementace, které vytvářejí komplexní objekt pomocí jednoduchého volání metody:
*/

//------------------------Main------------------------

public class FactoryMethodUsage {

    public static void main(String[] args) {

        final String type = args[0];
        GameFactory gameFactory;
        
        if (type.equals("PC")) {
            gameFactory = new ValorantGameCreator();
        } else if (type.equals("Board")){
            gameFactory = new MonopolyGameCreator();
        } else {
            throw new RuntimeException("unknown game type");
        }

        Game createdGame = gameFactory.create();
        System.out.println("Created game " + createdGame);
    }
}

//------------------------Game(interface)------------------------

public interface Game {
	String getName();
	String getType();
	int getMinNumberOfPlayers();
	int getMaxNumberOfPlayers();
	boolean canBePlayedRemotely();
}

//------------------------BoardGame------------------------

public class BoardGame implements Game {

    private String name;
    private String type;
    private int maxPlayersNum;

    public BoardGame(final String name, final String type, final int maxPlayersNum) {
        this.name = name;
        this.type = type;
        this.maxPlayersNum = maxPlayersNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getMinNumberOfPlayers() {
        return 2;
    }

    @Override
    public int getMaxNumberOfPlayers() {
        return maxPlayersNum;
    }

    @Override
    public boolean canBePlayedRemotely() {
        return false;
    }

    @Override
    public String toString() {
        return "BoardGame{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", maxPlayersNum=" + maxPlayersNum +
        '}';
    }
}

//------------------------PCGame------------------------

public class PCGame implements Game {

    private final String name;
    private final String type;
    private final int minNumberOfPlayers;
    private final int maxNumberOfPlayers;
    private final boolean isOnline;

    public PCGame(final String name, final String type, final int minNumberOfPlayers, final int maxNumberOfPlayers, final boolean isOnline) {
        this.name = name;
        this.type = type;
        this.minNumberOfPlayers = minNumberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.isOnline = isOnline;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getMinNumberOfPlayers() {
        return minNumberOfPlayers;
    }

    @Override
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    @Override
    public boolean canBePlayedRemotely() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "PCGame{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", minNumberOfPlayers=" + minNumberOfPlayers +
            ", maxNumberOfPlayers=" + maxNumberOfPlayers +
            ", isOnline=" + isOnline +
        '}';
    }
}

//------------------------GameFactory(interface)------------------------

public interface GameFactory {
    Game create();
}

//------------------------MonopolyGameCreator------------------------

public class MonopolyGameCreator implements GameFactory {

    @Override
    public Game create() {
        return new BoardGame("Monopoly", "Family Game", 4);
    }
}

//------------------------ValorantGameCreator------------------------

public class ValorantGameCreator implements GameFactory {

    @Override
    public Game create() {
        return new PCGame("Valorant", "FPS", 4, 10, true);
    }
}