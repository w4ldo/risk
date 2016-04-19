package riskid.risk.game.sovelluslogiikka;

import java.util.Scanner;

public class LuvunKysyja {

    private Scanner lukija;

    public LuvunKysyja(Scanner lukija) {
        this.lukija = lukija;
    }

    public int kysyLukua() {
        int luku = 0;
        try {
            luku = Integer.parseInt(lukija.nextLine());
        } catch (Exception e) {
            System.out.println("Ei kirjaimii senki hessu vaan numeroita.");
        }
        return luku;
    }

    public boolean onkoLukuSallittu(int luku) {
        if (luku > 0 && luku < 43) {
            return true;
        } else if (luku == 999) {
            return false;
        }
        System.out.println("Valitse alue 1-42");
        return false;
    }

    public int monellakoHyokataan() {
        int monellako = 0;
        try {
            monellako = Integer.parseInt(lukija.nextLine());
        } catch (Exception e) {
            System.out.println("Ei kirjaimii senki hessu vaan numeroita.");
        }
        return monellako;
    }

}
