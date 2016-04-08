package riskid.risk.game;

import riskid.risk.game.domain.*;
import riskid.risk.game.sovelluslogiikka.*;

public class Main {

    public static void main(String[] args) {
        Pelaaja pelaaja = new Pelaaja("ossi");
        MapBuilder mapbuilder = new MapBuilder();
        Kartta map = mapbuilder.buildmap();
        map.getAlue(1).setYksikko(new Yksikko(pelaaja));
        map.getAlue(1).getYksikko().setVahvuus(6);

        System.out.println(map);
        map.tulostaViereisetAlueetJaJoukot();

    }

}
