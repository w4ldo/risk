package riskid.risk.game.sovelluslogiikka;

import riskid.risk.game.kayttoliittyma.GUI;



public class LuvunKysyja {
    private GUI gui;

    public LuvunKysyja(GUI gui) {
        this.gui = gui;
    }

    public int kysyLukua() {
        int luku = 0;
        try {
            luku = Integer.parseInt(gui.getTekstiKentta());
        } catch (Exception e) {
            gui.uusiAlert("Ei kirjaimii senki hessu vaan numeroita.");
            gui.getTekstiKentta();
        }
        return luku;
    }

    public boolean onkoLukuSallittu(int luku) {
        if (luku > 0 && luku < 43) {
            return true;
        } else if (luku == 999) {
            return false;
        }
        gui.uusiIlmoitus("Valitse alue 1-42");
        return false;
    }

    public int monellakoHyokataan() {
        int monellako = 0;
        try {
            monellako = Integer.parseInt(gui.getTekstiKentta());
        } catch (Exception e) {
            gui.uusiIlmoitus("Ei kirjaimii senki hessu vaan numeroita.");
        }
        return monellako;
    }

}
