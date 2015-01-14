/**
 * 
 */
package fr.iessa.vue;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
	
	
	public MainLayeredPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		_gestionPlans = new JLayeredPane();
		_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Click and Go"));
		
		
		//Gestion du fond ecran: l'infrastructure
		_controleur = new Controleur();
		_infrastructurePanel = new InfrastructurePanel(_controleur);
		//_zpanel.setComponentZOrder(_infrastructurePanel, 0); //0 -> arriere-plan
		//_gestionPlans.add(_infrastructurePanel, 0); //0 -> arriere-plan
		//_gestionPlans.setLayout(new GridLayout(1, 2));
		
		_gestionPlans.setLayout(null);
		_infrastructurePanel.setBounds(10, 30, 300, 400);
		
		_gestionPlans.add(_infrastructurePanel, -3000);
		JButton button = new JButton("PROUT");
		button.setBounds(60, 80, 300, 400);
		_gestionPlans.add(button,0);
		/*button.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("MOUSE MOUSE MOUSE");
				
			}
		});
		button.setBounds(10, 10, 20, 20);*/
		//InfrastructurePanel prout = new InfrastructurePanel(_controleur);
		//prout.setBounds(320, 30, 300, 400);
		
		/*
		 * IL Y A AUSSI POUR DISPATCHER LES EVENTS
		 * http://stackoverflow.com/questions/21500162/jlayeredpane-how-to-check-if-component-has-another-drawn-under-it
		 */
		
		_gestionPlans.addMouseListener(_infrastructurePanel);
		//Gestion du trafic

		//Gestion du panel de controle
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);
	}
	
	
}
