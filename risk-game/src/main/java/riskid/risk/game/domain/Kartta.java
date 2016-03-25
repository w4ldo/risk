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

//    public void tulostaViereisetAlueetJaJoukot() {
//        for (Manner m : this.mantereet.values()) {
//            for (Alue a : m.getAlueet().values()) {
//                System.out.println(a.getId() + ": " + a.getViereiset() + ", " + a.getYksikko());
//            }
//        }
//    }
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
