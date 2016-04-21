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
        gui.teeLisaOsatNakyviksi();
        while (!onkoVoittajaa) {
            if (pelaajaAVuoro) {
                gui.uusiIlmoitus("Uusi vuoro: sininen");
                uusiVuoro(pelaajaA);
            } else {
                gui.uusiIlmoitus("Uusi vuoro: keltainen");
                uusiVuoro(pelaajaB);
            }
        }
        if (pelaajaAVuoro) {
            gui.uusiIlmoitus("gg, sininen voitti");
        } else {
            gui.uusiIlmoitus("gg, keltainen voitti");
        }
    }

    private void pelinAlustus() {
        pelaajaA.lisaaReserviin(60);
        pelaajaB.lisaaReserviin(60);
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
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro);
        }
    }

    private void aloitusjoukkojenVahvistus() {
        gui.uusiIlmoitus("Aloitusjoukkojen vahvistus. Lisää 3 yksikköä");
        while (pelaajaA.getReservi() > 0 || pelaajaB.getReservi() > 0) {
            if (gui.onkoTekstia()) {
                if (this.pelaajaAVuoro) {
                    lisajoukkojenSijoitus(pelaajaA, 3);
                } else {
                    lisajoukkojenSijoitus(pelaajaB, 3);
                }
                gui.uusiAlert("");
                gui.uusiIlmoitus("");
                pelaajaAVuoro = !pelaajaAVuoro;
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro);
        }
    }

    private void lisajoukkojenSijoitus(Pelaaja pelaaja, int maara) {
        int i = 0;
        gui.uusiIlmoitus("Lisäjoukkojen sijoitus");
        while (i < maara) {
            if (gui.onkoTekstia()) {
                int luku = lk.kysyLukua();
                if (!lk.onkoLukuSallittu(luku)) {
                    gui.uusiIlmoitus("Valitse alue 1-42");
                    continue;
                }
                if (!jl.sijoitaLisajoukkoja(map.getAlue(luku), 1, pelaaja)) {
                    continue;
                }
                i++;
            }
            gui.paivitaGui(map.tulostaKartta(), pelaajaAVuoro);
        }
    }

    private void uusiVuoro(Pelaaja pelaaja) {
        gui.uusiIlmoitus("Sijoita lisäjoukkoja");
        lisajoukkojenSijoitus(pelaaja, map.laskePelaajanLisajoukot(pelaaja));
        hyokkaysVaihe(pelaaja);
        if (onkoVoittajaa) {
            return;
        }
        vahvistusVaihe(pelaaja);
        pelaajaAVuoro = !pelaajaAVuoro;
    }

    //hyökkäysvaiheessa pelaaja suorittaa omilta alueiltaan niin monta hyökkäystä vihollisen alueille
    //kuin haluaa. Lopetetaan komennolla 999
    private void hyokkaysVaihe(Pelaaja pelaaja) {
        gui.uusiAlert("Hyökkaysvaihe, lopeta komennolla 999");
        while (true) {
            if (gui.onkoTekstia()) {
                int mista = lk.kysyLukua();
                if (lk.onkoLukuSallittu(mista)) {
                    if (map.getAlue(mista).getHallitsija() != pelaaja) {
                        gui.uusiIlmoitus("Hyökkää omalta alueeltasi");
                        continue;
                    }
                    gui.uusiIlmoitus("Minne hyökätään?");
                    int minne = lk.kysyLukua();
//                    if (lk.onkoLukuSallittu(minne)) {
//                        if (map.getAlue(minne).getHallitsija() == pelaaja) {
//                            gui.uusiIlmoitus("Hyökkää vihollisesi alueelle");
//                            continue;
//                        }
//                        gui.uusiIlmoitus("Monellako hyökätään?");
//                        int monellako = 0;
//                        monellako = lk.monellakoHyokataan();
//                        jl.hyokkaaTaiValtaa(map.getAlue(mista), map.getAlue(minne), monellako);
                        if (map.voittaako(pelaaja)) {
                            onkoVoittajaa = true;
                            break;
                        }
//                    } else {
//                        gui.uusiIlmoitus("Laiton siirto");
//                    }
                } else if (mista == 999) {
                    break;
                }
            }
        }
    }

    //vahvistusvaiheessa pelaaja suorittaa enintään 3 siirtoa omilla alueillaan.
    //Lopetetaan komennolla 999, tai kun 3 siirtoa on suoritettu
    private void vahvistusVaihe(Pelaaja pelaaja) {
        gui.uusiIlmoitus("Vahvistusvaihe, siirrä enintään kolmea omaa joukkoa, lopeta komennolla 999");
        int siirtoja = 0;
        while (siirtoja < 3) {
            gui.uusiIlmoitus(
                    "Mitä joukkoa liikutetaan?");
            int mista = lk.kysyLukua();
            if (lk.onkoLukuSallittu(mista)) {
                if (map.getAlue(mista).getHallitsija() != pelaaja) {
                    gui.uusiIlmoitus("Siirrä omalta alueeltasi");
                    continue;
                }
                gui.uusiIlmoitus("Mihin liikutaan?");
                int mihin = lk.kysyLukua();
                if (lk.onkoLukuSallittu(mihin)) {
                    if (map.getAlue(mihin).getHallitsija() != pelaaja) {
                        gui.uusiIlmoitus("Siirrä omalle alueellesi");
                        continue;
                    }
                    gui.uusiIlmoitus("Montako siirretään?");
                    int montako = lk.kysyLukua();
                    if (map.getAlue(mista).onkoViereinen(map.getAlue(mihin)) && montako >= 1 && montako < map.getAlue(mista).getYksikonVahvuus()) {
                        jl.passiivinenLiike(map.getAlue(mista), map.getAlue(mihin), montako);
                    } else {
                        gui.uusiIlmoitus("Siirrä vain viereiselle alueelle.");
                        gui.uusiIlmoitus("Liikuta vähintään yhtä ja jätä vähintään yksi taakse");
                        continue;
                    }
                } else {
                    continue;
                }
            } else if (mista == 999) {
                break;
            } else {
                continue;
            }
            siirtoja++;
        }
    }

}
