/**
 * 
 */
package fr.iessa.metier.trafic;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

/**
 * @author duvernal
 *
 */
public class PointFabriqueTest {

	/**
	 * Test method for {@link fr.iessa.metier.trafic.PointFabrique#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		
		String coord = "-4234,234";
		
		Point premiereDemande = PointFabrique.get(coord);
		Point deuxiemeDemande = PointFabrique.get(coord);
		
		assertSame("les deux demandes doivent retourner la meme instance de Point", 
						premiereDemande, deuxiemeDemande);		
	}

}
