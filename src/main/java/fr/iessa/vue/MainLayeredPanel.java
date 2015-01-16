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
		
		//Gestion du trafic
		//Via Un JLayer peut desactiver la sensibilite au MouseWheel et ainsi passer ca directement au InfraPanel
		TraficPanel myPanel = new  TraficPanel();
		myPanel.setAlignmentX(0.0f);
		myPanel.setAlignmentY(0.0f);
		_gestionPlans.add(new TraficPanel(),0);
		
		JPanel trafic = new JPanel();
		trafic.setAlignmentX(0.0f);
		trafic.setAlignmentY(0.0f);
		trafic.setMinimumSize(new Dimension(800, 600));
		trafic.setPreferredSize(new Dimension(800, 600));
		trafic.setDoubleBuffered(true);
		//trafic.setLayout(new OverlayLayout(trafic));
		trafic.setLayout(new GridLayout(1, 1));
		trafic.setOpaque(false);
		trafic.setBorder(BorderFactory.createLineBorder(Color.RED));
		//_gestionPlans.add(trafic,0);
		
		JButton avion1 = new JButton("avion1");
		//avion1.setAlignmentX(0.0f);
		//avion1.setAlignmentY(0.0f);
		//avion1.setBounds(0, 200, 800, 600);
		avion1.setPreferredSize(new Dimension(40, 40));
		avion1.setMaximumSize(new Dimension(40, 40));
		avion1.setLocation(0,200);
		JToolTip toolTip = avion1.createToolTip();
		toolTip.setToolTipText("PROUT");
		trafic.add(avion1);
		//_gestionPlans.add(avion1,0);
		avion1.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int newX = avion1.getLocation().x + 10;
				int newY = avion1.getLocation().y - 10;

				avion1.setLocation(newX, newY);
				System.out.println("avion1");
				
			}
		});
		
		JButton avion2 = new JButton("avion2");
		avion2.setBounds(200, 0, 40, 40);
		avion2.setPreferredSize(new Dimension(40, 40));
		avion2.setMaximumSize(new Dimension(40, 40));
		avion2.setLocation(200,0);
		trafic.add(avion2);
		//_gestionPlans.add(avion2,0);
		avion2.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int newX = avion2.getLocation().x - 10;
				int newY = avion2.getLocation().y + 10;

				avion2.setLocation(newX, newY);
				System.out.println("avion2");
				
			}
		});
		
		JButton avion3 = new JButton("avion3");
		avion2.setBounds(100, 100, 40, 40);
		/*avion3.setPreferredSize(new Dimension(40, 40));
		avion3.setMaximumSize(new Dimension(40, 40));
		avion3.setLocation(100,100);*/
		trafic.add(avion3);
		//_gestionPlans.add(avion3,0);
		avion3.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int newX = avion3.getLocation().x + 10;
				int newY = avion3.getLocation().y + 10;

				avion3.setLocation(newX, newY);
				System.out.println("avion3");
				
			}
		});
		
		/*
		 * IL Y A AUSSI POUR DISPATCHER LES EVENTS
		 * http://stackoverflow.com/questions/21500162/jlayeredpane-how-to-check-if-component-has-another-drawn-under-it
		 */
		
		//Gestion du panel de controle
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);	
	}
	
	
}
