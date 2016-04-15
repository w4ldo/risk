package riskid.risk.game.domain;

import java.util.*;

public class Alue {

    private Pelaaja hallitsija;
    private final int id;
    private Map<Integer, Alue> viereiset;
    private Yksikko yksikko;

    public Alue(int id) {
        this.viereiset = new HashMap<>();
        this.id = id;
    }

    public void setHallitsija(Pelaaja hallitsija) {
        this.hallitsija = hallitsija;
    }

    public void setYksikko(Yksikko unit) {
        this.yksikko = unit;
    }

    public void setViereiset(Alue... alue) {
        for (Alue a : alue) {
            this.viereiset.put(a.getId(), a);
        }
    }

    public Yksikko getYksikko() {
        return yksikko;
    }

    public int getYksikonVahvuus() {
        return this.yksikko.getVahvuus();
    }

    public Pelaaja getHallitsija() {
        return hallitsija;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Alue> getViereiset() {
        return viereiset;
    }

    public boolean onkoViereinen(Alue alue) {
        if (this.getViereiset().containsKey(alue.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String a = "";
        return a + this.getId();
    }

}
