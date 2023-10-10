/*
Záměr vzoru 
- Pokud máme dva objekty s nekompatibilními rozhraními, můžeme použít vzor Adapter, aby spolu fungovaly. 

Problém, který řeší 
- Nekompatibilní objekty na úrovni rozhraní 
- Příklad: Jeden objekt očekává soubor JPG, ale my máme k dispozici pouze soubor PNG => vytvořit adaptér, 
    který tento soubor JPG převede na PNG a pošle jej požadovanému objektu 

Kdy jej použijeme 
- Chceme použít existující třídu, ale její rozhraní není kompatibilní s naší kódovou základnou 

----------------------------

Výhody  
- SRP => oddělit logiku převodu do vlastní třídy 
- OCP => pokud chceme podporovat jiné rozhraní, jednoduše vytvoříme nový adaptér.. Není třeba měnit stávající třídy 

Nevýhody 
- Někdy může být jednodušší prostě změnit kód klienta tak, aby podporoval nekompatibilní třídu 




-------------------------------------Priklad-------------------------------------

- Mame software na nahravani hudby a chteli bychom umet tuto hudbu analyzovat. 
- Nasli jsme knihovnu, ktera umi analyzovat hudbu, ale prijima zvukove stopy ve formatu MP3, zatimco my nahravame hudbu do formatu WAV.
- Navrhnete reseni tohoto problemu pomoci Adapter patternu.
*/

//------------------------Main------------------------

public class Main {
    public static void main(String[] args) {

        WavRecording wavRecording = new WavRecording(2000);

        // Zde jsme klasicky pouzivali wav analyzer, tak jak jsme zvykli
        //WavAnalyzer wavAnalyzer = new WavAnalyzerImpl();

        // Zde jsme beze zmeny klienta pouzili uplne jiny zpusob analyzy tech nasich nahravek
        WavAnalyzer wavAnalyzer = new WavToMp3Adapter();

        int trackLength = wavAnalyzer.analyzeWavRecording(wavRecording);

        System.out.println("Delka tracku: " + trackLength);

    }
}

//------------------------Mp3Analyzer(interface)------------------------

public interface Mp3Analyzer {

    int analyzeMp3Recording(Mp3Recording recording);

}

//------------------------WavAnalyzer(interface)------------------------

public interface WavAnalyzer {

    int analyzeWavRecording(WavRecording recording);

}

//------------------------Mp3Recording(record)------------------------

public record Mp3Recording(int length) {

}

//------------------------WavRecording(record)------------------------

public record WavRecording(int length) {


}

//------------------------Mp3AnalyzerImpl------------------------

public class Mp3AnalyzerImpl implements Mp3Analyzer {
    @Override
    public int analyzeMp3Recording(Mp3Recording recording) {
        System.out.println("Analyzujeme nove MP3 soubory..");
        return recording.length();
    }
}

//------------------------WavAnalyzerImpl------------------------

public class WavAnalyzerImpl implements WavAnalyzer {


    @Override
    public int analyzeWavRecording(WavRecording recording) {
        System.out.println("Analyzujeme wavka tak jako obvykle..");
        return recording.length();
    }
}

//------------------------WavToMp3Adapter------------------------

public class WavToMp3Adapter implements WavAnalyzer {


    Mp3Analyzer mp3Analyzer = new Mp3AnalyzerImpl();

    @Override
    public int analyzeWavRecording(WavRecording recording) {

        Mp3Recording mp3Recording = wavToMp3(recording);

        return mp3Analyzer.analyzeMp3Recording(mp3Recording);
    }

    private Mp3Recording wavToMp3(WavRecording wavRecording) {

        // adaptive logic

        return new Mp3Recording(wavRecording.length());
    }

}