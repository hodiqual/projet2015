/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import fr.iessa.controleur.LibereMemoire;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;

/**
 * @author hodiqual
 *
 */
public class LignesDrawer {
	
	public void dessine(Aeroport aeroport, Graphics2D g2 )
	{
		
		Stroke oldStyle = g2.getStroke();
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(8.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_taxiway()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}
        
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_lignes()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_pushbacks()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}
        
        g2.setStroke(oldStyle);
	}

}
