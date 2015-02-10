package fr.iessa.metier.infra;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.iessa.dao.infra.PlateformeDAO;

public class AeroportTest {

	static Aeroport aeroport;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 aeroport = PlateformeDAO.charger("lfpg.txt");
	}

	@Test
	public void testInitialiseBounds() {
		aeroport.initialiseBounds();
		
		assertEquals(-3879, aeroport.getMinX(),0);
		assertEquals(4571,  aeroport.getMaxX(),0);
		assertEquals(-2219, aeroport.getMinY(),0);
		assertEquals(1880,  aeroport.getMaxY(),0);
	}

}
