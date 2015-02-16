
package fr.iessa.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.TypeVol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné sur la carte ou dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelAffichageVol extends JPanel implements PropertyChangeListener {
	/** Attributs */
    private JLabel _jlIdVol, _jlCategorieVol, _jlTypeHeureVol;
    private final JCheckBox _checkBox = new JCheckBox("Texte");
    private final JButton _boutonShow = new JButton("Show");
    private final JButton _boutonRemove = new JButton("Remove");
    
    /** Constructeur */
    public PanelAffichageVol(Vol v) {
		
    	super();
    	setBorder(BorderFactory.createTitledBorder(" Paramètres : "));
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	add(Box.createHorizontalGlue()); //Pour que les éléments prennent toute la largeur du panel
		
		_jlIdVol = new JLabel("Identifiant : " + v.getId());
    	_jlCategorieVol = new JLabel("Catégorie : " + (v.getCategorie()).getAbreviation() );
    	if (v.getTypeVol() == TypeVol.ARR) {
    		_jlTypeHeureVol = new JLabel("Heure d'arrivée : " + v.getPremierInstant());
	    } else {
	    	_jlTypeHeureVol = new JLabel("Heure de départ : " + v.getPremierInstant());
	    }
    	
    	_checkBox.addActionListener(new ActionCheck());
    	_boutonShow.addActionListener(new ActionShow());
    	_boutonRemove.addActionListener(new ActionRemove());
    	
    	add(_jlIdVol);
    	add(_jlCategorieVol);
    	add(_jlTypeHeureVol);
    	add(_checkBox);
    	add(_boutonShow);
    	add(_boutonRemove);
		
	}
	
    /** Listeners */
    class ActionCheck implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    	}
    }
    
    class ActionShow implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    	}
    }
    
    class ActionRemove implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		// Pas sure
    		removeAll();
    	}
    }
    
    /** Actions à realiser après une demande de mise à jour par le contrôleur */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case UPDATE_INSTANT:
				break;

			default:
				break;
		}
		
	}

}
