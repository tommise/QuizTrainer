
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti maksukortti;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(1000);
    }

    @Test
    public void alussaOikeaMaaraSaldoa() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }

    @Test
    public void alussaEdullisiaLounaitaEiOleMyyty() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void alussaMaukkaitaLounaitaEiOleMyyty() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    // Kateismaksut
    
    @Test
    public void edullisenLounaanKateismaksuKerryttaaKassaa() {
        kassapaate.syoEdullisesti(240);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void maukkaanLounaanKateismaksuKerryttaaKassaa() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }  
    
    @Test
    public void edullisenLounaanKateismaksuKasvattaaMyytyja() {
        kassapaate.syoEdullisesti(240);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanKateismaksuKasvattaaMyytyja() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanKateismaksuPalauttaaOikeanVaihtorahan() {
        assertEquals(10, kassapaate.syoEdullisesti(250));
    }
    
    @Test
    public void maukkaanLounaanKateismaksuPalauttaaOikeanVaihtorahan() {
        assertEquals(20, kassapaate.syoMaukkaasti(420));
    }
    
    @Test
    public void edullisenLounaanLiianPieniKateismaksuEiMeneKassaan() {
        kassapaate.syoEdullisesti(230);
        assertEquals(100000, kassapaate.kassassaRahaa());        
    }
    
    @Test
    public void maukkaanLounaanLiianPieniKateismaksuEiMeneKassaan() {
        kassapaate.syoMaukkaasti(390);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void edullisenLounaanLiianPieniKateismaksuPalauttaaKaikki() {
        assertEquals(230, kassapaate.syoEdullisesti(230));
    }

    @Test
    public void maukkaanLounaanLiianPieniKateismaksuPalauttaaKaikki() {
        assertEquals(390, kassapaate.syoMaukkaasti(390));
    }
    
    @Test
    public void edullisenLounaanLiianPieniKateismaksuEiKasvataMyytyja() {
        kassapaate.syoEdullisesti(230);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());        
    }
    
    @Test
    public void maukkaanLounaanLiianPieniKateismaksuEiKasvataMyytyja() {
        kassapaate.syoMaukkaasti(390);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    // Korttimaksut
    
    @Test
    public void edullisenLounaanOnnistunutKorttimaksuPalauttaaTrue() {
        assertTrue(kassapaate.syoEdullisesti(maksukortti));
    }
    
    @Test
    public void maukkaanLounaanOnnistunutKorttimaksuPalauttaaTrue() {
        assertTrue(kassapaate.syoMaukkaasti(maksukortti));
    }
    
    @Test
    public void edullisenLounaanKorttimaksuVahentaaKortinSaldoa() {
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(760, maksukortti.saldo());
    }
    
    @Test
    public void maukkaanLounaanKorttimaksuVahentaaKortinSaldoa() {
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(600, maksukortti.saldo());
    }
    
    @Test
    public void edullisenLounaanKorttimaksuKasvattaaMyytyja() {
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanKorttimaksuKasvattaaMyytyja() {
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanEpaonnistunutKorttimaksuPalauttaaFalse() {
        maksukortti.otaRahaa(800);
        assertFalse(kassapaate.syoEdullisesti(maksukortti));
    }
    
    @Test
    public void maukkaanLounaanEpaonnistunutKorttimaksuPalauttaaFalse() {
        maksukortti.otaRahaa(700);
        assertFalse(kassapaate.syoMaukkaasti(maksukortti));
    }
    
    @Test
    public void edullisenLounaanLiianPieniKorttimaksuEiVahennaKortinSaldoa() {
        maksukortti.otaRahaa(800);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(200, maksukortti.saldo());
    }
    
    @Test
    public void maukkaanLounaanLiianPieniKorttimaksuEiVahennaKortinSaldoa() {
        maksukortti.otaRahaa(700);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(300, maksukortti.saldo());
    }
    
    @Test
    public void edullisenLounaanEpaonnistunutKorttimaksuEiKasvataMyytyja() {
        maksukortti.otaRahaa(800);
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanEpaonnistunutKorttimaksuEiKasvataMyytyja() {
        maksukortti.otaRahaa(700);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanKorttimaksuEiKerrytaKassaa() {
        kassapaate.syoEdullisesti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());        
    }
    
    @Test
    public void maukkaanLounaanKorttimaksuEiKerrytaKassaa() {
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());           
    }
    
    @Test
    public void rahanLataaminenKasvattaaKortinSaldoa() {
        kassapaate.lataaRahaaKortille(maksukortti, 100);
        assertEquals(1100, maksukortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaKassaa() {
        kassapaate.lataaRahaaKortille(maksukortti, 100);
        assertEquals(100100, kassapaate.kassassaRahaa());        
    }
    
    @Test
    public void kortilleNegatiivistaSummaaLadattaessaKortinSaldoEiMuutu() {
        kassapaate.lataaRahaaKortille(maksukortti, -100);
        assertEquals(1000, maksukortti.saldo());        
    }
    
    @Test
    public void kortilleNegatiivistaSummaaLadattaessaKassanSaldoEiMuutu() {
        kassapaate.lataaRahaaKortille(maksukortti, -100);
        assertEquals(100000, kassapaate.kassassaRahaa());         
    }
}
