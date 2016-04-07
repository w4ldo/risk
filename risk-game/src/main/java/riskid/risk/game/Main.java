package riskid.risk.game;

import riskid.risk.game.domain.*;
import riskid.risk.game.sovelluslogiikka.*;

public class Main {

    public static void main(String[] args) {
        Pelaaja pelaaja = new Pelaaja("ossi");
        Mapbuilder mapbuilder = new Mapbuilder();
        Kartta map = mapbuilder.buildmap();
        map.getMantereet().get("P-Amerikka").getAlueet().get(1).setYksikko(new Yksikko(pelaaja));
        map.getMantereet().get("P-Amerikka").getAlueet().get(1).getYksikko().setVahvuus(6);

        System.out.println(map);
//        map.tulostaViereisetAlueetJaJoukot();

    }

}
