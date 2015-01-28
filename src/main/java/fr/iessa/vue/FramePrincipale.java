
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/** Classe FramePrincipale
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class FramePrincipale extends JFrame {
	/** La barre de menu */
	private JMenuBar _barreMenu;
    private JMenu _menu;
    private JMenuItem _menuChargerPlateForme;
    private JMenuItem _menuChargerTrafic;
    private JMenuItem _menuQuitter;
    
	/** Constructeur */
	public FramePrincipale() {
		// Construction de la fenêtre principale
    	super("800x600 800x600 800x600");
    	this.setPreferredSize((new Dimension(800, 600)));
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Construction de la barre de menu
	    _barreMenu = new JMenuBar();
	    _menu = new JMenu("Fichier");
	    _menuChargerPlateForme = new JMenuItem("Charger Plate-Forme");
	    _menuChargerTrafic = new JMenuItem("Charger Trafic");
	    _menuChargerTrafic.setEnabled(false);;
	    _menuQuitter = new JMenuItem("Quitter");
	    
	    _menuChargerPlateForme.addActionListener(new ActionChargerPlateForme());
	    _menuChargerTrafic.addActionListener(new ActionChargerTrafic());
    	_menuQuitter.addActionListener(new ActionQuitter());
	
    	_menu.add(_menuChargerPlateForme);
    	_menu.add(_menuChargerTrafic);
    	_menu.add(_menuQuitter);
	
    	_barreMenu.add(_menu);
    	
    	//Create and set up the content pane.
	    this.getContentPane().add(new PanelPrincipalMultiCouches());
	    this.validate();
	    this.setJMenuBar(_barreMenu);
	    this.pack();
	    this.setVisible(true);
    	
	}
	
	/** Listeners */
    class ActionChargerPlateForme implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
            File fichierPlateForme = null;
            String cheminFichierPlateForme = "";
            JFileChooser dialogue = new JFileChooser(new File("."));
            dialogue.showOpenDialog(null);
            
            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		fichierPlateForme = dialogue.getSelectedFile();
            		cheminFichierPlateForme = fichierPlateForme.getAbsolutePath();
            		_menuChargerTrafic.setEnabled(true);
        	}
             
            System.out.println("Fichier Plate-forme choisi : " + cheminFichierPlateForme);
    		
    	}
    }
    
    class ActionChargerTrafic implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		File fichierTrafic = null;
            String cheminFichierTrafic = "";
            JFileChooser dialogue = new JFileChooser(new File("."));
            dialogue.showOpenDialog(null);

            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		fichierTrafic = dialogue.getSelectedFile();
            		cheminFichierTrafic = fichierTrafic.getAbsolutePath();
        	}
             
            System.out.println("Fichier Trafic choisi : " + cheminFichierTrafic);
    		
    	}
    }
    
    class ActionQuitter implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		System.exit(0);
    	}
    }
    
    /** Fonctions */
    	// Charger le fichier Plate-Forme
    public void chargerPlateForme() {
    	
    }
    	// Charger le fichier Trafic
    public void chargerTrafic() {
    	
    }
    
    	//Quitter
    public void quitter() {
	
    }
	
}
