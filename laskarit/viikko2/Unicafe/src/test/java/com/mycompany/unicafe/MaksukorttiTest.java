package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoa() {
        kortti.lataaRahaa(15);
        assertEquals(25, kortti.saldo());
    }

    @Test
    public void rahanOttaminenVahentaaSaldoa() {
        kortti.otaRahaa(9);
        assertEquals(1, kortti.saldo());
    }

    @Test
    public void saldoEiMuutuJosOttaminenMeneeMiinukselle() {
        kortti.otaRahaa(25);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void rahanOttaminenPalauttaaTrueJosSaldoRiittaa() {
        assertTrue(kortti.otaRahaa(5));
    }

    @Test
    public void rahanOttaminenPalauttaaFalseJosSaldoEiRiita() {
        assertFalse(kortti.otaRahaa(15));
    } 
    
    @Test
    public void toStringPalauttaaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
