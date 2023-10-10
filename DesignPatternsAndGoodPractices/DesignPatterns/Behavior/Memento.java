/*
Záměr tohoto vzoru 
- Uložit konkrétní stav objektu v čase a později jej obnovit, aniž by bylo nutné zasahovat do vnitřní reprezentace objektu. 
- Tento vnitřní stav žije v objektu zvaném Memento a přístup k němu mají pouze objekty, které tato Mementa vytvářejí 

Problém, který tento vzor řeší 
- Někdy nelze zkopírovat kompletní stav objektu kvůli zapouzdření 
- I kdyby to šlo, kód pro kopírování stavu objektu by byl úzce spjat se samotným objektem ... museli bychom tyto změny promítnout do objektu

Kdy chceme tento vzor použít? 
- Pokud potřebujeme uložit stav objektu a později jej obnovit. 

----------------------------

Důležité věci: 
- Originátor je třída, kterou chceme uložit a která zpracovává proces. 
- Memento by mělo být neměnné 
- Správce uchovává historii a ví, kdy má uložit a obnovit stav

----------------------------

Výhody 
- Můžeme zkopírovat stav objektu, aniž bychom porušili zapouzdření (nemáme přímý přístup ke stavu) 
- Zjednodušený kód původce díky tomu, že správce ošetřuje, kdy tyto paměti provést 
 
Nevýhody 
- Problémy s pamětí, pokud ukládáme příliš mnoho mement.





-------------------------------------Priklad-------------------------------------

- Příklad ukazuje, jak uložit určitou hru. 
- Tento stav je uložen ve třídě GameState, zatímco třída GameStateSnapshot představuje jeho uložení. 
- Instance třídy GameStateSnapshot se vytvoří vytvořením kopie polí třídy GameState. 
- Existuje také možnost obnovit stav třídy GameState na základě hodnoty v instanci GameStateSnapshot 
    (tj. mezi objekty GameState a GameStateSnapshot je možné vytvořit hloubkovou kopii v obou směrech).

- Objektem spravujícím uložené stavy je třída GameStateManager, která umožňuje ukládat a načítat hru pouze z předchozího stavu. 
- K tomuto účelu byl použit zásobník. 
- Jednou z vestavěných kolekcí, které lze použít, je třída ArrayDeque, která implementuje rozhraní Deque:
*/

//------------------------GameState------------------------

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    private Integer health;
    private Integer mana;
    private List<String> items;

    @Override
    public String toString() {
        return "GameState{" +
            "health=" + health +
            ", mana=" + mana +
            ", items=" + items +
        "}\n";
    }

    public void heal() {
        health = 100;
    }

    public void takeDamage(final int damage) {
        health -= damage;
    }

    public void addItem(final String item) {
        items.add(item);
    }

    public void loseAllItems() {
        items.clear();
    }

    public void restoreFromSnapshot(final GameStateSnapshot snapshot) {
        health = snapshot.getHealth();
        mana = snapshot.getMana();
        items = List.copyOf(snapshot.getItems());
    }
}

//------------------------GameStateSnapshot------------------------

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateSnapshot {

    private Integer health;
    private Integer mana;
    private List<String> items;

    public GameStateSnapshot(final GameState gameState) {
        this.health = gameState.getHealth();
        this.mana = gameState.getMana();
        this.items = List.copyOf(gameState.getItems()); // deep copy!
    }
}

//------------------------GameStateManager------------------------

public class GameStateManager {

    private final Deque<GameStateSnapshot> snapshots = new ArrayDeque<>();

    public void saveGame(final GameState gameState) {
        snapshots.push(new GameStateSnapshot(gameState));
    }

    public GameStateSnapshot restorePreviousCheckpoint() {
        return snapshots.pop();
    }
}

//------------------------MementoUsage(main)------------------------

import java.util.ArrayList;

public class MementoUsage {

    public static void main(String[] args) {

        final GameState gameState = new GameState(100, 80, new ArrayList<>());

        final GameStateManager gameStateManager = new GameStateManager();
        gameStateManager.saveGame(gameState);
        System.out.println(gameState);

        gameState.addItem("Basic Sword");
        gameState.takeDamage(10);
        System.out.println(gameState);

        gameStateManager.saveGame(gameState);

        gameState.takeDamage(50);
        gameState.addItem("Shield");
        System.out.println(gameState);

        gameStateManager.saveGame(gameState);

        // decided to load previous save twice

        gameStateManager.restorePreviousCheckpoint();
        final GameStateSnapshot gameStateSnapshot = gameStateManager.restorePreviousCheckpoint();
        gameState.restoreFromSnapshot(gameStateSnapshot);
        System.out.println(gameState);
    }
}

/* OUTPUT:
GameState{health=100, mana=80, items=[]}

GameState{health=90, mana=80, items=[Basic Sword]}

GameState{health=40, mana=80, items=[Basic Sword, Shield]}

GameState{health=90, mana=80, items=[Basic Sword]}
*/