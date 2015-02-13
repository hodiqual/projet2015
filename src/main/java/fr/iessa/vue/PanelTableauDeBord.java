
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.iessa.controleur.ModeleEvent;

/** Classe Tableau de Bord.
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelTableauDeBord extends JPanel implements PropertyChangeListener {
	/** Attributs */
	private final JLabel _titre = new JLabel("TABLEAU DE BORD");
	private PanelAffichageVol _affichageVol = new PanelAffichageVol();
	
	/** Constructeur */
	public PanelTableauDeBord() {
		super();
		
		// Configuration du Layout
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		// Configuration du Background
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		
		// Configuration du titre
		_titre.setForeground(Color.BLUE);
		_titre.setFont(new Font("Sans", Font.BOLD, 12));
		//_titre.setAlignmentX(Component.CENTER_ALIGNMENT);
		_titre.setVerticalAlignment(SwingConstants.CENTER);
		//_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Ajout des différents éléments du Tableau de bord
		add(_titre);
		add(_affichageVol);
		
	}
	
	/** Actions à realiser lors de la mise à jour demandée par le contrôleur */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case UPDATE_INSTANT:

				break;

			default:
				break;
		}
		
	}
	

}
