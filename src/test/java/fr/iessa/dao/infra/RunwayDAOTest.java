package fr.iessa.dao.infra;

import static org.junit.Assert.*;
import org.junit.Test;
import fr.iessa.metier.infra.Runway;


/** Pour tester la classe RunwayDAO
 * @author duvernal
 * @version 1.0 
 */

public class RunwayDAOTest {


	@Test
	public void testChargerRunway1()
	{
		RunwayDAO rdao = new RunwayDAO();

		String ligneDuFichierTexte = "R 09L-27R 09L 27R -1682,1660;1009,1888 Z1;Z2;Z3;Z4;Z5;Z6;Z7;Z8";
		Runway actual =  rdao.charger(ligneDuFichierTexte);
		
		
		assertEquals("09L-27R", actual.get_nom());
		assertEquals("09L", actual.get_QFUL().toString());
		assertEquals("27R", actual.get_QFUR().toString());
		assertEquals(new java.awt.Point(-1682,1660), actual.get_extremite(0));
		assertEquals(1009, actual.get_extremite_x(1),0);		
		assertEquals(1888, actual.get_extremite_y(1),0);
		assertEquals(8, actual.get_listepoints().length, 0);
		assertEquals("Z1", actual.get_listepoints(0));
		assertEquals("Z2", actual.get_listepoints(1));
		assertEquals("Z3", actual.get_listepoints(2));
		assertEquals("Z4", actual.get_listepoints(3));
		assertEquals("Z5", actual.get_listepoints(4));
		assertEquals("Z6", actual.get_listepoints(5));
		assertEquals("Z7", actual.get_listepoints(6));
		assertEquals("Z8", actual.get_listepoints(7));
	
	}
	
	@Test
	public void testChargerRunway2()
	{
		
		RunwayDAO rdao = new RunwayDAO();

		String ligneDuFichierTexte = "R 09R-27L 09R 27L -2545,1201;1639,1555 K1;K2;K3;K6;K7;Y1;Y10;Y11;Y12;Y13;Y2;Y3;Y4;Y5;Y6;Y7;Y8";
		Runway actual =  rdao.charger(ligneDuFichierTexte);
		
		
		assertEquals("09R-27L", actual.get_nom());
		assertEquals("09R", actual.get_QFUR().toString());
		assertEquals("27L", actual.get_QFUL().toString());
		assertEquals(new java.awt.Point(-2545,1201), actual.get_extremite(0));
		assertEquals(1639, actual.get_extremite_x(1),0);		
		assertEquals(1555, actual.get_extremite_y(1),0);
		assertEquals(17, actual.get_listepoints().length, 0);
		assertEquals("K1", actual.get_listepoints(0));
		assertEquals("K2", actual.get_listepoints(1));
		assertEquals("K3", actual.get_listepoints(2));
		assertEquals("K6", actual.get_listepoints(3));
		assertEquals("K7", actual.get_listepoints(4));
		assertEquals("Y1", actual.get_listepoints(5));
		assertEquals("Y10", actual.get_listepoints(6));
		assertEquals("Y11", actual.get_listepoints(7));
		assertEquals("Y12", actual.get_listepoints(8));
		assertEquals("Y13", actual.get_listepoints(9));
		assertEquals("Y2", actual.get_listepoints(10));
		assertEquals("Y3", actual.get_listepoints(11));
		assertEquals("Y4", actual.get_listepoints(12));
		assertEquals("Y5", actual.get_listepoints(13));
		assertEquals("Y6", actual.get_listepoints(14));
		assertEquals("Y7", actual.get_listepoints(15));
		assertEquals("Y8", actual.get_listepoints(16));		
	

	}
}
