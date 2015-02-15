/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Runway;

/**
 * Classe qui dessine les pistes de l'aeroport avec marquages au sol et numŽros des pistes.
 * @author duvernal
 *
 */
public class RunwayDrawer {

	
	/** 
	 * Dessine les runways de l'aeroport
	 * pistes + marquages au sol + numŽros des pistes
	 */
	public void dessine(Aeroport aeroport, Graphics2D g2 )
	{
		// Stocke l'ancien style en mŽmoire
		Stroke oldStyle = g2.getStroke();
      
        for (Runway runway : aeroport.get_runways()) {
        	// Variables locales
            /** L'angle de la piste */
        	int anglepiste =(int)Math.round(runway.getAngle(-Math.PI/2));
            /** La largeur de la piste selon une constante dŽfinie.
             * * @see fr.iessa.vue.infra.PlateformeStd */
        	int largeurpiste=(int)(PlateformeStd.RUNWAY.largeur());
            /** La coordonŽe X de la piste 0 */
        	int X_extremite0 = (int)runway.get_extremite_x(0);
            /** La coordonŽe X de la piste 1 */
        	int X_extremite1 = (int)runway.get_extremite_x(1);
            /** La coordonŽe Y de la piste 0 */
        	int Y_extremite0 = (int)runway.get_extremite_y(0);
            /** La coordonŽe Y de la piste 1 */
        	int Y_extremite1 = (int)runway.get_extremite_y(1);
        	
        	
        	
        	// Dessine la piste
        	g2.setColor(PlateformeStd.RUNWAY.couleur());
        	g2.setStroke(new BasicStroke(PlateformeStd.RUNWAY.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.CAP_SQUARE));
			g2.drawLine(X_extremite0, Y_extremite0, X_extremite1, Y_extremite1);
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

			g2.drawLine(X_extremite0, Y_extremite0-largeurpiste/2+2, X_extremite1, Y_extremite1-largeurpiste/2+2);
			g2.drawLine(X_extremite0, Y_extremite0+largeurpiste/2-2, X_extremite1, Y_extremite1+largeurpiste/2-2);
	
			// Dessine les bandes en seuil de piste
			float dash2[] = { 2f};
			g2.setStroke(new BasicStroke(20f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 2f, dash2, 0.0f));
			g2.drawLine(X_extremite0+anglepiste, Y_extremite0-largeurpiste/2+4, X_extremite0, Y_extremite0+largeurpiste/2-4);
			g2.drawLine(X_extremite1+anglepiste, Y_extremite1-largeurpiste/2+4, X_extremite1, Y_extremite1+largeurpiste/2-4);


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
		      GlyphVector vectLnombre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(0, 2));
		      GlyphVector vectLlettre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(2, 3));
			    g2.drawGlyphVector(vectLnombre, (float)X_extremite1-50, (float)Y_extremite1-15-anglepiste);
			    g2.drawGlyphVector(vectLlettre, (float)X_extremite1-20, (float)Y_extremite1-5-anglepiste);  
		      }
		      else{
			      GlyphVector vectLnombre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(0, 2));
			     GlyphVector vectLlettre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUL().toString().substring(2, 3));
				    g2.drawGlyphVector(vectLnombre, (float)X_extremite0+50, (float)Y_extremite0+20+anglepiste);
				    g2.drawGlyphVector(vectLlettre, (float)X_extremite0+20, (float)Y_extremite0+5+anglepiste);   
		      }
		      
		      
			   // Dessine les QFU R	
		      if (Integer.parseInt(runway.get_QFUL().toString().substring(0, 2))<Integer.parseInt(runway.get_QFUR().toString().substring(0, 2))){
		      GlyphVector vectRnombre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(0, 2));
		      GlyphVector vectRlettre = fontfliprotate.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(2, 3));
			    g2.drawGlyphVector(vectRnombre, (float)X_extremite1-50, (float)Y_extremite1-15-anglepiste);
			    g2.drawGlyphVector(vectRlettre, (float)X_extremite1-20, (float)Y_extremite1-5-anglepiste);}
			
		      else{
			      GlyphVector vectRnombre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(0, 2));
			      GlyphVector vectRlettre = fontfliprotate2.createGlyphVector(g2.getFontRenderContext(), runway.get_QFUR().toString().substring(2, 3)); 
				    g2.drawGlyphVector(vectRnombre, (float)X_extremite0+50, (float)Y_extremite0+20+anglepiste);
				    g2.drawGlyphVector(vectRlettre, (float)X_extremite0+20, (float)Y_extremite0+10+anglepiste);
		      }
		      
		    // RŽgŽnre l'ancien style
		    g2.setStroke(oldStyle);		


		}
        

	}
}
