/**
 * 
 */
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.OverlayLayout;

import fr.iessa.controleur.Controleur;

/**
 * @author hodiqual
 * Superpose le panel qui gere la carte et le panel qui gere le trafic.
 */
public class PanelPrincipalMultiCouches extends JPanel {

	/** Permet de gerer plusieurs plans sur un meme panel*/
	private JLayeredPane _gestionPlans;
	
	/** Permet de gerer plusieurs plans sur un meme panel*/
	private JPanel _zpanel;
	
	/** Controleur de notre pattern MVC */
	private Controleur _controleur;
	
	/** Panel qui gere l'infrastructure, ce panel sera en arriere plan */
	private InfrastructurePanel _infrastructurePanel;
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
        return true;
      }
	
	public PanelPrincipalMultiCouches()
	{
		setLayout(new GridLayout(1, 1));
		
		_gestionPlans = new JLayeredPane() {
		public boolean isOptimizedDrawingEnabled() {
	        return true;
	      }
		}
		;
		
		OverlayLayout layout = new OverlayLayout(_gestionPlans);
		_gestionPlans.setLayout(layout);
		_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Click and Go"));
		
		//Gestion de l'infrastructure
		_controleur = new Controleur();
		
		_infrastructurePanel = new InfrastructurePanel(_controleur);
		final ChargeEnCoursLayerUI layerUI = new ChargeEnCoursLayerUI();
		_infrastructurePanel.setChargeEnCoursLayerUI(layerUI);
		JLayer<JPanel> jlayer = new JLayer<JPanel>(_infrastructurePanel, layerUI);

		_infrastructurePanel.setAlignmentX(0.0f);
		_infrastructurePanel.setAlignmentY(0.0f);
		
		_gestionPlans.add(jlayer, -3000);
		
		//Gestion du trafic
		TraficPanel myPanel = new  TraficPanel();
		myPanel.setAlignmentX(0.0f);
		myPanel.setAlignmentY(0.0f);
		_gestionPlans.add(new TraficPanel(),0);
		
		
		/*
		 * IL Y A AUSSI POUR DISPATCHER LES EVENTS
		 * http://stackoverflow.com/questions/21500162/jlayeredpane-how-to-check-if-component-has-another-drawn-under-it
		 */
		
		//Gestion du panel de controle
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);	
	}
	
	
}
