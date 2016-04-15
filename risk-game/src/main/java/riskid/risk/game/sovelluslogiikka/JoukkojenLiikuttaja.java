package riskid.risk.game.sovelluslogiikka;

import riskid.risk.game.domain.*;

public class JoukkojenLiikuttaja {

    private Taistelusimulaattori ts;

    public JoukkojenLiikuttaja() {
        ts = new Taistelusimulaattori();
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
                    liikutaVihollisAlueelle(mista, mihin, montako);
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
