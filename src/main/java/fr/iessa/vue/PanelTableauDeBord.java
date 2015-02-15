
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/** Classe Tableau de Bord.
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelTableauDeBord extends JPanel implements PropertyChangeListener {
	/** Attributs */
	private final JLabel _titre = new JLabel("TABLEAU DE BORD");
	private PanelAffichageVol _affichageVol = new PanelAffichageVol();
	private PanelAffichageVol _affichageVol1 = new PanelAffichageVol();
	private JScrollPane scrollPane;
	
	/** Constructeur */
	public PanelTableauDeBord(Controleur controleur) {
		super();
		
		JPanel _panelTableauDeBord = new JPanel();
		
		// Configuration du Layout
		_panelTableauDeBord.setLayout(new BoxLayout(_panelTableauDeBord, BoxLayout.Y_AXIS));
		
		// Configuration du Background
		_panelTableauDeBord.setOpaque(true);
		_panelTableauDeBord.setBackground(Color.LIGHT_GRAY);
		
		// Configuration du titre
		_titre.setForeground(Color.BLUE);
		_titre.setFont(new Font("Sans", Font.BOLD, 12));
		
		// Ajout des différents éléments du Tableau de bord
		_panelTableauDeBord.add(_titre);
		_panelTableauDeBord.add(_affichageVol);
		_panelTableauDeBord.add(_affichageVol1);
		
		scrollPane = new JScrollPane(_panelTableauDeBord);
		add(scrollPane);
		
	}
	
	//public JScrollPane getScrollPane() {
		//return scrollPane;
	//}
	
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
