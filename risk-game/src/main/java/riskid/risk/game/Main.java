package riskid.risk.game;

import riskid.risk.game.sovelluslogiikka.*;
import java.util.Scanner;
import riskid.risk.game.kayttoliittyma.GUI;

public class Main {

    public static void main(String[] args) {

        Peli risk = new Peli();


        risk.run();
        
//        Scanner lukija = new Scanner("a");
//        LuvunKysyja lk = new LuvunKysyja(lukija);
//        System.out.println(lk.kysyLukua());
    }

}
