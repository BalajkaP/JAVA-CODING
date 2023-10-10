/*
Záměr tohoto vzoru 
- Předávat požadavky v řetězci zpracovatelů 
- Každý z těchto zpracovatelů může zpracovat požadavek a rozhodnout, zda má požadavek pokračovat dále v řetězci. 

Problém, který tento vzor řeší 
- Rozsáhlé třídy s více chováními => opakovaně použitelné obslužné programy 

Jak to funguje? 
- Izolujte každé chování do samostatné třídy obsluhy 
- Propojte tyto obsluhy dohromady

Kdy chceme tento vzor použít? 
- Když program potřebuje provést různé typy požadavků různými způsoby, ale pořadí operací není známo. 
- Pokud potřebujeme použít několik obslužných programů 

----------------------------

Výhody 
- Můžeme kontrolovat pořadí obslužných rutin použitých na požadavek 
- SRP => Obsluhy žijí v oddělených třídách 
- OCP => Přidání nového handleru znamená vytvoření nové třídy, nemusíme upravovat kód klienta 

Nevýhody 
- Můžeme zpracovávat pouze požadavky, pro které máme zpracovatele

----------------------------

Příklad
- Řekněme, že zavádíme systém pro schvalování nákupu pracovních věcí pro zaměstnance.
- Víme, že pro malé nákupy stačí schválení vedoucím pracovníkem.
- U středně velkých nákupů bychom chtěli, aby je schvaloval ředitel
- U velkých nákupů to musí podepsat generální ředitel
- Jaké je řešení tohoto vzorce? Řetězec odpovědnosti!





-------------------------------------Priklad-------------------------------------

- Pracujeme ve firme, kde si mohou zamestnanci zadat o koupi pracovnich produktu. 
- Tyto produkty musi schvalit bud manazer, reditel nebo CEO podle ceny produktu.
- Ukol: Implementujte pomoci patternu chain of responsibility
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {
        Approver manager = new Manager();
        Approver director = new Director();
        Approver ceo = new CEO();

        PurchaseRequest request = new PurchaseRequest("Sluzebni auto Tesla model S", 3000000);

        manager.setNext(director);
        director.setNext(ceo);

        boolean directorOutOfOffice = true;

        if (directorOutOfOffice) {
            // poskladat retez uplne jinym zpusobem
        }

        manager.approve(request);
    }

}

//------------------------Approver(interface)------------------------

public interface Approver {

    void approve(PurchaseRequest purchaseRequest);

    void setNext(Approver approver);

}

//------------------------PurchaseRequest(record)------------------------

public record PurchaseRequest(String description, long price) {
}

//------------------------Manager------------------------

public class Manager implements Approver {

    private Approver nextApprover;

    @Override
    public void approve(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.price() <= 2000) {
            System.out.println("Request has been approved by manager!");
        } else {
            nextApprover.approve(purchaseRequest);
        }
    }

    @Override
    public void setNext(Approver approver) {
        if (approver != null) {
            nextApprover = approver;
        }
    }
}

//------------------------Director------------------------

public class Director implements Approver {

    private Approver nextApprover;

    @Override
    public void approve(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.price() <= 50000) {
            System.out.println("Request approved by director!");
        } else {
            nextApprover.approve(purchaseRequest);
        }
    }

    @Override
    public void setNext(Approver approver) {
        if (approver != null) {
            nextApprover = approver;
        }
    }
}

//------------------------CEO------------------------

public class CEO implements Approver {

    @Override
    public void approve(PurchaseRequest purchaseRequest) {
        if (purchaseRequest.price() <= 2000000) {
            System.out.println("Request approved by CEO!");
        } else {
            System.out.println("The request is automatically declined.");
        }
    }

    @Override
    public void setNext(Approver approver) {

    }
}
