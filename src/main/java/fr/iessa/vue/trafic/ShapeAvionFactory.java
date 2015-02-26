package fr.iessa.vue.trafic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.iessa.vue.Ressources;

/**
 * Fabrique qui instancie les uniques dessins des avions.
 * @author hodiqual
 *
 */
public enum ShapeAvionFactory {
	HIGH_BLUE(Ressources.AVION_HIGH_BLUE)
,   MEDIUM_BLUE(Ressources.AVION_MEDIUM_BLUE)
,   LIGHT_BLUE(Ressources.AVION_LIGHT_BLUE)

,	HIGH_BLUEF(Ressources.AVION_HIGH_BLUEF)
,   MEDIUM_BLUEF(Ressources.AVION_MEDIUM_BLUEF)
,   LIGHT_BLUEF(Ressources.AVION_LIGHT_BLUEF)

,	HIGH_RED(Ressources.AVION_HIGH_RED)
,   MEDIUM_RED(Ressources.AVION_MEDIUM_RED)
,   LIGHT_RED(Ressources.AVION_LIGHT_RED)

,	HIGH_ORANGE(Ressources.AVION_HIGH_ORANGE)
,   MEDIUM_ORANGE(Ressources.AVION_MEDIUM_ORANGE)
,   LIGHT_ORANGE(Ressources.AVION_LIGHT_ORANGE)

,	HIGH_VERT(Ressources.AVION_HIGH_VERT)
,   MEDIUM_VERT(Ressources.AVION_MEDIUM_VERT)
,   LIGHT_VERT(Ressources.AVION_LIGHT_VERT)

,	HIGH_VIOLET(Ressources.AVION_HIGH_VIOLET)
,   MEDIUM_VIOLET(Ressources.AVION_MEDIUM_VIOLET)
,   LIGHT_VIOLET(Ressources.AVION_LIGHT_VIOLET)
;
	
	private BufferedImage[] _avionsParAngle;
	
	private ShapeAvionFactory(String ressource){
		_avionsParAngle = getShapesByColor(ressource);
	}
	
	private final static int _stepAngulaire = 5;
	
	private BufferedImage[] getShapesByColor(String resource) {
		BufferedImage imageOriginal = null;
		
		try {
			imageOriginal = ImageIO.read(Ressources.get(resource));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		int largeur = imageOriginal.getWidth();
		int hauteur = imageOriginal.getHeight();
		
		
		BufferedImage[] images = new BufferedImage[360/_stepAngulaire];
		images[0] = imageOriginal;
		
		for(int i=5; i<360 ;i+=_stepAngulaire){
			BufferedImage rotated = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB );
			Graphics2D g2 = rotated.createGraphics();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			
			g2.setTransform(AffineTransform.getRotateInstance(-Math.toRadians(i), largeur/2, hauteur/2));
			g2.drawImage(imageOriginal, 0,0,null);
			images[i/_stepAngulaire]=rotated;
		}		
		
		return images;
	}
	
	/**
	 * 
	 * @param angleEnDegre
	 * @return l'image correspondante du vol selon l'angle.
	 */
	public BufferedImage get( double angleEnDegre)  {
		int index = (int) Math.round((angleEnDegre/_stepAngulaire));
		return _avionsParAngle[Math.min(index,71)];
	}
	
	
}
