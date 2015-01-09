/**
 * 
 */
package fr.iessa.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.trafic.Trafic;

/**
 * @author hodiqual
 *
 */
public class Controleur {
	
	/** Contiendra le trafic lorsqu'il sera chargé dans l'application. */
	private TraficConteneur _modele = new TraficConteneur();
	
	/** Permet de notifier la vue en garantissant que cela soit dans l'Event Dispatch Thread*/
	private SwingPropertyChangeSupport _swingObservable;
	
	public Controleur() {
		// Les observers seront notifiés seulement dans l'Event Dispatch Thread
		_swingObservable = new SwingPropertyChangeSupport(_modele, true);
	}
	
	public void ajoutVue(PropertyChangeListener vue) {
		_swingObservable.addPropertyChangeListener(vue);
	}
	
	public void chargerCarte(String ficname) {
		//Controle de ficname
		if(ficname == null || ficname.equals(""))
		{
			ModeleEvent evtfin = ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR;	
			_swingObservable.firePropertyChange(evtfin.toString(), null, "Le nom du fichier n'est pas renseigne");	
			return;
		}		

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<ImageIcon, ModeleEvent> sw = new SwingWorker<ImageIcon, ModeleEvent>(){
			protected ImageIcon doInBackground() throws Exception {
				Aeroport aeroport = InfrastructureDAO.charger(ficname);
				publish(ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE);

				//Creer l'image background une fois pour toute.
				return null;
			}

			//process & pusblish pour la gestion des resultats intermediaires

			public void done(){
				try {
					ImageIcon imageBackground = get();
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_CARTE_GRAPHIQUE_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, imageBackground));


				} catch (ExecutionException | InterruptedException e) {
					//Cas ou le doInBackground a lanc√© une exception ou a ete interrompu
					e.printStackTrace();
					ModeleEvent evt = ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR;	
					_swingObservable.firePropertyChange(evt.toString(), null, e.getCause().getMessage());
				}
			}         
		};
		
		//On lance le SwingWorker
		sw.execute();
	}
	
	
	public void chargerTrafic(String ficname) {
		//Controle de ficname
		if(ficname == null || ficname.equals(""))
		{
			ModeleEvent evtfin = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR;	
			_swingObservable.firePropertyChange(evtfin.toString(), null, "Le nom du fichier n'est pas renseigne");	
			return;
		}		

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<Trafic, ModeleEvent> sw = new SwingWorker<Trafic, ModeleEvent>(){
			protected Trafic doInBackground() throws Exception {
				//TODO Chargement fichier trafic 
				Trafic trafic = null;
				publish(ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE);
				return trafic;
			}

			//process & pusblish pour la gestion des resultats intermediaires,

			public void done(){
				try {
					Trafic trafic = get();
					_modele.setTrafic(trafic);
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _modele));
				} catch (ExecutionException | InterruptedException e) {
					//Cas ou le doInBackground a lanc√© une exception ou a ete interrompu
					e.printStackTrace();
					ModeleEvent evt = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR;	
					_swingObservable.firePropertyChange(evt.toString(), null, e.getCause().getMessage());
				}
			}         
		};
		
		//On lance le SwingWorker
		sw.execute();
	}
	

}
