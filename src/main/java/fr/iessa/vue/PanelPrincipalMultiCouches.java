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
	private PanelInfrastructure _infrastructurePanel;
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
        return true;
      }
	
	public PanelPrincipalMultiCouches(Controleur controleur)
	{
		setLayout(new GridLayout(1, 1));	
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);
		
		_gestionPlans = new JLayeredPane() {
		public boolean isOptimizedDrawingEnabled() {
	        return true;
	      }
		};
		
		OverlayLayout layout = new OverlayLayout(_gestionPlans);
		_gestionPlans.setLayout(layout);
		/*_gestionPlans.setBorder(BorderFactory.createTitledBorder(
                                    "Veuillez charger un fichier de plateforme ..."));*/
		
		//Gestion de l'infrastructure
		this._controleur = controleur;
		
		_infrastructurePanel = new PanelInfrastructure(_controleur);
		final ChargeEnCoursLayerUI layerUI = new ChargeEnCoursLayerUI();
		_infrastructurePanel.setChargeEnCoursLayerUI(layerUI);
		JLayer<JPanel> jlayer = new JLayer<JPanel>(_infrastructurePanel, layerUI);

		_infrastructurePanel.setAlignmentX(0.0f);
		_infrastructurePanel.setAlignmentY(0.0f);
		
		_gestionPlans.add(jlayer, -3000);
		
		//Gestion du trafic
		PanelTrafic traficPanel = new  PanelTrafic();
		traficPanel.setAlignmentX(0.0f);
		traficPanel.setAlignmentY(0.0f);
		_gestionPlans.add(traficPanel,0);
		
		//Gestion des controles Lecteur et Tableau de Bord
		PanelDesControles controles = new PanelDesControles(_controleur);
		controles.setAlignmentX(0.0f);
		controles.setAlignmentY(0.0f);
		_gestionPlans.add(controles,1);
		
		//Rajout du composant de gestion des plans a ce JPanel
		add(_gestionPlans);	
	}
	
	
}
