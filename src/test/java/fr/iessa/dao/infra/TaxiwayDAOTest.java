package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import java.awt.geom.PathIterator;

import org.junit.Test;

import fr.iessa.dao.infra.InfrastructureDAO.Lookup;
import fr.iessa.metier.infra.Taxiway;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

public class TaxiwayDAOTest {

	//l'objet ˆ tester
	private TaxiwayDAO _taxiwayDAO  = (TaxiwayDAO) Lookup.TAXIWAY.get();

	@Test
	public void testChargerTaxiway1()
	{
		String ligneDuFichierTexte = "L AEF 10 H S -494,923;-508,922";
		Taxiway actual =  _taxiwayDAO.charger(ligneDuFichierTexte);
		
		assertEquals("AEF", actual.get_nom());
		assertEquals(10, actual.get_vitesseDeRoulage());
		assertSame(Categorie.HIGH, actual.get_categorie());
		assertEquals(Direction.SINGLE, actual.get_direction());
		
		//Test les points inseres
		PathIterator iterator = actual.get_lignePointAPoint().getPathIterator(null);
		float[] actualCoords = new float[2];
		assertEquals( PathIterator.SEG_MOVETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-494, actualCoords[0],0);
		assertEquals("Coord Y: ",923, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-508, actualCoords[0],0);
		assertEquals("Coord Y: ",922, actualCoords[1],0);
	}
	
	
	@Test
	public void testChargerTaxiway2()
	{
		String ligneDuFichierTexte = "L AIG 3 H S -440,926;-494,923;-510,916;-523,899;-532,875;-538,846;-539,816;-536,786;-528,759";
		Taxiway actual =  _taxiwayDAO.charger(ligneDuFichierTexte);
		
		assertEquals("AIG", actual.get_nom());
		assertEquals(3, actual.get_vitesseDeRoulage());
		assertSame(Categorie.HIGH, actual.get_categorie());
		assertEquals(Direction.SINGLE, actual.get_direction());
		
		//Test les points inseres
		PathIterator iterator = actual.get_lignePointAPoint().getPathIterator(null);
		float[] actualCoords = new float[2];
		assertEquals( PathIterator.SEG_MOVETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-440, actualCoords[0],0);
		assertEquals("Coord Y: ",926, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-494, actualCoords[0],0);
		assertEquals("Coord Y: ",923, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-510, actualCoords[0],0);
		assertEquals("Coord Y: ",916, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-523, actualCoords[0],0);
		assertEquals("Coord Y: ",899, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-532, actualCoords[0],0);
		assertEquals("Coord Y: ",875, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-538, actualCoords[0],0);
		assertEquals("Coord Y: ",846, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-539, actualCoords[0],0);
		assertEquals("Coord Y: ",816, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-536, actualCoords[0],0);
		assertEquals("Coord Y: ",786, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-528, actualCoords[0],0);
		assertEquals("Coord Y: ",759, actualCoords[1],0);
	}
	
	

}
