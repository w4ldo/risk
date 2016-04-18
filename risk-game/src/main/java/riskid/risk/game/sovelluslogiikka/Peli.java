package riskid.risk.game.sovelluslogiikka;

import java.util.Scanner;
import riskid.risk.game.domain.*;

public class Peli {

    private MapBuilder mb;
    private JoukkojenLiikuttaja jl;
    private Scanner lukija;
    private Kartta map;
    private Pelaaja sininen;
    private Pelaaja keltainen;
    private boolean sinisenVuoro;
    private boolean onkoVoittajaa;

    public Peli() {
        this.mb = new MapBuilder();
        this.jl = new JoukkojenLiikuttaja();
        this.lukija = new Scanner(System.in);
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

        System.out.println("reservit: sininen" + sininen.getReservi() + ", keltainen " + keltainen.getReservi());
    }

    private void aloitusjoukkojenSijoitus() {
        System.out.println("Aloitusjoukkojen sijoitus");
        while (map.onkoTyhjiaAlueita()) {
            int luku = kysyLukua();

            if (!onkoLukuSallittu(luku)) {
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
            int luku = kysyLukua();
            if (!onkoLukuSallittu(luku)) {
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

    private void hyokkaysVaihe(Pelaaja pelaaja) {
        System.out.println("Hyökkaysvaihe, lopeta komennolla 999");
        while (true) {
            System.out.println("Mistä hyökätään?");
            int mista = kysyLukua();
            if (onkoLukuSallittu(mista)) {
                if (map.getAlue(mista).getHallitsija() != pelaaja) {
                    System.out.println("Hyökkää omalta alueeltasi");
                    continue;
                }
                System.out.println("Minne hyökätään?");
                int minne = kysyLukua();
                if (onkoLukuSallittu(minne)) {
                    if (map.getAlue(minne).getHallitsija() == pelaaja) {
                    System.out.println("Hyökkää vihollisesi alueelle");
                    continue;
                }
                    System.out.println("Monellako hyökätään?");
                    int monellako = 0;
                    try {
                        monellako = Integer.parseInt(lukija.nextLine());
                    } catch (Exception e) {
                        System.out.println("Ei kirjaimii senki hessu vaan numeroita.");
                        continue;
                    }
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

    private void vahvistusVaihe(Pelaaja pelaaja) {
        System.out.println("Vahvistusvaihe, siirrä enintään kolmea omaa joukkoa, lopeta komennolla 999");
        int x = 0;
        while (x < 3) {
            System.out.println("Mitä joukkoa liikutetaan?");
            int mista = kysyLukua();
            if (onkoLukuSallittu(mista)) {
                if (map.getAlue(mista).getHallitsija() != pelaaja) {
                    System.out.println("Siirrä omalta alueeltasi");
                    continue;
                }
                System.out.println("Mihin liikutaan?");
                int mihin = kysyLukua();
                if (onkoLukuSallittu(mihin)) {
                    if (map.getAlue(mihin).getHallitsija() != pelaaja) {
                    System.out.println("Siirrä omalle alueellesi");
                    continue;
                }
                    if (map.getAlue(mista).onkoViereinen(map.getAlue(mihin))) {
                        System.out.println("Montako siirretään?");
                        int montako = kysyLukua();
                        if (montako >= 1 && montako < map.getAlue(mista).getYksikonVahvuus()) {
                            jl.passiivinenLiike(map.getAlue(mista), map.getAlue(mihin), montako);
                        } else {
                            System.out.println("Liikuta vähintään yhtä ja jätä vähintään yksi taakse");
                            continue;
                        }
                    } else {
                        System.out.println("Siirrä vain viereiselle alueelle");
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
            x++;
        }
    }

    private int kysyLukua() {
        int luku = 0;
        try {
            luku = Integer.parseInt(lukija.nextLine());
        } catch (Exception e) {
            System.out.println("Ei kirjaimii senki hessu vaan numeroita.");
        }
        return luku;
    }

    private boolean onkoLukuSallittu(int luku) {
        if (luku > 0 && luku < 43) {
            return true;
        }
        return false;
    }

}
