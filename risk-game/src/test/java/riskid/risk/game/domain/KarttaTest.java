/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riskid.risk.game.domain;

import java.util.*;
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
public class KarttaTest {

    Kartta kartta;
    Manner manner;
    Manner toinenManner;
    Alue alue;
    Alue toinen;
    Alue kolmas;

    public KarttaTest() {
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
        toinen = new Alue(2);
        kolmas = new Alue(3);
        manner = new Manner(3, "manner", alue, toinen);
        toinenManner = new Manner(2, "toinenManner", kolmas);
        kartta = new Kartta(manner, toinenManner);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void konstruktoriAsettaaMantereetOikein() {
        Map<String, Manner> map = new HashMap<>();
        map.put("manner", manner);
        map.put("toinenManner", toinenManner);
        assertEquals(map, kartta.getMantereet());
    }
    
    @Test
    public void toStringToimii() {
        assertEquals("toinenManner: 3\nmanner: 1, 2", kartta.toString());
    }
    
//     @Test
//     public void hello() {}
}
