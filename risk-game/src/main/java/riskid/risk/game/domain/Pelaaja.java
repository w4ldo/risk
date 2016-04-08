package riskid.risk.game.domain;

import java.util.*;

public class Pelaaja {

    private String nimi;
    private List<Yksikko> joukot;
    private int reservi;

    public Pelaaja(String nimi) {
        this.nimi = nimi;
        this.reservi = 0;
    }

    public String getNimi() {
        return nimi;
    }
    
    public void lisaaReserviin(int maara) {
//        reservillä tarkoitetaan pelaajan laudalle sijoittamattomien joukkujen määrää
//        i.e joukot jotka saadaan pelin alussa ja vuorojen alkaessa
        this.reservi += maara;
    }

    public int getReservi() {
        return reservi;
    }

    @Override
    public String toString() {
        return this.getNimi();
    }
    
    
}
