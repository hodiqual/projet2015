
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import fr.iessa.metier.Instant.InstantFabrique;

/** 
 * Fenêtre principale du programme
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class FramePrincipale extends JFrame implements PropertyChangeListener {
	/** Barre de menu contenant un menu "Fichier" et un menu "Options" */
	private JMenuBar _barreMenu;
	/** Menu "Fichier" */
    private JMenu _menuFichier;
    /** Pour charger le fichier "Plateforme"
     * @see ActionChargerPlateForme
     */
    private JMenuItem _menuChargerPlateForme;
    /** Pour charger le fichier "Trafic"
     * @see ActionChargerTrafic
     */
    private JMenuItem _menuChargerTrafic;
    /** Pour quitter le programme */
    private JMenuItem _menuQuitter;
    /** Menu "Options" */
    private JMenu _menuOption;
    /** Controleur de la MVC */
    private Controleur _controleur;
    
	/** Construction de la fenêtre principale et du controleur MVC
	 * @param _barreMenu
	 * @param _menuFichier
	 * @param _menuOption
	 * @param _controleur
	 */
	public FramePrincipale() {
		// Construction de la fenêtre principale
    	super("Teahupoo");
    	this.setPreferredSize((new Dimension(800, 600)));
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // Construction de la barre de menu
	    _barreMenu = new JMenuBar();
	    _menuFichier = new JMenu("Fichier");
	    _menuChargerPlateForme = new JMenuItem("Charger plateforme");
	    _menuChargerTrafic = new JMenuItem("Charger trafic");
	    _menuChargerTrafic.setEnabled(false);
	    _menuQuitter = new JMenuItem("Quitter");
	    
	    _menuChargerPlateForme.addActionListener(new ActionChargerPlateForme());
	    _menuChargerTrafic.addActionListener(new ActionChargerTrafic());
    	_menuQuitter.addActionListener(new ActionQuitter());
	
    	_menuFichier.add(_menuChargerPlateForme);
    	_menuFichier.add(_menuChargerTrafic);
    	_menuFichier.add(_menuQuitter);
	
    	_barreMenu.add(_menuFichier);
    	
    	_menuOption = new JMenu("Options");
    	_menuOption.setEnabled(false);
    	
    	//hodiqual
    	JMenuItem menuAjoutVue = new JMenuItem("Ajout vue");
    	menuAjoutVue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrameSecondaire(_controleur);
			}
		});
    	
    	//hodiqual
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
    	
    	//hodiqual
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
    
    //hodiqual
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
    
    /** Actions realisées lors d'un evenement du contrôleur
     * _ Test du chargement des fichiers
     * _ Test de la sauvegarde du fichier "Collisions"
     * _ Active le menu "Cherger Trafic" et "Options"
     */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case CHARGEMENT_CARTE_FICHIER_DONE:
			_menuChargerTrafic.setEnabled(true);
				break;
			
			case CHARGEMENT_TRAFIC_FICHIER_DONE:
		    	_menuOption.setEnabled(true);
		    	//hodiqual
		        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		        manager.addKeyEventDispatcher(new KeyDispatcher());
				break;
				
			case CHARGEMENT_CARTE_FICHIER_ERREUR:
				JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;
				
			case CHARGEMENT_TRAFIC_FICHIER_ERREUR:
				JOptionPane.showMessageDialog(null, "Chargement echoue : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;
				
			//hodiqual	
			case SAUVEGARDE_COLLISION_DONE:
				JOptionPane.showMessageDialog(null, "Sauvegarde des collisions effectuées dans " + evt.getNewValue(), "" , JOptionPane.INFORMATION_MESSAGE);
				break;
			
			//hodiqual
			case SAUVEGARDE_COLLISION_ERREUR:
				JOptionPane.showMessageDialog(null, "Savegarde des collisions echouée : " + evt.getNewValue(), "" , JOptionPane.ERROR_MESSAGE);
				break;

			default:
				break;
		}
		
	}
    
    //hodiqual
    private class KeyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED ) {
            	if(e.getKeyCode() == KeyEvent.VK_RIGHT) {	
            		_controleur.setInstant(_controleur.getInstantCourant()+InstantFabrique._pasEntreInstant);
            		return true;
            	}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            		_controleur.setInstant(_controleur.getInstantCourant()-InstantFabrique._pasEntreInstant);
                    return true;
                }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                	if(_controleur.isTraficRunning())
                		_controleur.stopTrafic();
                	else
                		_controleur.runTrafic();
                	
                	return true;
                }  
            	
            	return false;
            }
            else
            	return false;
        }
    }
	
    
}
