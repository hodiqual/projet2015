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
		//1. Sauvegarde du stroke courant
		Stroke oldStyle = g2.getStroke();
		
		//2. Dessine l'alsphate des taxiways
        g2.setColor(PlateformeStd.TAXIWAY.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.TAXIWAY.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_taxiway()) {
			g2.draw(ligne.get_lignePointAPoint());
		}
		
		//3. Dessine l'alsphate des lignes
        g2.setColor(PlateformeStd.LIGNES.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.LIGNES.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_lignes()) {
			g2.draw(ligne.get_lignePointAPoint());
		}
		
		//4. Dessine l'alsphate des pushbacks
        g2.setColor(PlateformeStd.PUSHBACK.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.PUSHBACK.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_pushbacks()) {
			g2.draw(ligne.get_lignePointAPoint());
		}
        
        //5. Reset du stroke avec le stroke initial.
        g2.setStroke(oldStyle);
	}
	
	public void dessineMarquageAuSol(Aeroport aeroport, Graphics2D g2)
	{	
		//1. Sauvegarde du stroke courant
		Stroke oldStyle = g2.getStroke();
		
		//2. Dessine les marquages au sol des taxiways
			//2.1 la bordure de la ligne centrale taxiway
        g2.setColor(PlateformeStd.TAXIWAY_MARQUE_SOL_BORDURE.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.TAXIWAY_MARQUE_SOL_BORDURE.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne taxiway : aeroport.get_taxiway()) {			
			g2.draw(taxiway.get_lignePointAPoint());	
		}
        	
			//2.2 la ligne centrale taxiway
        g2.setColor(PlateformeStd.TAXIWAY_MARQUE_SOL.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.TAXIWAY_MARQUE_SOL.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne taxiway : aeroport.get_taxiway()) {			
			g2.draw(taxiway.get_lignePointAPoint());	
		}
        
        //3. Dessine les marquages au sol des lignes
			//3.1 la bordure de la ligne centrale des lignes
        g2.setColor(PlateformeStd.LIGNES_MARQUE_SOL_BORDURE.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.LIGNES_MARQUE_SOL_BORDURE.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_lignes()) {		
			g2.draw(ligne.get_lignePointAPoint());		
		}
			//3.2 la ligne centrale des lignes
        g2.setColor(PlateformeStd.LIGNES_MARQUE_SOL.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.LIGNES_MARQUE_SOL.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND));
        for (Ligne ligne : aeroport.get_lignes()) {		
			g2.draw(ligne.get_lignePointAPoint());		
		}  
                
        final float motifPointille[] = {10.0f};
        
        //4. Dessine les marquages au sol des pushbacks
			//4.1 la bordure de la ligne centrale des lignes
        g2.setColor(PlateformeStd.PUSHBACK_MARQUE_SOL_BORDURE.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.PUSHBACK_MARQUE_SOL_BORDURE.largeur(), BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_ROUND, 1.0f, motifPointille, 0f));
        for (Ligne ligne : aeroport.get_pushbacks()) {		
			g2.draw(ligne.get_lignePointAPoint());		
		}
			//4.2 la ligne centrale des pushbacks
        g2.setColor(PlateformeStd.PUSHBACK_MARQUE_SOL.couleur());
        g2.setStroke(new BasicStroke(PlateformeStd.PUSHBACK_MARQUE_SOL.largeur(), BasicStroke.CAP_SQUARE,
        		BasicStroke.JOIN_ROUND, 1.0f, motifPointille, 0f));
        for (Ligne ligne : aeroport.get_pushbacks()) {		
			g2.draw(ligne.get_lignePointAPoint());		
		}
              
        //Reset du stroke avec le stroke initial.
        g2.setStroke(oldStyle);
	}

}
