package riskid.risk.game.sovelluslogiikka;

import java.util.Scanner;
import riskid.risk.game.domain.*;

public class Peli {

    private MapBuilder mb;
    private JoukkojenLiikuttaja jl;
    private LuvunKysyja lk;
    private Kartta map;
    private Pelaaja sininen;
    private Pelaaja keltainen;
    private boolean sinisenVuoro;
    private boolean onkoVoittajaa;

    public Peli(Scanner lukija) {
        this.mb = new MapBuilder();
        this.lk = new LuvunKysyja(lukija);
        this.jl = new JoukkojenLiikuttaja();
        this.map = mb.buildmap();
        this.sininen = new Pelaaja("Sininen");
        this.keltainen = new Pelaaja("Keltainen");
        this.sinisenVuoro = true;
        this.onkoVoittajaa = false;
    }

    public void run() {
        pelinAlustus();
        while (!onkoVoittajaa) {
            if (sinisenVuoro) {
                System.out.println("Uusi vuoro: sininen");
                uusiVuoro(sininen);
            } else {
                System.out.println("Uusi vuoro: keltainen");
                uusiVuoro(keltainen);
            }
        }
        if (sinisenVuoro) {
            System.out.println("gg, sininen voitti");
        } else {
            System.out.println("gg, keltainen voitti");
        }
    }

    private void pelinAlustus() {
        sininen.lisaaReserviin(60);
        keltainen.lisaaReserviin(60);
        aloitusjoukkojenSijoitus();
        aloitusjoukkojenVahvistus();
    }

    private void aloitusjoukkojenSijoitus() {
        System.out.println("Aloitusjoukkojen sijoitus");
        while (map.onkoTyhjiaAlueita()) {
            int luku = lk.kysyLukua();
            if (!lk.onkoLukuSallittu(luku)) {
                System.out.println("Valitse alue 1-42");
                continue;
            }
            if (sinisenVuoro) {
                if (!jl.sijoitaAloitusJoukkoja(map.getAlue(luku), sininen)) {
                    continue;
                }
            } else if (!jl.sijoitaAloitusJoukkoja(map.getAlue(luku), keltainen)) {
                continue;
            }
            sinisenVuoro = !sinisenVuoro;
        }
    }

    private void aloitusjoukkojenVahvistus() {
        System.out.println("Aloitusjoukkojen vahvistus");
        while (sininen.getReservi() > 0 || keltainen.getReservi() > 0) {
            if (this.sinisenVuoro) {
                lisajoukkojenSijoitus(sininen, 3);
            } else {
                lisajoukkojenSijoitus(keltainen, 3);
            }
            sinisenVuoro = !sinisenVuoro;
        }
    }

    private void lisajoukkojenSijoitus(Pelaaja pelaaja, int maara) {
        int i = 0;
        System.out.println("Lisäjoukkojen sijoitus");
        while (i < maara) {
            int luku = lk.kysyLukua();
            if (!lk.onkoLukuSallittu(luku)) {
                System.out.println("Valitse alue 1-42");
                continue;
            }
            if (!jl.sijoitaLisajoukkoja(map.getAlue(luku), 1, pelaaja)) {
                continue;
            }
            i++;
        }
    }

    private void uusiVuoro(Pelaaja pelaaja) {
        System.out.println("Sijoita lisäjoukkoja");
        lisajoukkojenSijoitus(pelaaja, map.laskePelaajanLisajoukot(pelaaja));
        hyokkaysVaihe(pelaaja);
        if (onkoVoittajaa) {
            return;
        }
        vahvistusVaihe(pelaaja);
        sinisenVuoro = !sinisenVuoro;
    }

    //hyökkäysvaiheessa pelaaja suorittaa omilta alueiltaan niin monta hyökkäystä vihollisen alueille
    //kuin haluaa. Lopetetaan komennolla 999
    private void hyokkaysVaihe(Pelaaja pelaaja) {
        System.out.println("Hyökkaysvaihe, lopeta komennolla 999");
        while (true) {
            System.out.println("Mistä hyökätään?");
            int mista = lk.kysyLukua();
            if (lk.onkoLukuSallittu(mista)) {
                if (map.getAlue(mista).getHallitsija() != pelaaja) {
                    System.out.println("Hyökkää omalta alueeltasi");
                    continue;
                }
                System.out.println("Minne hyökätään?");
                int minne = lk.kysyLukua();
                if (lk.onkoLukuSallittu(minne)) {
                    if (map.getAlue(minne).getHallitsija() == pelaaja) {
                        System.out.println("Hyökkää vihollisesi alueelle");
                        continue;
                    }
                    System.out.println("Monellako hyökätään?");
                    int monellako = 0;
                    monellako = lk.monellakoHyokataan();
                    jl.hyokkaaTaiValtaa(map.getAlue(mista), map.getAlue(minne), monellako);
                    if (map.voittaako(pelaaja)) {
                        onkoVoittajaa = true;
                        break;
                    }
                } else {
                    System.out.println("Laiton siirto");
                }
            } else if (mista == 999) {
                break;
            }
        }
    }

    //vahvistusvaiheessa pelaaja suorittaa enintään 3 siirtoa omilla alueillaan.
    //Lopetetaan komennolla 999, tai kun 3 siirtoa on suoritettu
    private void vahvistusVaihe(Pelaaja pelaaja) {
        System.out.println("Vahvistusvaihe, siirrä enintään kolmea omaa joukkoa, lopeta komennolla 999");
        int siirtoja = 0;
        while (siirtoja < 3) {
            System.out.println("Mitä joukkoa liikutetaan?");
            int mista = lk.kysyLukua();
            if (lk.onkoLukuSallittu(mista)) {
                if (map.getAlue(mista).getHallitsija() != pelaaja) {
                    System.out.println("Siirrä omalta alueeltasi");
                    continue;
                }
                System.out.println("Mihin liikutaan?");
                int mihin = lk.kysyLukua();
                if (lk.onkoLukuSallittu(mihin)) {
                    if (map.getAlue(mihin).getHallitsija() != pelaaja) {
                        System.out.println("Siirrä omalle alueellesi");
                        continue;
                    }
                    System.out.println("Montako siirretään?");
                    int montako = lk.kysyLukua();
                    if (map.getAlue(mista).onkoViereinen(map.getAlue(mihin)) && montako >= 1 && montako < map.getAlue(mista).getYksikonVahvuus()) {
                        jl.passiivinenLiike(map.getAlue(mista), map.getAlue(mihin), montako);
                    } else {
                        System.out.println("Siirrä vain viereiselle alueelle.");
                        System.out.println("Liikuta vähintään yhtä ja jätä vähintään yksi taakse");
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
