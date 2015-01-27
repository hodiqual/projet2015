/**
 * 
 */
package fr.iessa.vue.infra;

import static org.junit.Assert.*;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.junit.Test;

import aurelienribon.tweenengine.Tween;
import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.infra.Aeroport;

/**
 * @author hodiqual
 *
 */
public class InfrastructureDrawerTest {

	/**
	 * Test method for {@link fr.iessa.vue.infra.InfrastructureDrawer#dessineAeroport(fr.iessa.metier.infra.Aeroport, java.awt.Graphics2D, double, double)}.
	 */
	@Test
	public void testDessineAeroport() {
		
		Tween.registerAccessor(TraficDao.class, null);
		
		Aeroport aeroport = InfrastructureDAO.charger("lfpg.txt");
		
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthS = 1280;
        int heightS = 1024;
		BufferedImage image = new BufferedImage(widthS, heightS, BufferedImage.TYPE_INT_RGB);
		

		InfrastructureDrawer drawer = new InfrastructureDrawer();
		drawer.dessineAeroport(aeroport, image.createGraphics(), widthS, heightS, new AffineTransform());
		
		assertEquals(-3879, drawer._minReelX,0);
		assertEquals(4571, drawer._maxReelX,0);
		assertEquals(-2219, drawer._minReelY,0);
		assertEquals(1880, drawer._maxReelY,0);
		
		double[] expectedTransAffine = { 0.14797687861271677,               0.0, 0.0,
										-0.2381949290532682, 588.8, 471.62595952547105 };
		double[] actualTransAffine = new double[6];
		drawer._transfoAffine.getMatrix(actualTransAffine);
		assertArrayEquals(expectedTransAffine, actualTransAffine, 0.000001);
	}

}
