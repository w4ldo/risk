package riskid.risk.game.sovelluslogiikka;

import riskid.risk.game.domain.*;

public class JoukkojenLiikuttaja {

    public JoukkojenLiikuttaja() {

    }

    public void liikutaYksikkoa(Alue mista, Alue mihin, int montako) {
        if (mista.onkoViereinen(mihin)) {
            if (montako >= 1 && montako < mista.getYksikko().getVahvuus()) {
                if (mihin.getYksikko() == null) {
                    //jos siirto on laillinen ja ruutu tyhjä, siirretään
                    //haluttu määrä sotilaita ruutuun
                    liikutaNeutraalilleAlueelle(mista, mihin, montako);
                } else if (!mista.getHallitsija().equals(mihin.getHallitsija())) {
                    //taistellaaaaaaaaaaaaaaaan :DDD
                } else {
                    //passiivinen liikeasdasdasdasd
                    liikutaOmalleAlueelle(mista, mihin, montako);
                }
            } else {
                System.out.println("Laiton siirto, liikuta vähintään yhtä ja jätä vähintään yksi taakse");
            }
        } else {
            System.out.println("Laitoin siirto, alueet eivä ole vierekkäin");
        }
    }

    public void liikutaOmalleAlueelle(Alue mista, Alue mihin, int montako) {
        mista.getYksikko().setVahvuus(mista.getYksikonVahvuus() - montako);
        mihin.getYksikko().setVahvuus(mihin.getYksikonVahvuus() + montako);
    }

    public void liikutaVihollisAlueelle(Alue mista, Alue mihin, int montako) {

    }

    public void liikutaNeutraalilleAlueelle(Alue mista, Alue mihin, int montako) {
        mista.getYksikko().setVahvuus(mista.getYksikonVahvuus() - montako);
        mihin.setYksikko(new Yksikko(mista.getHallitsija()));
        mihin.getYksikko().setVahvuus(montako);
        mihin.setHallitsija(mista.getHallitsija());
    }

}
