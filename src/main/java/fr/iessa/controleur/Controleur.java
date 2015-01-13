/**
 * 
 */
package fr.iessa.controleur;

import java.awt.image.BufferedImage;
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
	
	/** Contiendra le trafic lorsqu'il sera charge dans l'application. */
	private TraficConteneur _modele = new TraficConteneur();
	
	/** Permet de notifier la vue en garantissant que cela soit dans l'Event Dispatch Thread*/
	private SwingPropertyChangeSupport _swingObservable;
	
	public Controleur() {
		// Les observers seront notifies seulement dans l'Event Dispatch Thread
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
		SwingWorker<BufferedImage, ModeleEvent> sw = new SwingWorker<BufferedImage, ModeleEvent>(){
			protected BufferedImage doInBackground() throws Exception {
				Aeroport aeroport = InfrastructureDAO.charger(ficname);
				publish(ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE);
				
				//Creer l'image background une fois pour toute.
				//http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
				BufferedImage carte = new BufferedImage(0, 0, 0);
				//aeroport
				//Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
			    //Et aussi graphics.dispose pour toute la memoire qui n'a plus de reference
			    //http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html#dispose()
				return null;
			}

			//process & pusblish pour la gestion des resultats intermediaires

			public void done(){
				try {
					BufferedImage imageBackground = get();
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_CARTE_GRAPHIQUE_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, imageBackground));


				} catch (ExecutionException | InterruptedException e) {
					//Cas ou le doInBackground a lancé une exception ou a ete interrompu
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

				//Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
			    
				return trafic;
			}

			//process & publish pour la gestion des resultats intermediaires, PROGRESS fire le TRAVAIL EN COURS 
			// pour que la vue affiche une animation en rond travail en cours avec un tool tips

			public void done(){
				try {
					Trafic trafic = get();
					_modele.setTrafic(trafic);
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _modele));
					
					//TODO Lancer en arriere plan la detection des collisions. pour faire un ReadyToUse.
					//Attribut qui ecoute le modele chargement trafic fichier done pour lancer la detection 
					//des collisions dans un swingworker
				} catch (ExecutionException | InterruptedException e) {
					//Cas ou le doInBackground a lancé une exception ou a ete interrompu
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
