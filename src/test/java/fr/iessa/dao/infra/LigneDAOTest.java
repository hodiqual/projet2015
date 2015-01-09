package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import java.awt.geom.PathIterator;

import org.junit.Test;

import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

public class LigneDAOTest {

	//l'objet a tester
	private LigneDAO _ligneDAO  = new LigneDAO();

	@Test
	public void testChargerLigne1()
	{
		String ligneDuFichierTexte = "L _ 10 H D -854,-1341;-959,-1466";
		Ligne actual =  _ligneDAO.charger(ligneDuFichierTexte);
		
		assertEquals(10, actual.get_vitesseDeRoulage());
		assertSame(Categorie.HIGH, actual.get_categorie());
		assertEquals(Direction.DOUBLE, actual.get_direction());
		
		//Test les points inseres
		PathIterator iterator = actual.get_lignePointAPoint().getPathIterator(null);
		float[] actualCoords = new float[2];
		assertEquals( PathIterator.SEG_MOVETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-854, actualCoords[0],0);
		assertEquals("Coord Y: ",-1341, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",-959, actualCoords[0],0);
		assertEquals("Coord Y: ",-1466, actualCoords[1],0);
	}
	
	@Test
	public void testChargerLigne2()
	{
		String ligneDuFichierTexte = "L _ 3 M S 2530,-702;2371,-734;2362,-738";
		Ligne actual =  _ligneDAO.charger(ligneDuFichierTexte);
		
		assertEquals(3, actual.get_vitesseDeRoulage());
		assertSame(Categorie.MEDIUM, actual.get_categorie());
		assertEquals(Direction.SINGLE, actual.get_direction());
		
		//Test les points inseres
		PathIterator iterator = actual.get_lignePointAPoint().getPathIterator(null);
		float[] actualCoords = new float[2];
		assertEquals( PathIterator.SEG_MOVETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",2530, actualCoords[0],0);
		assertEquals("Coord Y: ",-702, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",2371, actualCoords[0],0);
		assertEquals("Coord Y: ",-734, actualCoords[1],0);
		
		iterator.next();
		assertEquals( PathIterator.SEG_LINETO, iterator.currentSegment(actualCoords));
		assertEquals("Coord X: ",2362, actualCoords[0],0);
		assertEquals("Coord Y: ",-738, actualCoords[1],0);	
	}

}
