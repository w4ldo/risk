
package riskid.risk.game.sovelluslogiikka;

import java.util.ArrayList;
import java.util.List;
import riskid.risk.game.domain.*;

public class Mapbuilder {
    
    public Mapbuilder(){
    }
    
    public Kartta buildmap() {
        List<Alue> lista = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            lista.add(new Alue(i));
        }
        lista.get(0).setViereiset(lista.get(1), lista.get(2));
        lista.get(1).setViereiset(lista.get(2), lista.get(0));
        lista.get(2).setViereiset(lista.get(1), lista.get(0), lista.get(3));
        lista.get(3).setViereiset(lista.get(4), lista.get(2));
        lista.get(4).setViereiset(lista.get(3));

        Manner eka = new Manner(3, "eka", lista.get(0), lista.get(1), lista.get(2));
        Manner toka = new Manner(2, "toka", lista.get(3), lista.get(4));

        Kartta map = new Kartta(eka, toka);
        
        return map;
    }
    
}
