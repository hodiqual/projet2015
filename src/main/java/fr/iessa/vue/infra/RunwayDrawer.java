/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

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
        	g2.setColor(Color.DARK_GRAY);
        	g2.setStroke(new BasicStroke(45.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.CAP_SQUARE));
			g2.draw(runway.get_runwayPointAPoint());
			g2.setColor(Color.WHITE);
			 float dash[] = { 40f };
			g2.setStroke(new BasicStroke(1.7f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 20f, dash, 0.0f));
			g2.draw(runway.get_runwayPointAPoint());
			g2.setColor(Color.RED);
			Font font = new Font("Arial", Font.PLAIN, 36);
			g2.setFont(font);
			g2.drawString(runway.get_QFUL().toString(), (float)runway.get_extremite_x(1),(float)runway.get_extremite_y(1));
			
		}
        
        g2.setStroke(oldStyle);
	}

}
