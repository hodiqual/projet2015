
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.TypeVol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné sur la carte ou dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelAffichageVol extends JPanel {
	/** Attributs */
    private final JLabel _jlIdVol, _jlCategorieVol, _jlTypeHeureVol;
    private final JCheckBox _highLight = new JCheckBox("Highlight");
    private final JButton _boutonShow = new JButton("Show");
    private final JButton _boutonRemove = new JButton("Remove");
    private final Controleur _controleur;
    private final Vol _vol;
    
    /** Constructeur */
    public PanelAffichageVol(Vol v, Controleur c) {
		
    	super();
    	_controleur = c;
    	_vol = v;
    	
    	setBorder(BorderFactory.createTitledBorder(" "));
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	add(Box.createHorizontalGlue()); //Pour que les éléments prennent toute la largeur du panel
		
		_jlIdVol = new JLabel("Identifiant : " + v.getId());
		_jlIdVol.setFont( new Font(getFont().getName(), Font.BOLD, getFont().getSize()) ); //Mise en gras
    	_jlCategorieVol = new JLabel("Catégorie : " + (v.getCategorie()).getAbreviation() );
    	if (v.getTypeVol() == TypeVol.ARR) {
    		_jlTypeHeureVol = new JLabel("Heure d'arrivée : " + v.getPremierInstant());
	    } else {
	    	_jlTypeHeureVol = new JLabel("Heure de départ : " + v.getPremierInstant());
	    }
    	
    	_highLight.addActionListener(new ActionHighLight());
    	_boutonShow.addActionListener(new ActionShow());
    	_boutonRemove.addActionListener(new ActionRemove());
    	
    	add(_jlIdVol);
    	add(_jlCategorieVol);
    	add(_jlTypeHeureVol);
    	add(_highLight);
    	add(_boutonShow);
    	add(_boutonRemove);
		
	}
	
    /** Met en évidence le vol */
    class ActionHighLight implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		if ( _highLight.isSelected() ){
    			System.out.println(_vol.getId() + " : Je suis sélectionné !");
    		} else {
    			System.out.println(_vol.getId() + " : Je ne suis pas sélectionné.");
    		}
    		
    	}
    }
    
    /** Réaffiche le trafic au momment où le vol apparait */
    class ActionShow implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		_controleur.stopTrafic();
    		_controleur.setInstant(_vol.getPremierInstant().getSeconds());
    	}
    }
    
    /** Supprime l'affichage des paramètres du vol */
    class ActionRemove implements ActionListener {
    	public void actionPerformed(ActionEvent arg0) {
    		
    		Container parent = PanelAffichageVol.this.getParent();
    		parent.remove(PanelAffichageVol.this);
    		parent.revalidate();
    		parent.repaint();
    		
    	}
    }
    

}
