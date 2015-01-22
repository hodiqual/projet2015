/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Runway;

/**
 * @author duvernal
 *
 */
public class RunwayDrawer {

    private Graphics2D g2d;
	
	public void dessine(Aeroport aeroport, Graphics2D g2 )
	{
        //g2.setColor(Color.BLACK);
		Stroke oldStyle = g2.getStroke();



        
       
        
        
        for (Runway runway : aeroport.get_runways()) {
        	
        	// Dessine la piste
        	g2.setColor(Color.DARK_GRAY);
        	g2.setStroke(new BasicStroke(45.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.CAP_SQUARE));
			g2.draw(runway.get_runwayPointAPoint());
			
			// Dessine la ligne discontinue centrale
			g2.setColor(Color.WHITE);
			float dash[] = { 40f };
			g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 20f, dash, 0.0f));
			g2.draw(runway.get_runwayPointAPoint());
			
			
			
			
			
			// Dessine le QFU
			g2.setColor(Color.GREEN);
			Font font = new Font("Helvetica", Font.PLAIN, 36);
			AffineTransform flip = AffineTransform.getScaleInstance(-1, 1);
			AffineTransform rotate = AffineTransform.getRotateInstance(-Math.PI/2);
			AffineTransform fliprotate = new AffineTransform ();
			fliprotate.concatenate(flip);
			flip.concatenate(rotate);
			
			Font fontfliprotate = font.deriveFont(flip);
			
			//g2.setFont(font);
			//g2.drawString(runway.get_QFUL().toString(), (float)runway.get_extremite_x(1),(float)runway.get_extremite_y(1));

		   
		      GlyphVector vect = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString());
		     // Shape shape = vect.getOutline(0f, (float) -vect.getVisualBounds().getY());

		    g2.drawGlyphVector(vect, (float)runway.get_extremite_x(1), (float)runway.get_extremite_y(1));
		    //g2.drawGlyphVector(vect, 0, 0);
			
		    //g2.draw(shape);
		    g2.setStroke(oldStyle);		


		}
        

	}
}
