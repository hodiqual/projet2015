/**
 * 
 */
package fr.iessa.vue;

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
import javax.swing.OverlayLayout;

import fr.iessa.controleur.Controleur;

/**
 * @author hodiqual
 *
 */
public class MainLayeredPanel extends JPanel {

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
        return false;
      }
	
	
	public MainLayeredPanel()
	{
		setLayout(new GridLayout(1, 1));
		
		_gestionPlans = new JLayeredPane() {
		public boolean isOptimizedDrawingEnabled() {
	        return false;
	      }
		}
		;
		
		OverlayLayout layout = new OverlayLayout(_gestionPlans);
		_gestionPlans.setLayout(layout);
		_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Click and Go"));
		
		
		//Gestion du fond ecran: l'infrastructure
		_controleur = new Controleur();
		_infrastructurePanel = new InfrastructurePanel(_controleur);
		final ChargeEnCoursLayerUI layerUI = new ChargeEnCoursLayerUI();
		_infrastructurePanel.setChargeEnCoursLayerUI(layerUI);
		JLayer<JPanel> jlayer = new JLayer<JPanel>(_infrastructurePanel, layerUI);

		_infrastructurePanel.setAlignmentX(0.0f);
		_infrastructurePanel.setAlignmentY(0.0f);
		//_zpanel.setComponentZOrder(_infrastructurePanel, 0); //0 -> arriere-plan
		//_gestionPlans.add(_infrastructurePanel, 0); //0 -> arriere-plan
		//_gestionPlans.setLayout(new GridLayout(1, 2));
		//_infrastructurePanel.setBounds(10, 30, 300, 400);
		
		_gestionPlans.add(jlayer, -3000);
		
		JButton button = new JButton("PROUT");
	    button.setAlignmentX(0.0f);
	    button.setAlignmentY(0.0f);
		button.setPreferredSize(new Dimension(40, 40));
		button.setMaximumSize(new Dimension(40, 40));
		//button.setBounds(60, 80, 300, 400);
		JPanel trafic = new JPanel();
		trafic.setOpaque(false);
		_gestionPlans.add(trafic,0);
		trafic.add(button);

		/*trafic.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int newX = button.getLocation().x + 10;
				int newY = button.getLocation().y + 10;

				button.setLocation(newX, newY);
				System.out.println("TRAFIC PANEL PANEL");
				
			}
		});*/
		button.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int newX = button.getLocation().x + 10;
				int newY = button.getLocation().y + 10;

				button.setLocation(newX, newY);
				System.out.println("MOUSE MOUSE MOUSE");
				
			}
		});
		//button.setBounds(10, 10, 20, 20);
		//InfrastructurePanel prout = new InfrastructurePanel(_controleur);
		//prout.setBounds(320, 30, 300, 400);
		
		/*
		 * IL Y A AUSSI POUR DISPATCHER LES EVENTS
		 * http://stackoverflow.com/questions/21500162/jlayeredpane-how-to-check-if-component-has-another-drawn-under-it
		 */
		
		//addMouseListener(_infrastructurePanel);
		//Gestion du trafic

		//Gestion du panel de controle
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);
		//add(_infrastructurePanel);
		
	}
	
	
}
