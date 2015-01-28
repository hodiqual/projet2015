/**
 * 
 */
package fr.iessa.vue;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import fr.iessa.controleur.Controleur;

/**
 * @author hodiqual
 * Superpose le panel qui gere la carte et le panel qui gere le trafic.
 */
public class PanelPrincipalMultiCouches extends JPanel {

	/** Permet de gerer plusieurs plans sur un meme panel*/
	private JLayeredPane _gestionPlans;
	
	/** Controleur de notre pattern MVC */
	private Controleur _controleur;
	
	/** Panel qui gere l'infrastructure, ce panel sera en arriere plan */
	private InfrastructurePanel _infrastructurePanel;
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
        return true;
      }
	
	public PanelPrincipalMultiCouches(Controleur controleur)
	{
		setLayout(new GridLayout(1, 1));
		
		_gestionPlans = new JLayeredPane() {
		public boolean isOptimizedDrawingEnabled() {
	        return true;
	      }
		};
		
		OverlayLayout layout = new OverlayLayout(_gestionPlans);
		_gestionPlans.setLayout(layout);
		_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Click and Go"));
		
		//Gestion de l'infrastructure
		this._controleur = controleur;
		
		_infrastructurePanel = new InfrastructurePanel(_controleur);
		final ChargeEnCoursLayerUI layerUI = new ChargeEnCoursLayerUI();
		_infrastructurePanel.setChargeEnCoursLayerUI(layerUI);
		JLayer<JPanel> jlayer = new JLayer<JPanel>(_infrastructurePanel, layerUI);

		_infrastructurePanel.setAlignmentX(0.0f);
		_infrastructurePanel.setAlignmentY(0.0f);
		
		_gestionPlans.add(jlayer, -3000);
		
		//Gestion du trafic
		TraficPanel traficPanel = new  TraficPanel();
		traficPanel.setAlignmentX(0.0f);
		traficPanel.setAlignmentY(0.0f);
		_gestionPlans.add(traficPanel,0);
		
		//Gestion des controles Rejeu et Tableau de Bord
		PanelDesControles controles = new PanelDesControles();
		controles.setAlignmentX(0.0f);
		controles.setAlignmentY(0.0f);
		_gestionPlans.add(controles,1);
		
		/*
		 * IL Y A AUSSI POUR DISPATCHER LES EVENTS
		 * http://stackoverflow.com/questions/21500162/jlayeredpane-how-to-check-if-component-has-another-drawn-under-it
		 */
		
		//Gestion du panel de controle
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);	
	}
	
	
}
