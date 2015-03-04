
package fr.iessa.vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.TypeVol;
import fr.iessa.vue.trafic.ComponentVol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */
public class PanelAffichageVol extends JPanel {
	
	/** Labels pour l'identifiant, la catégorie et l'heure de départ ou d'arrivée d'un vol
	 * 
	 */
    private final JLabel _jlIdVol, _jlCategorieVol, _jlTypeHeureVol;
    /** Pour repérer un vol sur la plateforme */
    private final JCheckBox _highLight = new JCheckBox("Highlight");
    /** Pour afficher le trafic à l'instant où le vol apparait */
    private final JButton _boutonShow = new JButton("Show");
    /** Pour afficher le trafic à l'instant où le vol apparait */
    private final JButton _boutonRemove = new JButton("Remove");
    /** Controleur de la MVC */
    private final Controleur _controleur;
    private final Vol _vol;
    /** Une vue du vol 
     * @see ComponentVol
     * @see ActionHighLight
     * @see ActionRemove
     */
    private final ComponentVol _componentVol;
    /** Couleurs des Highlight */
    private final static Color[] _couleursHighlights = new Color[] {Color.BLUE, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PINK};
    private static int _cpteur = 0;
    private final int _couleur;
    
    /** Construction du panel
     * @param componentVol
     * 			La "vue" du vol sélectionné
     * @param c
     * 			Le contrôleur de la MVC
     */
    public PanelAffichageVol(ComponentVol componentVol, Controleur c) {
		
    	super();
    	_controleur = c;
    	_componentVol = componentVol;
    	_vol = componentVol.getVol();
    	_cpteur++;
    	_cpteur = _cpteur%_couleursHighlights.length; //Remise à zéro du compteur
    	_couleur = _cpteur;
    	
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
    	_highLight.setForeground(_couleursHighlights[_couleur]);
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
    			_componentVol.setHighlightColor(_couleursHighlights[_couleur]);
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
