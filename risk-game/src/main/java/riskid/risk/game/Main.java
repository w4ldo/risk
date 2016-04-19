package riskid.risk.game;

import riskid.risk.game.sovelluslogiikka.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//        Scanner lukija = new Scanner(System.in);
//        Peli risk = new Peli(lukija);
//        risk.run();
        Scanner lukija = new Scanner("a");
        LuvunKysyja lk = new LuvunKysyja(lukija);
        System.out.println(lk.kysyLukua());
    }

}
