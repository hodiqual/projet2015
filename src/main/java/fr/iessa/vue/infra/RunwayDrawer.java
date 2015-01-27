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
			g2.drawLine((int)runway.get_extremite_x(0), (int)runway.get_extremite_y(0), (int)runway.get_extremite_x(1), (int)runway.get_extremite_y(1));
        	g2.draw(runway.get_runwayPointAPoint());
			
			g2.setColor(Color.WHITE);
        	// Dessine la ligne discontinue centrale
			float dash[] = { 40f };
			g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 20f, dash, 0.0f));
			g2.draw(runway.get_runwayPointAPoint());
		
        	// Dessine la ligne de bordures
			float dashbordure[] = { 100f,10f };
			g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 2f, dashbordure, 0.0f));

			g2.drawLine((int)runway.get_extremite_x(0), (int)runway.get_extremite_y(0)-20, (int)runway.get_extremite_x(1), (int)runway.get_extremite_y(1)-20);
			g2.drawLine((int)runway.get_extremite_x(0), (int)runway.get_extremite_y(0)+20, (int)runway.get_extremite_x(1), (int)runway.get_extremite_y(1)+20);
	
			// Dessine les bandes en seuil de piste
			float dash2[] = { 2f};
			g2.setStroke(new BasicStroke(20f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 2f, dash2, 0.0f));
			g2.drawLine((int)runway.get_extremite_x(0)+(int)Math.round(runway.getAngle(-Math.PI/2)), (int)runway.get_extremite_y(0)-18, (int)runway.get_extremite_x(0), (int)runway.get_extremite_y(0)+18);
			g2.drawLine((int)runway.get_extremite_x(1)+(int)Math.round(runway.getAngle(-Math.PI/2)), (int)runway.get_extremite_y(1)-18, (int)runway.get_extremite_x(1), (int)runway.get_extremite_y(1)+18);

			// Dessine les autres bandes
			

						g2.drawLine((int)runway.get_extremite_x(0)+150, (int)runway.get_extremite_y(0)-14, (int)runway.get_extremite_x(0)+150, (int)runway.get_extremite_y(0)-4);
						g2.drawLine((int)runway.get_extremite_x(0)+150, (int)runway.get_extremite_y(0)+14, (int)runway.get_extremite_x(0)+150, (int)runway.get_extremite_y(0)+4);
						g2.drawLine((int)runway.get_extremite_x(1)+150, (int)runway.get_extremite_y(1)-14, (int)runway.get_extremite_x(1)+150, (int)runway.get_extremite_y(1)-4);
						g2.drawLine((int)runway.get_extremite_x(1)+150, (int)runway.get_extremite_y(1)+14, (int)runway.get_extremite_x(1)+150, (int)runway.get_extremite_y(1)+4);

			
			// Dessine le QFU
			Font font = new Font("Helvetica", Font.PLAIN, 28);
			AffineTransform flip = AffineTransform.getScaleInstance(-1, 1);
			AffineTransform flip2 = AffineTransform.getScaleInstance(-1, 1);
			AffineTransform rotate = AffineTransform.getRotateInstance(runway.getAngle(Math.PI/2));
			AffineTransform rotate2 = AffineTransform.getRotateInstance(runway.getAngle(-Math.PI/2));
			AffineTransform fliprotate = new AffineTransform ();
			AffineTransform fliprotate2 = new AffineTransform ();
			fliprotate.concatenate(flip);
			flip.concatenate(rotate);
			Font fontfliprotate = font.deriveFont(flip);

			fliprotate2.concatenate(flip2);
			flip2.concatenate(rotate2);
			Font fontfliprotate2 = font.deriveFont(flip2);


		      // Dessine les QFU L	
		      if (Integer.parseInt(runway.get_QFUL().toString().substring(0, 2))>Integer.parseInt(runway.get_QFUR().toString().substring(0, 2))){	    	
		      GlyphVector vectLchiffre1 = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(0, 1));
		      GlyphVector vectLchiffre2 = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(1, 2));
		      GlyphVector vectLlettre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(2, 3));
			    g2.drawGlyphVector(vectLchiffre1, (float)runway.get_extremite_x(1)-40, (float)runway.get_extremite_y(1)-20);
			    g2.drawGlyphVector(vectLchiffre2, (float)runway.get_extremite_x(1)-40, (float)runway.get_extremite_y(1));
			    g2.drawGlyphVector(vectLlettre, (float)runway.get_extremite_x(1)-10, (float)runway.get_extremite_y(1)-10);  
		      }
		      else{
			      GlyphVector vectLchiffre1 = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(0, 1));
			      GlyphVector vectLchiffre2 = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(1, 2));
			      GlyphVector vectLlettre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(2, 3));
				    g2.drawGlyphVector(vectLchiffre1, (float)runway.get_extremite_x(0)+40, (float)runway.get_extremite_y(0)+25);
				    g2.drawGlyphVector(vectLchiffre2, (float)runway.get_extremite_x(0)+40, (float)runway.get_extremite_y(0)+5);
				    g2.drawGlyphVector(vectLlettre, (float)runway.get_extremite_x(0)+10, (float)runway.get_extremite_y(0)+10);   
		      }
		      
		      
			   // Dessine les QFU R	
		      if (Integer.parseInt(runway.get_QFUL().toString().substring(0, 2))<Integer.parseInt(runway.get_QFUR().toString().substring(0, 2))){
		      GlyphVector vectRchiffre1 = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(0, 1));
		      GlyphVector vectRchiffre2 = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(1, 2));
		      GlyphVector vectRlettre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(2, 3));
			    g2.drawGlyphVector(vectRchiffre1, (float)runway.get_extremite_x(1)-40, (float)runway.get_extremite_y(1)-20);
			    g2.drawGlyphVector(vectRchiffre2, (float)runway.get_extremite_x(1)-40, (float)runway.get_extremite_y(1));
			    g2.drawGlyphVector(vectRlettre, (float)runway.get_extremite_x(1)-10, (float)runway.get_extremite_y(1)-10);}
			
		      else{
			      GlyphVector vectRchiffre1 = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(0, 1));
			      GlyphVector vectRchiffre2 = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(1, 2));
			      GlyphVector vectRlettre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(2, 3)); 
				    g2.drawGlyphVector(vectRchiffre1, (float)runway.get_extremite_x(0)+40, (float)runway.get_extremite_y(0)+25);
				    g2.drawGlyphVector(vectRchiffre2, (float)runway.get_extremite_x(0)+40, (float)runway.get_extremite_y(0));
				    g2.drawGlyphVector(vectRlettre, (float)runway.get_extremite_x(0)+10, (float)runway.get_extremite_y(0)+15);
		      }
		      
		    
		    g2.setStroke(oldStyle);		


		}
        

	}
}
