/**
 * 
 */
package fr.iessa.vue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * @author hodiqual
 *
 */
public class LabelHorloge extends JLabel implements PropertyChangeListener {

	public LabelHorloge(Controleur controleur)
	{
		super("00:00:00");
		final ModeleEvent[] evts = {ModeleEvent.UPDATE_INSTANT};
		controleur.ajoutVue(this,  evts ) ;
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setText(evt.getNewValue().toString());
	}

}
