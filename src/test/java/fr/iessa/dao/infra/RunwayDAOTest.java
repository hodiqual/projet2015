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
	public void testChargerRunway()
	{
		RunwayDAO rdao = new RunwayDAO();

		String ligneDuFichierTexte = "R 09L-27R 09L 27R -1682,1660;1009,1888 Z1;Z2;Z3;Z4;Z5;Z6;Z7;Z8";
		Runway actual =  rdao.charger(ligneDuFichierTexte);
		
		
		assertEquals("09L-27R", actual.get_nom());
		assertEquals("09L", actual.get_QFUL());
		assertEquals("27R", actual.get_QFUR());
		assertEquals("-1682,1660", actual.get_extremite(0));
		assertEquals("1009", actual.get_extremite_x(1));		
		assertEquals("1888", actual.get_extremite_y(1));
	
	}
}
