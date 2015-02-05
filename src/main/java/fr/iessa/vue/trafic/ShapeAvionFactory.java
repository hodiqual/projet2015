package fr.iessa.vue.trafic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.iessa.vue.Ressources;


public enum ShapeAvionFactory {
	HIGH_BLUE(Ressources.AVION_HIGH_BLUE)
,   MEDIUM_BLUE(Ressources.AVION_MEDIUM_BLUE)
,   LIGHT_BLUE(Ressources.AVION_LIGHT_BLUE) ;
	
	private BufferedImage[] _avionsParAngle;
	
	private ShapeAvionFactory(String ressource){
		_avionsParAngle = getShapesByColor(ressource);
		
		System.out.println("ShapeAvionFactory: " +  _avionsParAngle.length);
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
	
	public BufferedImage get( double angleEnDegre)  {
		int index = (int) Math.round((angleEnDegre/_stepAngulaire));
		return _avionsParAngle[Math.min(index,71)];
	}
	
	
}
