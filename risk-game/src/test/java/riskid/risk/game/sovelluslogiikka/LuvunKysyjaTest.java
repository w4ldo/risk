/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riskid.risk.game.sovelluslogiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import riskid.risk.game.kayttoliittyma.GUI;

/**
 *
 * @author imatias
 */
public class LuvunKysyjaTest {

    private LuvunKysyja lk;

    public LuvunKysyjaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        GUI gui = new GUI();
        lk = new LuvunKysyja(gui);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void onkoLukuSallittuToimii() {
        for (int i = 1; i < 43; i++) {
            assertTrue(lk.onkoLukuSallittu(i));
        }
        assertFalse(lk.onkoLukuSallittu(0));
        assertFalse(lk.onkoLukuSallittu(43));
    }
}
