package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.iessa.metier.infra.Point;
import fr.iessa.metier.type.TypePoint;

/** Pour tester la classe PointDAO
 * @author THOMAS Raimana
 * @version 1.0 
 */
public class PointDAOTest {

	/**
	 * Test method for {@link fr.iessa.dao.infra.PointDAO#charger(java.lang.String)}.
	 */
	@Test
	public void testCharger1() {
		PointDAO pd = new PointDAO();
		Point pt = pd.charger("P K05 0 2741,-243");
		
		assertEquals("K05", pt.get_nom());
		assertEquals(TypePoint.STAND, pt.get_type());
		assertEquals(2741, pt.getX(), 0);
		assertEquals(-243, pt.getY(), 0);
	}
	
	@Test
	public void testCharger2() {
		PointDAO pd = new PointDAO();
		Point pt = pd.charger("P K05 1 2741,-243");
		
		assertEquals("K05", pt.get_nom());
		assertEquals(TypePoint.DEICING, pt.get_type());
		assertEquals(2741, pt.getX(), 0);
		assertEquals(-243, pt.getY(), 0);
	}
	
	@Test
	public void testCharger3() {
		PointDAO pd = new PointDAO();
		Point pt = pd.charger("P Z8 2 912,1880");
		
		assertEquals("Z8", pt.get_nom());
		assertEquals(TypePoint.RUNWAY_POINT, pt.get_type());
		assertEquals(912, pt.getX(), 0);
		assertEquals(1880, pt.getY(), 0);
	}



}
