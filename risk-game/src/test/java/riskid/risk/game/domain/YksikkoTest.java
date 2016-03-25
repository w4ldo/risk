/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riskid.risk.game.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author imatias
 */
public class YksikkoTest {

    Yksikko yksikko;
    Alue alue;
    Pelaaja pelaaja;

    public YksikkoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        alue = new Alue(1);
        pelaaja = new Pelaaja("ossi");
        yksikko = new Yksikko(pelaaja, alue);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void konstruktoriAsettaaPelaajanOikein() {
        assertEquals(pelaaja, yksikko.getPelaaja());
    }
    
    @Test
    public void konstruktoriAsettaaAlueenOikein() {
        assertEquals(alue, yksikko.getAlue());
    }
    
    @Test
    public void VahvuusAlussaYksi() {
        assertEquals(1, yksikko.getVahvuus());
    }
    
    @Test
    public void setVahvuusAsettaaVahvuuden() {
        yksikko.setVahvuus(5);
        assertEquals(5, yksikko.getVahvuus());
    }

//     @Test
//     public void hello() {}
}
