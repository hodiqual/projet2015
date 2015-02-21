
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
    
    private JMenu _menuOption;
    
    private Controleur _controleur;
    
	/** Constructeur */
	public FramePrincipale() {
		// Construction de la fenêtre principale
    	super("Teahupoo");
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
    	
    	_menuOption = new JMenu("Options");
    	_menuOption.setEnabled(false);
    	JMenuItem menuAjoutVue = new JMenuItem("Ajout vue");
    	menuAjoutVue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameSecondaire(_controleur);
			}
		});
    	JCheckBoxMenuItem menuCollision = new JCheckBoxMenuItem("Collisions");
    	menuCollision.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(menuCollision.isSelected())
					_controleur.showCollision(true);
				else
					_controleur.showCollision(false);
			}
		});
    	JMenuItem menuSauvCollision = new JMenuItem("Sauvegarde collisions");
    	menuSauvCollision.addActionListener(new ActionSauverCollisions());
    	_menuOption.add(menuAjoutVue);
    	_menuOption.add(menuCollision);
    	_menuOption.add(menuSauvCollision);
    	_barreMenu.add(_menuOption);
    	
    	// Création et configuration du controleur MVC
    	_controleur = new Controleur(); 
	    this.getContentPane().add(new PanelPrincipalMultiCouches(_controleur,true));
	    final ModeleEvent[] evts = {ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE, 
	    							ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE,
	    							ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR,
	    							ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR,
	    							ModeleEvent.SAUVEGARDE_COLLISION_DONE,
	    							ModeleEvent.SAUVEGARDE_COLLISION_ERREUR};
	    
		_controleur.ajoutVue(this, evts) ;
	    
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
                    _controleur.chargerCarte(nomFichierPlateForme);
        	}
    		
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
                    _controleur.chargerTrafic(nomFichierTrafic);
        	}
            	
    	}
    }
    
    class ActionCollisions implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {  		
    		_controleur.showCollision(true);        	
    	}
    }
    
    class ActionSauverCollisions implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		File fichierCollision = null;
            String nomFichierCollision = "";
            JFileChooser dialogue = new JFileChooser(new File("."));
           
            if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            	fichierCollision = dialogue.getSelectedFile();
            	nomFichierCollision = fichierCollision.getName();
                _controleur.sauvegarderCollision(dialogue.getCurrentDirectory()+"/"+nomFichierCollision + ".txt");
        	}	
    	}
    }
    
    class ActionQuitter implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		System.exit(0);
    	}
    }
    
    /** Actions à realiser lors d'un evenement du contrôleur */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case CHARGEMENT_CARTE_FICHIER_DONE:
			_menuChargerTrafic.setEnabled(true);
				break;
			
			case CHARGEMENT_TRAFIC_FICHIER_DONE:
		    	_menuOption.setEnabled(true);
				break;
				
			case CHARGEMENT_CARTE_FICHIER_ERREUR:
				JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;
				
			case CHARGEMENT_TRAFIC_FICHIER_ERREUR:
				JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;
				
			case SAUVEGARDE_COLLISION_DONE:
				JOptionPane.showMessageDialog(null, "Sauvegarde des collisions effectuées dans " + evt.getNewValue(), "" , JOptionPane.INFORMATION_MESSAGE);
				break;
				
			case SAUVEGARDE_COLLISION_ERREUR:
				JOptionPane.showMessageDialog(null, "Savegarde des collisions echouée : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;

			default:
				break;
		}
		
	}
	
    
}
