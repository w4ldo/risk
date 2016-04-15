package riskid.risk.game.domain;

public class Yksikko {

    private int vahvuus;
    private final Pelaaja pelaaja;

    public Yksikko(Pelaaja pelaaja) {
        this.vahvuus = 1;
        this.pelaaja = pelaaja;

    }

    public void setVahvuus(int vahvuus) {
        this.vahvuus = vahvuus;
    }

    public int getVahvuus() {
        return vahvuus;
    }

    public Pelaaja getPelaaja() {
        return pelaaja;
    }

    @Override
    public String toString() {
        return this.pelaaja + ": " + this.vahvuus;
    }

}
