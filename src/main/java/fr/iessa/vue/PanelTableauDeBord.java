
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.Vol;

/** Classe Tableau de Bord.
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelTableauDeBord extends JPanel {
	/** Attributs */
	private final JLabel _titre = new JLabel("TABLEAU DE BORD");
	private JPanel _panelVols = new JPanel();
	
	public PanelTableauDeBord(Controleur controleur) {
		super();
		
		// Configuration du Layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setFont(new Font("Sans", Font.BOLD, 8));
		
		// Configuration du Background
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		
		// Configuration du titre
		_titre.setForeground(Color.BLUE);
		_titre.setFont(new Font("Sans", Font.BOLD, 12));
		_titre.setAlignmentX(CENTER_ALIGNMENT);
		
		// Ajout des différents éléments du Tableau de bord
		this.add(_titre);
		this.add(new PanelFiltres(controleur, new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)  {
					PanelAffichageVol affVol = new PanelAffichageVol((Vol)e.getItem());
					_panelVols.add(affVol);
					_panelVols.revalidate();
					_panelVols.repaint();
				}
			}
		} ) );

		_panelVols.setLayout(new BoxLayout(_panelVols, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(_panelVols);
		add(scrollPane);
		
	}
}
