/**
 * 
 */
package fr.iessa.metier.trafic;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.iessa.controleur.Controleur;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 *
 */
public class CollisionTest {

	/**
	 * Test method for {@link fr.iessa.metier.trafic.Collision#Collision(fr.iessa.metier.Instant, java.awt.Point, java.util.List)}.
	 */
	@Test
	public void testCollision() {
		final ArrayList<Vol> volsImpliques = new ArrayList<Vol>();
		final Vol vol1 = new Vol(TypeVol.ARR, "AFPROUT", Categorie.HIGH, InstantFabrique.get(12000));
		final Vol vol2 = new Vol(TypeVol.ARR, "AFPROUT", Categorie.HIGH, InstantFabrique.get(12000));
		final Vol vol3 = new Vol(TypeVol.ARR, "AFPROUT", Categorie.HIGH, InstantFabrique.get(12000));
		
		volsImpliques.add(vol1);
		volsImpliques.add(vol2);
		volsImpliques.add(vol3);
		
		Collision collisionATester= new Collision(InstantFabrique.get(12000), new Point(-10,3000), volsImpliques);
	
		assertEquals(InstantFabrique.get(12000), collisionATester.getInstant());
		assertEquals(new Point(-10,3000), collisionATester.getPointImpact());
		
		assertTrue(collisionATester.getVolsImpliques().contains(vol1));
		assertTrue(collisionATester.getVolsImpliques().contains(vol2));
		assertTrue(collisionATester.getVolsImpliques().contains(vol3));
	}
	
	@Test
	public void testCollisionTrouvees() {
		TraficDao loader = new TraficDao();
		Trafic trafic = loader.charger("TestTraficCrash.txt");
		
		List<Collision> collisions = trafic.getCollisions();
		
		assertEquals(27, collisions.size(),0);
	}
	
	@Test
	public void testCollisionImpliquant2Avions() {
		TraficDao loader = new TraficDao();
		Trafic trafic = loader.charger("TestTrafic1Collision2Avions.txt");
		
		List<Collision> collisions = trafic.getCollisions();
		
		assertEquals(1, collisions.size(),0);
		assertEquals(2, collisions.get(0).getVolsImpliques().size(),0);
		assertEquals(1450, collisions.get(0).getInstant().getSeconds(),0);

		assertEquals("SRR6316", collisions.get(0).getVolsImpliques().get(0).getId());
		assertEquals("BCS1748", collisions.get(0).getVolsImpliques().get(1).getId());
	}
	
	@Test
	public void testCollisionImpliquant3Avions() {
		TraficDao loader = new TraficDao();
		Trafic trafic = loader.charger("TestTrafic1Collision3Avions.txt");
		
		List<Collision> collisions = trafic.getCollisions();
		
		assertEquals(1, collisions.size(),0);
		assertEquals(3, collisions.get(0).getVolsImpliques().size(),0);
		assertEquals(1450, collisions.get(0).getInstant().getSeconds(),0);
		assertEquals(new Point2D.Double(-938,-1128), collisions.get(0).getPointImpact());

		assertEquals("SRR6316", collisions.get(0).getVolsImpliques().get(0).getId());
		assertEquals("BCS1748", collisions.get(0).getVolsImpliques().get(1).getId());
		assertEquals("FDX5182", collisions.get(0).getVolsImpliques().get(2).getId());
	}
	
	

}
