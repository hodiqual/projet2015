/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Runway;

/**
 * @author duvernal
 *
 */
public class RunwayDrawer {
	
	public void dessine(Aeroport aeroport, Graphics2D g2 )
	{
        g2.setColor(Color.BLACK);
        for (Runway runway : aeroport.get_runways()) {
			g2.draw(runway.get_runwayPointAPoint());		
		}

	}

}
