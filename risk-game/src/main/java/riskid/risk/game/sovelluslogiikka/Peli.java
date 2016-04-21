package riskid.risk.game.sovelluslogiikka;

import java.util.Scanner;
import riskid.risk.game.domain.*;
import riskid.risk.game.kayttoliittyma.GUI;

public class Peli {

    private MapBuilder mb;
    private JoukkojenLiikuttaja jl;
    private LuvunKysyja lk;
    private Kartta map;
    private Pelaaja pelaajaA;
    private Pelaaja pelaajaB;
    private boolean pelaajaAVuoro;
    private boolean onkoVoittajaa;
    private GUI gui;

    public Peli() {
        this.gui = new GUI();
        this.mb = new MapBuilder();
        this.jl = new JoukkojenLiikuttaja(gui);
        this.map = mb.buildmap();
        this.pelaajaA = new Pelaaja("Alpha");
        this.pelaajaB = new Pelaaja("Bravo");
        this.pelaajaAVuoro = true;
        this.onkoVoittajaa = false;

        this.lk = new LuvunKysyja(gui);
    }

    public void run() {
        gui.setVisible(true);
        gui.teeLisaOsatNakymattomiksi();
        pelinAlustus();
        while (!onkoVoittajaa) {
            if (pelaajaAVuoro) {
                uusiVuoro(pelaajaA);
            } else {
                uusiVuoro(pelaajaB);
            }
        }
        gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        if (pelaajaAVuoro) {
            gui.uusiIlmoitus("gg, Alpha voitti");
        } else {
            gui.uusiIlmoitus("gg, Bravo voitti");
        }
    }

    private void pelinAlustus() {
        pelaajaA.lisaaReserviin(57);
        pelaajaB.lisaaReserviin(57);
        aloitusjoukkojenSijoitus();
        aloitusjoukkojenVahvistus();
    }

    private void aloitusjoukkojenSijoitus() {
        gui.uusiIlmoitus("Aloitusjoukkojen sijoitus");
        while (map.onkoTyhjiaAlueita()) {
            if (gui.onkoTekstia()) {
                int luku = lk.kysyLukua();
                if (!lk.onkoLukuSallittu(luku)) {
                    continue;
                }
                if (pelaajaAVuoro) {
                    if (!jl.sijoitaAloitusJoukkoja(map.getAlue(luku), pelaajaA)) {
                        continue;
                    }
                } else if (!jl.sijoitaAloitusJoukkoja(map.getAlue(luku), pelaajaB)) {
                    continue;
                }
                gui.uusiAlert("");
                gui.uusiIlmoitus("");
                pelaajaAVuoro = !pelaajaAVuoro;
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        }
    }

    private void aloitusjoukkojenVahvistus() {
        gui.uusiIlmoitus("Aloitusjoukkojen vahvistus. Lisää 2x2 yksikköä");
        while (pelaajaA.getReservi() > 0 || pelaajaB.getReservi() > 0) {
            if (gui.onkoTekstia()) {
                if (this.pelaajaAVuoro) {
                    lisajoukkojenSijoitus(pelaajaA, 2, 2);
                } else {
                    lisajoukkojenSijoitus(pelaajaB, 2, 2);
                }
                gui.uusiAlert("");
                gui.uusiIlmoitus("");
                pelaajaAVuoro = !pelaajaAVuoro;
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        }
    }

    private void lisajoukkojenSijoitus(Pelaaja pelaaja, int montakoKertaa, int montakoKerralla) {
        int i = 0;
        gui.uusiIlmoitus("Lisäjoukkojen sijoitus");
        while (i < montakoKertaa) {
            if (gui.onkoTekstia()) {
                int luku = lk.kysyLukua();
                if (!lk.onkoLukuSallittu(luku)) {
                    continue;
                }
                if (!jl.sijoitaLisajoukkoja(map.getAlue(luku), montakoKerralla, pelaaja)) {
                    continue;
                }
                i++;
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        }
    }

    private void uusiVuoro(Pelaaja pelaaja) {
        gui.uusiIlmoitus("Sijoita lisäjoukkoja");
        pelaaja.lisaaReserviin(map.laskePelaajanLisajoukot(pelaaja));
        lisajoukkojenSijoitus(pelaaja, map.laskePelaajanLisajoukot(pelaaja) / 3, 3);
        gui.teeLisaOsatNakyviksi();
        hyokkaysVaihe(pelaaja);
        if (onkoVoittajaa) {
            return;
        }
        vahvistusVaihe(pelaaja);
        gui.teeLisaOsatNakymattomiksi();
        pelaajaAVuoro = !pelaajaAVuoro;
    }

    //hyökkäysvaiheessa pelaaja suorittaa omilta alueiltaan niin monta hyökkäystä vihollisen alueille
    //kuin haluaa. Lopetetaan komennolla 999
    private void hyokkaysVaihe(Pelaaja pelaaja) {
        gui.uusiAlert("Hyökkaysvaihe");
        while (true) {
            if (gui.onkoTekstia()) {
                int mista = lk.kysyLukua();
                int mihin = lk.mihinSiirretaan();
                int monellako = lk.montakoSiirretaan();
                if (lk.onkoLukuSallittu(mista)) {
                    jl.hyokkaaTaiValtaa(map.getAlue(mista), map.getAlue(mihin), monellako);
                    if (map.voittaako(pelaaja)) {
                        onkoVoittajaa = true;
                        break;
                    }
                } else if (mista == 999) {
                    break;
                }
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        }
    }

    //vahvistusvaiheessa pelaaja suorittaa enintään 3 siirtoa omilla alueillaan.
    //Lopetetaan komennolla 999, tai kun 3 siirtoa on suoritettu
    private void vahvistusVaihe(Pelaaja pelaaja) {
        gui.uusiIlmoitus("Vahvistusvaihe, siirrä enintään kolmea omaa joukkoa");
        int siirtoja = 0;
        while (siirtoja < 3) {
            if (gui.onkoTekstia()) {
                int mista = lk.kysyLukua();
                int mihin = lk.mihinSiirretaan();
                int montako = lk.montakoSiirretaan();
                if (lk.onkoLukuSallittu(mista)) {
                    if (jl.passiivinenLiike(map.getAlue(mista), map.getAlue(mihin), montako)) {
                        siirtoja++;
                    } else {
                        gui.uusiAlert("Siirrä vain viereiselle alueelle.");
                        gui.uusiIlmoitus("Liikuta vähintään yhtä ja jätä vähintään yksi taakse");
                        continue;
                    }
                } else if (mista == 999) {
                    break;
                } else {
                    continue;
                }
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro, pelaajaA.getReservi(), pelaajaB.getReservi());
        }
    }

}
