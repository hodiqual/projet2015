package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import java.awt.geom.PathIterator;

import org.junit.Test;

import fr.iessa.dao.infra.PlateformeDAO.Lookup;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Pushback;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

public class PushbackDAOTest {

	//l'objet a tester
	private PushbackDAO _pushbackDAO  = (PushbackDAO) Lookup.PUSHBACK.get();

	@Test
	public void testChargerPushback()
	{
		String ligneDuFichierTexte = "L _ -3 H S -663,644;-681,654;-702,670;-724,688;-744,709;-758,730;-764,750;-760,767";
		Pushback actual =  _pushbackDAO.charger(ligneDuFichierTexte);
		
		assertEquals(-3, actual.get_vitesseDeRoulage());
		assertSame(Categorie.HIGH, actual.get_categorie());
		assertEquals(Direction.SINGLE, actual.get_direction());
		
		//Test les points inseres
		PathIterator iterator = actual.get_lignePointAPoint().getPathIterator(null);
		float[] actualCoords = new float[2];
		assertEquals( PathIterator.SEG_MOVETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-663, actualCoords[0],0);
		assertEquals("Coord Y: ",644, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-681, actualCoords[0],0);
		assertEquals("Coord Y: ",654, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-702, actualCoords[0],0);
		assertEquals("Coord Y: ",670, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-724, actualCoords[0],0);
		assertEquals("Coord Y: ",688, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-744, actualCoords[0],0);
		assertEquals("Coord Y: ",709, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-758, actualCoords[0],0);
		assertEquals("Coord Y: ",730, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-764, actualCoords[0],0);
		assertEquals("Coord Y: ",750, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-760, actualCoords[0],0);
		assertEquals("Coord Y: ",767, actualCoords[1],0);
	}
	
	

}
