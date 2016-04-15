package riskid.risk.game;

import java.util.*;
import riskid.risk.game.domain.*;
import riskid.risk.game.sovelluslogiikka.*;

public class Main {

    public static void main(String[] args) {
        Pelaaja hessu = new Pelaaja("hessu");
        Pelaaja ossi = new Pelaaja("ossi");

        JoukkojenLiikuttaja kasi = new JoukkojenLiikuttaja();
        MapBuilder mapbuilder = new MapBuilder();
        Kartta map = mapbuilder.buildmap();
        map.getAlue(1).setYksikko(new Yksikko(ossi));
        map.getAlue(1).setHallitsija(ossi);
        map.getAlue(1).getYksikko().setVahvuus(10);
        
        map.getAlue(2).setYksikko(new Yksikko(hessu));
        map.getAlue(2).setHallitsija(hessu);
        map.getAlue(2).getYksikko().setVahvuus(9);


        kasi.liikutaYksikkoa(map.getAlue(1), map.getAlue(2), 9);
        
        System.out.println(map);
        map.tulostaViereisetAlueetJaJoukot();
    }

}
