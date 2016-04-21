package riskid.risk.game.domain;

import java.util.*;

public class Kartta {

    private final Map<String, Manner> mantereet;

    public Kartta(Manner... manner) {
        this.mantereet = new HashMap<>();
        for (Manner m : manner) {
            this.mantereet.put(m.getNimi(), m);
        }
    }

    public Map<String, Manner> getMantereet() {
        return mantereet;
    }

    public Alue getAlue(int i) {
        if (i >= 1 && i <= 9) {
            return this.mantereet.get("P-Amerikka").getAlue(i);
        } else if (i >= 10 && i <= 13) {
            return this.mantereet.get("E-Amerikka").getAlue(i);
        } else if (i >= 14 && i <= 20) {
            return this.mantereet.get("Eurooppa").getAlue(i);
        } else if (i >= 21 && i <= 26) {
            return this.mantereet.get("Afrikka").getAlue(i);
        } else if (i >= 27 && i <= 38) {
            return this.mantereet.get("Aasia").getAlue(i);
        } else if (i >= 39 && i <= 42) {
            return this.mantereet.get("Australia").getAlue(i);
        } else {
            return null;
        }
    }

    public boolean onkoTyhjiaAlueita() {
        int i = 0;
        for (Manner m : this.mantereet.values()) {
            for (Alue a : m.getAlueet().values()) {
                i++;
            }
        }
        for (int a = 1; a <= i; a++) {
            if (this.getAlue(a).getYksikko() == null) {
                return true;
            }
        }
        return false;
    }

    public int laskePelaajanLisajoukot(Pelaaja pelaaja) {
        int joukot = 0;
        joukot += laskePelaajanAlueet(pelaaja) / 3;
        for (Manner m : this.getMantereet().values()) {
            if (m.onkoBonus(pelaaja)) {
                joukot += m.getBonus();
            }
        }
        joukot = joukot / 3;
        joukot = joukot * 3;
        return joukot;
    }

    public boolean voittaako(Pelaaja pelaaja) {
        for (Manner m : this.mantereet.values()) {
            if (!m.onkoBonus(pelaaja)) {
                return false;
            }
        }
        return true;
    }

    private int laskePelaajanAlueet(Pelaaja pelaaja) {
        int i = 0;
        for (Manner m : this.mantereet.values()) {
            for (Alue a : m.getAlueet().values()) {
                if (a.getHallitsija() == pelaaja) {
                    i++;
                }
            }
        }
        return i;
    }
    
    public List<String> tulostaKartta() {
        List<String> lista = new ArrayList<>();
        for (int i = 1; i <= 42; i++) {
            lista.add(getAlue(i).toString());
        }
        return lista;
    }

    public void tulostaViereisetAlueetJaJoukot() {
        //lähinnä testausta varten
        for (Manner m : this.mantereet.values()) {
            for (Alue a : m.getAlueet().values()) {
                System.out.println(a.getId() + ": " + a.getViereiset() + ", " + a.getYksikko());
            }
        }
    }

    @Override
    public String toString() {
        String a = "";
        int i = 0;
        for (Manner m : this.mantereet.values()) {
            a += m.toString();
            if (i < this.mantereet.size() - 1) {
                a += "\n";
            }
            i++;
        }
        return a;
    }

}
