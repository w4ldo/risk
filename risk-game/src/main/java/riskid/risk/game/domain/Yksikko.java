
package riskid.risk.game.domain;

public class Yksikko {
    private int vahvuus;
    private final Pelaaja pelaaja;
    private Alue alue;
    
    public Yksikko(Pelaaja pelaaja, Alue alue) {
        this.vahvuus = 1;
        this.pelaaja = pelaaja;
        this.alue = alue;
        
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

    public Alue getAlue() {
        return alue;
    }

    @Override
    public String toString() {
        return this.pelaaja + ": " + this.vahvuus;
    }
    
    
    
}
