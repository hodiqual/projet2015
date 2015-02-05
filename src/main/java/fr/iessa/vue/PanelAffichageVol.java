
package fr.iessa.vue;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.TypeVol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné sur la carte ou dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelAffichageVol extends JPanel {
	/** Attributs */
    private JLabel _parDefaut = new JLabel("Aucun vol selectionne");
    private JLabel _jlIdVol, _jlCategorieVol, _jlTypeHeureVol;
    
    /** Constructeur */
    public PanelAffichageVol() {
		
    	super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel("Paramètres d'un vol"));
		add(_parDefaut);
		
	}
	
    public void SetVol(Vol v) {
    	
    	_jlIdVol = new JLabel(v.getId());
    	_jlCategorieVol = new JLabel( (v.getCategorie()).getAbreviation() );
    	if (v.getTypeVol() == TypeVol.ARR) {
    		_jlTypeHeureVol = new JLabel("Arrivée : " + v.getPremierInstant());
	    } else {
	    	_jlTypeHeureVol = new JLabel("Départ : " + v.getPremierInstant());
	    }
    	
    	remove(_parDefaut);
    	add(_jlIdVol);
    	add(_jlCategorieVol);
    	add(_jlTypeHeureVol);
    	
    }

}
