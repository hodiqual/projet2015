
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
import fr.iessa.vue.trafic.ComponentVol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné sur la carte ou dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelAffichageVol extends JPanel {

	/** Attributs */
    private final JLabel _jlIdVol, _jlCategorieVol, _jlTypeHeureVol;
    private final JCheckBox _highLight = new JCheckBox("Highlight");
    private final static Color[] _couleursHighlights = new Color[] {Color.BLUE, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PINK};
    private final JButton _boutonShow = new JButton("Show");
    private final JButton _boutonRemove = new JButton("Remove");
    private final Controleur _controleur;
    private final Vol _vol;
    private final ComponentVol _componentVol;
    
    /** Constructeur */
    public PanelAffichageVol(ComponentVol componentVol, Controleur c) {
		
    	super();
    	_controleur = c;
    	_componentVol = componentVol;
    	_vol = componentVol.getVol();
    	
    	setBorder(BorderFactory.createTitledBorder(" "));
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	add(Box.createHorizontalGlue()); //Pour que les éléments prennent toute la largeur du panel
		
		_jlIdVol = new JLabel("Identifiant : " + _vol.getId());
		_jlIdVol.setFont( new Font(getFont().getName(), Font.BOLD, getFont().getSize()) ); //Mise en gras
    	_jlCategorieVol = new JLabel("Catégorie : " + (_vol.getCategorie()).getAbreviation() );
    	if (_vol.getTypeVol() == TypeVol.ARR) {
    		_jlTypeHeureVol = new JLabel("Heure d'arrivée : " + _vol.getPremierInstant());
	    } else {
	    	_jlTypeHeureVol = new JLabel("Heure de départ : " + _vol.getPremierInstant());
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
    			_componentVol.setHighlightColor(_couleursHighlights[0]);
    		} else {
    			_componentVol.setHighlightColor(null);
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

			_componentVol.setHighlightColor(null);
    		Container parent = PanelAffichageVol.this.getParent();
    		parent.remove(PanelAffichageVol.this);
    		parent.revalidate();
    		parent.repaint();
    		
    	}
    }
    

}
