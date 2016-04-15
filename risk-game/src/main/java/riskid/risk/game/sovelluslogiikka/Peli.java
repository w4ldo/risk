
package riskid.risk.game.sovelluslogiikka;

import java.util.Scanner;
import riskid.risk.game.domain.*;

public class Peli {
    private MapBuilder mb;
    private JoukkojenLiikuttaja jl;
    private Scanner lukija;
    private Kartta map;
    
    
    public Peli() {
        this.mb = new MapBuilder();
        this.jl = new JoukkojenLiikuttaja();
        this.lukija = new Scanner(System.in);
        this.map = mb.buildmap();
    }
    
    public void run() {
        
        pelinAlustus();
        
        
    }
    
    private void pelinAlustus() {
        Pelaaja sininen = new Pelaaja("sininen");
        Pelaaja keltainen = new Pelaaja("keltainen");
        sininen.lisaaReserviin(55);
        keltainen.lisaaReserviin(55);
        boolean sinisenVuoro = true;
        aloitusJoukkojenSijoitus(sinisenVuoro, sininen, keltainen);
        lisajoukkojenSijoitus(sinisenVuoro, sininen, keltainen);
        
    }
    
    private void aloitusJoukkojenSijoitus(boolean sinisenVuoro, Pelaaja sininen, Pelaaja keltainen) {
        while (map.onkoTyhjiaAlueita()) {
            if (sinisenVuoro) {
                if (jl.sijoitaAloitusJoukkoja(map.getAlue(Integer.parseInt(lukija.nextLine())), sininen)) {
                    sinisenVuoro = !sinisenVuoro;
                } else {
                    continue;
                }
            } else {
                if (jl.sijoitaAloitusJoukkoja(map.getAlue(Integer.parseInt(lukija.nextLine())), keltainen)) {
                    sinisenVuoro = !sinisenVuoro;
                } else {
                    continue;
                }
            }
        }
    }

    private void lisajoukkojenSijoitus(boolean sinisenVuoro, Pelaaja sininen, Pelaaja keltainen) {
        while (sininen.getReservi() > 0 && keltainen.getReservi() > 0) {
            if (sinisenVuoro) {
                if (jl.sijoitaLisajoukkoja(map.getAlue(Integer.parseInt(lukija.nextLine())), 2, sininen)) {
                    sinisenVuoro = !sinisenVuoro;
                } else {
                    continue;
                }
            } else {
                if (jl.sijoitaLisajoukkoja(map.getAlue(Integer.parseInt(lukija.nextLine())), 2, keltainen)) {
                    sinisenVuoro = !sinisenVuoro;
                } else {
                    continue;
                }
            }
        }
    }
    
}
