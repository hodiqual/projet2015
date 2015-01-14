/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics2D;

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
        g2.setColor(Color.YELLOW);
        for (Ligne ligne : aeroport.get_lignes()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}
        LibereMemoire.controleMemoire();
        g2.setColor(Color.GREEN);
        for (Ligne ligne : aeroport.get_taxiway()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}
 
        LibereMemoire.controleMemoire();
        g2.setColor(Color.RED);
        for (Ligne ligne : aeroport.get_pushbacks()) {
			g2.draw(ligne.get_lignePointAPoint());		
		}
	}

}
