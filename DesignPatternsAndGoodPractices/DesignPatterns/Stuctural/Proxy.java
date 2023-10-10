/*
Záměr tohoto vzoru 
- Poskytneme náhradu za jiný objekt a řídíme přístup k němu. 
- Můžeme provést dodatečné chování před nebo po použití původního objektu 

Problém, který řeší 
- Provést něco před nebo po použití původního objektu, aniž by se změnila původní třída

Kdy chceme tento vzor použít? 
- Líná inicializace 
- Řízení přístupu 
- Vzdálený proxy server 
- Protokolování, ukládání do mezipaměti, průřezové problémy 

----------------------------

Výhody 
- Objekt můžeme ovládat beze změn v klientském kódu 
- Proxy funguje, i když původní objekt nefunguje 
- Lze použít více proxy serverů a klientovi je to jedno 

Nevýhody 
- Může zkomplikovat kód
- Může trochu zpomalit program

----------------------------

Příklad
- Řekněme, že máme knihovnu pro stahování videí YT
- Uživatelé žádají o stažení stejného videa několikrát denně
- To stojí hodně prostředků, šířky pásma a výkonu počítače
- Řešení: Vytvořte proxy server, který videa po určitou dobu ukládá do mezipaměti!





-------------------------------------Priklad-------------------------------------

- Chceme zpristupnit pristup k produktu pouze adminovi, ale nechceme si touto logikou spinit nasi produktovou sluzbu.
- Ukol: implementujte access proxy
*/

//------------------------Main------------------------

public class Main {

    public static void main(String[] args) {

        boolean isAdmin = true;

        ProductService productService = new ProductServiceImpl();

        ProductService productServiceProxy = new ProductServiceProxy(productService);

        productServiceProxy.getAllProducts(isAdmin);
    }

}

//------------------------ProductService------------------------

public interface ProductService {

    void getAllProducts(boolean isAdmin);

}

//------------------------ProductServiceImpl------------------------

public class ProductServiceImpl implements ProductService {
    @Override
    public void getAllProducts(boolean isAdmin) {
        // This is wrong, because this class suddenly has two responsibilities! - To check if user is admin
        // and to return all the products, which breaks SRP
//        if (isAdmin) {
//            System.out.println("Returning all the products!");
//        } else {
//            System.out.println("No access!");
//        }

        System.out.println("Returning all the products!");

    }
}

//------------------------ProductServiceProxy------------------------

public class ProductServiceProxy implements ProductService {

    private final ProductService realProductService;

    public ProductServiceProxy(ProductService realProductService) {
        this.realProductService = realProductService;
    }

    @Override
    public void getAllProducts(boolean isAdmin) {

        System.out.println("Doing something before the call on Product Service");

        if (isAdmin) {
            realProductService.getAllProducts(isAdmin);
        } else {
            System.out.println("No access!");
        }

        System.out.println("Doing something after the call on Product Service");

    }
}