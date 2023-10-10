/*
- Implementace builderů jsou pro každou třídu velmi podobné. 
- Tvůrci knihovny lombok chtěli odstranit nutnost psát kód builderu, lze jej vygenerovat pomocí anotace @Builder. 
- Následující příklad využívá i další anotace z této knihovny:
    - @Data - generuje gettery a settery pro všechna pole spolu s metodami toString, equals a hashCode.
    - @NoArgsConstructor - generuje konstruktor bez argumentů
    - @AllArgsConstructor - generuje konstruktor pro všechna pole třídy

- Nezapomeňte, že abychom se dostali k Builder vygenerovanému systémem lombok, musíme zavolat statickou metodu builder.
*/

//------------------------LombokBuilderUsage(main)------------------------

public class LombokBuilderUsage {

    public static void main(String[] args) {

        GraphicsCard graphicsCard = GraphicsCard.builder()
            .memoryInMb(2048)
            .modelName("GF1660")
            .producer("Asus")
            .series("1xxx")
            .build();
    }
}

//------------------------GraphicsCard------------------------

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraphicsCard {
    private int memoryInMb;
    private String producer;
    private String series;
    private String modelName;
}