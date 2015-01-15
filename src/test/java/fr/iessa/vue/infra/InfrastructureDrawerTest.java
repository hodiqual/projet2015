/**
 * 
 */
package fr.iessa.vue.infra;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import org.junit.Test;

import fr.iessa.dao.infra.InfrastructureDAO;
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
		Aeroport aeroport = InfrastructureDAO.charger("lfpg.txt");
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthS = (int) screenSize.getWidth();
        int heightS = (int) screenSize.getHeight();
		BufferedImage image = new BufferedImage(widthS, heightS, BufferedImage.TYPE_INT_RGB);
		

		InfrastructureDrawer drawer = new InfrastructureDrawer();
		drawer.dessineAeroport(aeroport, image.createGraphics(), widthS, heightS);
		
		assertEquals(-3879, drawer._minReelX,0);
		assertEquals(4571, drawer._maxReelX,0);
		assertEquals(-2219, drawer._minReelY,0);
		assertEquals(1880, drawer._maxReelY,0);
		
		double[] expectedTransAffine = { 0.151479289940828,               0.0, 0.0,
										-0.249817028543547, 587.5881656804734, 469.6560136618688 };
		double[] actualTransAffine = new double[6];
		drawer._transfoAffine.getMatrix(actualTransAffine);
		assertArrayEquals(expectedTransAffine, actualTransAffine, 0.000001);
	}

}
