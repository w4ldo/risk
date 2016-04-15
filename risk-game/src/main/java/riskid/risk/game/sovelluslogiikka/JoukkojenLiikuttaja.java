package riskid.risk.game.sovelluslogiikka;

import riskid.risk.game.domain.*;

public class JoukkojenLiikuttaja {

    private Taistelusimulaattori ts;

    public JoukkojenLiikuttaja() {
        ts = new Taistelusimulaattori();
    }

    public boolean sijoitaAloitusJoukkoja(Alue minne, Pelaaja kenen) {
        if (minne.getYksikko() == null) {
            minne.setYksikko(new Yksikko(kenen));
            minne.setHallitsija(kenen);
            kenen.poistaReservista(1);
            return true;
        } else {
            System.out.println("Laiton siirto: sijoita joukkoja tyhjään ruutuun");
        }

        return false;
    }

    public boolean sijoitaLisajoukkoja(Alue minne, int montako, Pelaaja kenen) {
        if (minne.getHallitsija().equals(kenen)) {
            minne.getYksikko().setVahvuus(minne.getYksikonVahvuus() + montako);
            kenen.poistaReservista(montako);
            return true;
        } else {
            System.out.println("Laiton siirto: lisää joukkoja omille alueillesi");
        }
        return false;
    }

    public void hyokkaaTaiValtaa(Alue mista, Alue mihin, int montako) {
        if (mista.onkoViereinen(mihin)) {
            if (montako >= 1 && montako < mista.getYksikko().getVahvuus()) {
                if (mihin.getYksikko() == null) {
                    //jos siirto on laillinen ja ruutu tyhjä, siirretään
                    //haluttu määrä sotilaita ruutuun
                    liikutaNeutraalilleAlueelle(mista, mihin, montako);
                } else if (!mista.getHallitsija().equals(mihin.getHallitsija())) {
                    //taistellaaaaaaaaaaaaaaaan :DDD
                    liikutaVihollisAlueelle(mista, mihin, montako);
                } else {
                    //passiivinen liikeasdasdasdasd
                    liikutaOmalleAlueelle(mista, mihin, montako);
                }
            } else {
                System.out.println("Laiton siirto: liikuta vähintään yhtä ja jätä vähintään yksi taakse");
            }
        } else {
            System.out.println("Laitoin siirto: alueet eivä ole vierekkäin");
        }
    }

    public boolean passiivinenLiike(Alue mista, Alue mihin, int montako) {
        if (mista.onkoViereinen(mihin)) {
            if (montako >= 1 && montako < mista.getYksikko().getVahvuus()) {
                if (mista.getHallitsija().equals(mihin.getHallitsija())) {
                    //passiivinen liike
                    liikutaOmalleAlueelle(mista, mihin, montako);
                    return true;
                } else {
                    System.out.println("Laiton siirto: siirrä omalle alueelle");
                }
            } else {
                System.out.println("Laiton siirto: liikuta vähintään yhtä ja jätä vähintään yksi taakse");
            }
        } else {
            System.out.println("Laitoin siirto: alueet eivä ole vierekkäin");
        }
        return false;
    }

    private void liikutaOmalleAlueelle(Alue mista, Alue mihin, int montako) {
        mista.getYksikko().setVahvuus(mista.getYksikonVahvuus() - montako);
        mihin.getYksikko().setVahvuus(mihin.getYksikonVahvuus() + montako);
    }

    private void liikutaVihollisAlueelle(Alue mista, Alue mihin, int montako) {
        ts.taistele(mista, mihin, montako);
    }

    private void liikutaNeutraalilleAlueelle(Alue mista, Alue mihin, int montako) {
        mista.getYksikko().setVahvuus(mista.getYksikonVahvuus() - montako);
        mihin.setYksikko(new Yksikko(mista.getHallitsija()));
        mihin.getYksikko().setVahvuus(montako);
        mihin.setHallitsija(mista.getHallitsija());
    }

}
