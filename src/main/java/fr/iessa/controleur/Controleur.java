/**
 * 
 */
package fr.iessa.controleur;

import javax.swing.event.SwingPropertyChangeSupport;

/**
 * @author hodiqual
 *
 */
public class Controleur {
	
	/** Contiendra le trafic lorsqu'il sera chargé dans l'application. */
	private TraficConteneur traficWrapper = new TraficConteneur();
	
	/** Permet de notifier la vue en garantissant que cela soit dans l'Event Dispatch Thread*/
	private SwingPropertyChangeSupport _swingObservable;

}
