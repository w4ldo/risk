package riskid.risk.game.domain;

import java.util.*;

public class Manner {

    private final Map<Integer, Alue> alueet;
    private final String nimi;
    private final int bonus;

    public Manner(int bonus, String nimi, Alue... alue) {
        this.nimi = nimi;
        this.bonus = bonus;
        this.alueet = new HashMap<>();
        for (Alue a : alue) {
            this.alueet.put(a.getId(), a);
        }
    }

    public Map<Integer, Alue> getAlueet() {
        return alueet;
    }

    public Alue getAlue(int i) {
        if (this.alueet.containsKey(i)) {
            return this.alueet.get(i);
        }
        return null;
    }

    public String getNimi() {
        return nimi;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        String s = this.nimi + ": ";
        int i = 0;
        for (Alue a : this.alueet.values()) {
            s += a.toString();
            if (i < this.alueet.size() - 1) {
                s += ", ";
            }
            i++;
        }
        return s;
    }

}
