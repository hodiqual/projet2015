
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;


/** Classe FramePrincipale
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class FramePrincipale extends JFrame implements PropertyChangeListener {
	/** La barre de menu */
	private JMenuBar _barreMenu;
    private JMenu _menu;
    private JMenuItem _menuChargerPlateForme;
    private JMenuItem _menuChargerTrafic;
    private JMenuItem _menuQuitter;
    
    private Controleur _controleur;
    
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
    	
    	// Création et configuration du controleur MVC
    	_controleur = new Controleur(); 
	    this.getContentPane().add(new PanelPrincipalMultiCouches(_controleur));
	    _controleur.ajoutVue(this);
	    
	    //Create and set up the content pane.
	    this.validate();
	    this.setJMenuBar(_barreMenu);
	    this.pack();
	    this.setVisible(true);
    	
	}
	
	/** Listeners */
    class ActionChargerPlateForme implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
            File fichierPlateForme = null;
            String nomFichierPlateForme = "";
            JFileChooser dialogue = new JFileChooser(new File("."));
            
            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		fichierPlateForme = dialogue.getSelectedFile();
            		nomFichierPlateForme = fichierPlateForme.getName();
        	}
            
            _controleur.chargerCarte(nomFichierPlateForme);
    		
    	}
    }
    
    class ActionChargerTrafic implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		File fichierTrafic = null;
            String nomFichierTrafic = "";
            JFileChooser dialogue = new JFileChooser(new File("."));
           
            if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            		fichierTrafic = dialogue.getSelectedFile();
            		nomFichierTrafic = fichierTrafic.getName();
        	}
            
            _controleur.chargerCarte(nomFichierTrafic);
            	
    	}
    }
    
    class ActionQuitter implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		System.exit(0);
    	}
    }
    
    /** Redéfinition des fonctions de l'interface PropertyChangeListener */
    public void propertyChange(PropertyChangeEvent evt) {
		
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
		case CHARGEMENT_CARTE_GRAPHIQUE_DONE:
			_menuChargerTrafic.setEnabled(true);
			break;

		default:
			break;
		}
		
	}
	
    
}
