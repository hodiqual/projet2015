/**
 * 
 */
package fr.iessa.dao.trafic;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 *
 */
public class TraficDaoTest {

	/**
	 * Test method for {@link fr.iessa.dao.trafic.TraficDao#charger(java.lang.String)}.
	 */
	@Test
	public void testCharger() {
		TraficDao traficDao = new TraficDao();
		
		Trafic trafic = traficDao.charger("trafic.txt");
		assertEquals(1,trafic.getVols(InstantFabrique.get(1440)).size());
		//Vols 1573, Instant 16452
		//assertEquals(1573, trafic.getVols().size());
	}

	/**
	 * Test method for {@link fr.iessa.dao.trafic.TraficDao#chargerVol(java.util.Scanner)}.
	 */
	@Test
	public void testChargerVolDep() {
		String ligneFormatVol = "DEP BCS1748 M M17 27L 1440 _ -946,-1116 -938,-1128\n";
		
		TraficDao traficDao = new TraficDao();
		Vol vol = traficDao.chargerVol(ligneFormatVol);
		
		assertEquals(TypeVol.DEP, vol.getTypeVol());
		assertEquals(Categorie.MEDIUM, vol.getCategorie());
		assertEquals("BCS1748", vol.getId());
		
		TreeMap<Instant, Point> expectedMap = new TreeMap<>();
		expectedMap.put( InstantFabrique.get(1440), new Point(-946,-1116));
		expectedMap.put( InstantFabrique.get(1445), new Point(-938,-1128));
		assertEquals(expectedMap, vol.getInstantVersCoord());
	}
	
	/**
	 * Test method for {@link fr.iessa.dao.trafic.TraficDao#chargerVol(java.util.Scanner)}.
	 */
	@Test
	public void testChargerVolArr() {
		String ligneFormatVol =  "ARR SRR6316 H M15 27R 3815 _ 1565,1935 1009,1888\n";
		
		TraficDao traficDao = new TraficDao();
		Vol vol = traficDao.chargerVol(ligneFormatVol);
		
		assertEquals(TypeVol.ARR, vol.getTypeVol());
		assertEquals(Categorie.HIGH, vol.getCategorie());
		assertEquals("SRR6316", vol.getId());
		
		TreeMap<Instant, Point> expectedMap = new TreeMap<>();
		expectedMap.put( InstantFabrique.get(3815), new Point(1565,1935) );
		expectedMap.put( InstantFabrique.get(3820), new Point(1009,1888) );
		assertEquals(expectedMap, vol.getInstantVersCoord());
	}

}
