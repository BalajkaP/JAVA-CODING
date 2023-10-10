/*
- Všimněte si, že třída ToyBuilder používá konstruktor třídy Toy, který má přístup k balíčku private (výchozí). 
- Třída ToyBuilder je ve stejném balíčku jako třída Toy. 
- Pokud jsou tyto třídy v různých balíčcích, musí být konstruktor třídy Toy public.
*/

//------------------------ToyBuilderUsage(main)------------------------

public class ToyBuilderUsage {

    public static void main(String[] args) {
        
        final Toy toy = new ToyBuilder()
            .withMadeOf("plastic")
            .withName("Matchbox car")
            .withType("Small car")
            .build();
    }
}

//------------------------Toy------------------------

public class Toy {

    private String name;
    private String type;
    private String madeOf;

    Toy(final String name, final String type, final String madeOf) {
        this.name = name;
        this.type = type;
        this.madeOf = madeOf;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getMadeOf() {
        return madeOf;
    }

    public void setMadeOf(final String madeOf) {
        this.madeOf = madeOf;
    }
}

//------------------------ToyBuilder------------------------

public class ToyBuilder {

    private String name;
    private String type;
    private String madeOf;

    public ToyBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public ToyBuilder withType(final String type) {
        this.type = type;
        return this;
    }

    public ToyBuilder withMadeOf(final String madeOf) {
        this.madeOf = madeOf;
        return this;
    }

    public Toy build() {
        return new Toy(name, type, madeOf);
    }
}
