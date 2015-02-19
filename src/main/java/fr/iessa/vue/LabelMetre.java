/**
 * 
 */
package fr.iessa.vue;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * @author duvernal
 *
 */
public class LabelMetre extends JLabel implements PropertyChangeListener, Observer {

	
	public LabelMetre(Controleur controleur, Echelle echelle)
	{
	
		super("1 km");
		setVisible(false);

		Font fontParDefaut = getFont();
		setFont(new Font(fontParDefaut.getName(), Font.PLAIN, fontParDefaut.getSize()));
		
		
		final ModeleEvent[] evts = {ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE};
		controleur.ajoutVue(this,  evts) ;
		
		echelle.addObserver(this);
	}

	/** 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		setVisible(true);
	}
	
	/**
	 * @param o observe echelle
	 * @param arg la nouvelle transformation a appliquer
	 */
	@Override
	public void update(Observable o, Object arg) {
			AffineTransform transformationCourante = (AffineTransform) arg;
			double factor = transformationCourante.getScaleX();
	}

}
