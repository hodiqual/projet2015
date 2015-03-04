
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.trafic.ClickComponentVolListener;
import fr.iessa.vue.trafic.ComponentVol;

/** Classe Tableau de Bord.
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelTableauDeBord extends JPanel implements Observer {

	private Controleur _controleur;
	private JPanel _panelVols = new JPanel();
	public PanelTableauDeBord(Controleur controleur, Map<Vol, ComponentVol> volsADessiner) {
		super();
		
		// Configuration du Layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setFont(new Font("Sans", Font.BOLD, 8));
		
		// Configuration du Background
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		
		// Configuration du titre
		final JLabel _titre = new JLabel("TABLEAU DE BORD");
		_titre.setForeground(Color.BLUE);
		_titre.setFont(new Font("Sans", Font.BOLD, 12));
		_titre.setAlignmentX(CENTER_ALIGNMENT);
		
		// Ajout des différents éléments du Tableau de bord
		this.add(_titre);
		this.add(new PanelDensiteTrafic(controleur));
		
		//hodiqual
		this.add(new PanelFiltres(controleur, new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)  {
					Vol volSelectionne = (Vol)e.getItem();
					PanelAffichageVol affVol = new PanelAffichageVol(volsADessiner.get(volSelectionne), controleur);
					_panelVols.add(affVol);
					_panelVols.revalidate();
					_panelVols.repaint();
				}
			}
		} ) );

		_panelVols.setLayout(new BoxLayout(_panelVols, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(_panelVols);
		add(scrollPane);
		
		_controleur = controleur;
		
	}

	
	/** 
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override //hodiqual
	public void update(Observable o, Object arg) {
		Map<Vol, ComponentVol> volsADessiner = (Map<Vol, ComponentVol>)arg;
		
		for (ComponentVol cv : volsADessiner.values()) {
			cv.setClickListener( new ClickComponentVolListener() {
				
				@Override
				public void componentVolClicked(ComponentVol cv) {
					PanelAffichageVol affVol = new PanelAffichageVol(cv, _controleur);
					
					_panelVols.add(affVol);
					_panelVols.revalidate();
					_panelVols.repaint();
				}
			});
		}		
	}
}
