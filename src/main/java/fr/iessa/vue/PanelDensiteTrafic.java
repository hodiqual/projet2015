
package fr.iessa.vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Vol;

/** Classe PanelAffichageVol qui affiche les paramètres d'un vol sélectionné dans la liste
 * @author THOMAS Raimana
 * @version 1.0 
 */

public class PanelDensiteTrafic extends JPanel implements PropertyChangeListener {

	/** Attributs */
	private final Controleur _controleur;
	private TreeMap<Instant, Set<Vol>> _volsParInstant;
	private GeneralPath _courbe = new GeneralPath();
	private Echelle _echelle = new Echelle();
  
    /** Constructeur */
    public PanelDensiteTrafic(Controleur c) {
    	
    	super();
    	
    	// Configuration du controleur
    	_controleur = c;
    	final ModeleEvent[] evts = {ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE, ModeleEvent.UPDATE_INSTANT};
		_controleur.ajoutVue(this, evts) ;
		
    }
    
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.draw(_courbe);
		
	}
    
    /** Actions à realiser lors d'une mise à jour par le contrôleur */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case CHARGEMENT_TRAFIC_FICHIER_DONE:
				
				_volsParInstant = _controleur.getTrafic().getVolsParInstant();
				Instant premierInstant = _volsParInstant.firstKey();
				_courbe.moveTo(premierInstant.getSeconds(), _volsParInstant.get(premierInstant).size());
				
				// Transforme la TreeMap _volsParInstant en Set sans la première valeur
				Set<Entry<Instant, Set<Vol>>> set = _volsParInstant.tailMap(premierInstant, false).entrySet();
				// Trace la courbe dans un repère orthogonale
				for (Entry<Instant, Set<Vol>> entry : set) {
		        	_courbe.lineTo(entry.getKey().getSeconds(), entry.getValue().size());
				}
				
				//
				double minX = _courbe.getBounds().getMinX();
				double maxX = _courbe.getBounds().getMaxX();
				double minY = _courbe.getBounds().getMinY();
				double maxY = _courbe.getBounds().getMaxY();
				
				break;
				
			case UPDATE_INSTANT:
				
				break;

			default:
				break;
		}
		
	}
    
}
