/**
 * 
 */
package fr.iessa.vue;

import javax.swing.JPanel;

import aurelienribon.slidinglayout.SLPanel;

/**
 * @author hodiqual
 *
 */
public class PanelVisualisationEtRejeu extends SLPanel {
	
	/**
	 * Contient la vue du controle de rejeu
	 */
	private PanelRejeu _rejeu;
	
	/**
	 * Panel transparent
	 */
	private JPanel _visualisation;
	
	/**
	 * Panel sensible au mouseOver pour afficher le rejeu
	 */
	private JPanel _zoneDetection;
	
	
	public  PanelVisualisationEtRejeu(){
		super();
		
		_rejeu = new PanelRejeu();
		_visualisation = new JPanel();
		_visualisation.setOpaque(false);
		
		_zoneDetection = new JPanel();
		_zoneDetection.setOpaque(false);
		
		
		
		
	}
	
}
