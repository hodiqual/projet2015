
package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Map;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Point;

/** Classe qui permet de dessiner tous les points
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PointsDrawer {

	public void dessine(Aeroport aeroport, Graphics2D g2 )
	{
        Map<String, Point> _points = aeroport.get_points();
        
        for (Map.Entry<String, Point> entry : _points.entrySet()) {
        	Point point = entry.getValue();
        
        	switch (point.get_type()) {
			case STAND :
				g2.setColor(Color.ORANGE);
				g2.fillRect((int)point.getX(), (int)point.getY(), 10, 10);
				break;
			
			case DEICING :
				g2.setColor(Color.BLUE);
				g2.fillRect((int)point.getX(), (int)point.getY(), 10, 10);
				break;
				
			case RUNWAY_POINT :
				g2.setColor(Color.RED);
				g2.fillRect((int)point.getX(), (int)point.getY(), 10, 10);
				break;

			default:
				break;
        	}	
        	
		}
        
	}
	
}
