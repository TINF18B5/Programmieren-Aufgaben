package de.dhbwka.fnoeppedie;

public class EineFrage {
    
    private final String frageText;
    
    public EineFrage(String frageText) {
        this.frageText = frageText;
    }
    
    public void beantworteFrage(MeinFunktionalesInterface meinFunktionalesInterface) {
        final int antwort = meinFunktionalesInterface.zweiundvierzig(frageText);
        System.out.printf("%s: %d%n", frageText, antwort);
    }
}
