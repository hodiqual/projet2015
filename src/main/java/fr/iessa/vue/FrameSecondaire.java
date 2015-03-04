package fr.iessa.vue;

import java.awt.Dimension;

import javax.swing.JFrame;

import fr.iessa.controleur.Controleur;

/**
 * 
 * @author hodiqual
 *
 */
public class FrameSecondaire extends JFrame {
	public FrameSecondaire(Controleur controleur) {
		super("Ground Trafic Control");
    	this.setPreferredSize((new Dimension(800, 600)));
    	
	    this.getContentPane().add(new PanelPrincipalMultiCouches(controleur,false));
	    
	    //Create and set up the content pane.
	    this.validate();
	    this.pack();
	    this.setVisible(true);
	}
}
