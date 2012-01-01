/**
 * 
 */
package fr.iessa.vue;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.infra.PanelPlateforme;
import fr.iessa.vue.trafic.ComponentVol;
import fr.iessa.vue.trafic.PanelTrafic;

/**
 * @author hodiqual
 * Superpose le panel qui gere la carte, le panel qui gere le trafic, le panel des controles.
 */
public class PanelPrincipalMultiCouches extends JPanel {

	/** Permet de gerer plusieurs plans sur un meme panel*/
	private JLayeredPane _gestionPlans;
	
	public PanelPrincipalMultiCouches(Controleur controleur, boolean avecControle)  
	{
		setLayout(new GridLayout(1, 1));	
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);
		
		_gestionPlans = new JLayeredPane();
		
		OverlayLayout layout = new OverlayLayout(_gestionPlans);
		_gestionPlans.setLayout(layout);
		/*_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Veuillez charger un fichier de plateforme ..."));*/
		
		//Gestion de la plateforme
		Echelle echelle = new Echelle();
		PanelPlateforme plateformePanel = new PanelPlateforme(controleur, echelle);
		final ChargeEnCoursLayerUI layerUI = new ChargeEnCoursLayerUI();
		plateformePanel.setChargeEnCoursLayerUI(layerUI);
		plateformePanel.setAlignmentX(0.0f);
		plateformePanel.setAlignmentY(0.0f);
		JLayer<JPanel> plateformLayered = new JLayer<JPanel>(plateformePanel, layerUI);
		_gestionPlans.add(plateformLayered, JLayeredPane.FRAME_CONTENT_LAYER);
		
		//Gestion du trafic
		PanelTrafic traficPanel = new  PanelTrafic(controleur, echelle);
		final ChargeEnCoursLayerUI layerUI2 = new ChargeEnCoursLayerUI();
		traficPanel.setChargeEnCoursLayerUI(layerUI2);
		JLayer<JPanel> traficLayered = new JLayer<JPanel>(traficPanel, layerUI2);
		traficLayered.setAlignmentX(0.0f);
		traficLayered.setAlignmentY(0.0f);
		_gestionPlans.add(traficLayered,JLayeredPane.DEFAULT_LAYER);
		
		if(avecControle) {
			//Gestion des controles Lecteur et Tableau de Bord
			PanelDesControles controles = new PanelDesControles(controleur, traficPanel, echelle);
			controles.setAlignmentX(0.0f);
			controles.setAlignmentY(0.0f);
			_gestionPlans.add(controles,JLayeredPane.PALETTE_LAYER);
		}else{
			//Affichage horloge seule
			JPanel panelHorloge = new JPanel();
			panelHorloge.setOpaque(false);
			panelHorloge.setLayout(new FlowLayout(FlowLayout.RIGHT));
			panelHorloge.add(new LabelHorloge(controleur));
			panelHorloge.setAlignmentX(0.0f);
			panelHorloge.setAlignmentY(0.0f);
			_gestionPlans.add(panelHorloge,JLayeredPane.PALETTE_LAYER);
		}
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);	
	}
	
	
}
