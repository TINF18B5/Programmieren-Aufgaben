package de.dhbwka.klausuren.jbay;

public class JBay {
    
    public static void main(String[] args) {
        final Auktionshaus jBay = new Auktionshaus();
        jBay.addAuktion(new Auktion(new Ware("Turnschuhe", "Tolle Turnschuhe, kaum getragen"), 2));
        jBay.addAuktion(new Auktion(new Ware("iPad", "Nagelneues iPad 3"), 4));
        jBay.addAuktion(new Auktion(new Ware("Currywurst", "Scharf, ohne Pommes"), 5));
        
        BieterTerminal b1 = new BieterTerminal(new Bieter("Micky", "Maus"), jBay);
        BieterTerminal b2 = new BieterTerminal(new Bieter("Donald", "Duck"), jBay);
        
        jBay.register(b1);
        jBay.register(b2);
    }
}
