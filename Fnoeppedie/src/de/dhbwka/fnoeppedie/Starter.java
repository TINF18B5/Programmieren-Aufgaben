package de.dhbwka.fnoeppedie;

import java.util.stream.*;

public class Starter {
    
    public static void main(String[] args) {
    
        final MeinFunktionalesInterface meinFunktionalesInterface = msg -> 42;
    

        Stream.of("A", "B", "C")
                .map(meinFunktionalesInterface::zweiundvierzig)
                .forEach(System.out::println);
    
    
        //for (EineFrage frage : new EineFrage[]{new EineFrage("Was ist der Sinn des Lebens?"), new EineFrage("Wie groß ist Andreas' Hand?")}) {
        //    frage.beantworteFrage(meinFunktionalesInterface);
        //}
    }
    
    
}
