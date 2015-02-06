
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/** Classe Tableau de Bord.
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelTableauDeBord extends JPanel implements PropertyChangeListener {

	// ***************** A SURPPRIMER **************************
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = Color.RED;
	// ***************** FIN A SURPPRIMER **************************
	
	private final JLabel _titre = new JLabel("TABLEAU DE BORD");
	private PanelAffichageVol _affichageVol = new PanelAffichageVol();
	
	public PanelTableauDeBord(Controleur controleur) {
		super();
		setOpaque(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(BG_COLOR);
		
		_titre.setForeground(FG_COLOR);
		_titre.setFont(new Font("Sans", Font.BOLD, 10));
		_titre.setVerticalAlignment(SwingConstants.CENTER);
		_titre.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(_titre);
		add(new PanelFiltres(controleur));
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
