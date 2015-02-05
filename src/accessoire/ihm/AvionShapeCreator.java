package ihm;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.iessa.controleur.LibereMemoire;

public class AvionShapeCreator {

	public static void main(String[] args) throws IOException {
		
		final String prefix = "/Users/sb00by/Desktop/Projet/Shape/";
		final String type = "HIGH/";
		final String couleur = "BLEU/";
		
		final String globalUrl = prefix+type+couleur;
		
		
		
		BufferedImage imageOriginal = ImageIO.read(new File( globalUrl + "0.png"));
		int largeur = imageOriginal.getWidth();
		int hauteur = imageOriginal.getHeight();
		

		BufferedImage[] images = new BufferedImage[360/5];
		images[0] = imageOriginal;
		
		for(int i=5; i<360 ;i+=5){
			BufferedImage rotated = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB );
			Graphics2D g2 = rotated.createGraphics();

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			
			g2.setTransform(AffineTransform.getRotateInstance(-Math.toRadians(i), largeur/2, hauteur/2));
			g2.drawImage(imageOriginal, 0,0,null);
			images[i/5]=rotated;
			File outputfile = new File( globalUrl + i/5 +".png");
		    ImageIO.write(rotated, "png", outputfile);
		}
		
		LibereMemoire.free();
		LibereMemoire.controleMemoire();
		

	}

}
