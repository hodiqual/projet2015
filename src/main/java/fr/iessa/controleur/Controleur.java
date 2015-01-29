/**
 * 
 */
package fr.iessa.controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Point;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.vue.infra.InfrastructureDrawer;

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
		if(ficname == null || ficname.equals("") )//fichierexistant)
		{
			ModeleEvent evtfin = ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR;	
			_swingObservable.firePropertyChange(evtfin.toString(), null, "Le nom du fichier n'est pas renseigne");	
			return;
		}		
		
		ModeleEvent evtfin = ModeleEvent.CHARGEMENT_CARTE_FICHIER_EN_COURS;	
		_swingObservable.firePropertyChange(evtfin.toString(), null, null);

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<Aeroport, ModeleEvent> sw = new SwingWorker<Aeroport, ModeleEvent>(){
			protected Aeroport doInBackground() throws Exception {

				//1. Charger fichier infrastructure
				Aeroport aeroport = InfrastructureDAO.charger(ficname);
				
				//2. Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
			    
				return aeroport;
			}

			//process & pusblish pour la gestion des resultats intermediaires

			public void done(){
				try {

				    Aeroport aeroport = get();
					LibereMemoire.controleMemoire();
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, aeroport));

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
		
		ModeleEvent evtfin = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_EN_COURS;	
		_swingObservable.firePropertyChange(evtfin.toString(), null, null);

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<Trafic, ModeleEvent> sw = new SwingWorker<Trafic, ModeleEvent>(){
			protected Trafic doInBackground() throws Exception {
				
				//1. Chargement fichier trafic 
				TraficDao traficDao = new TraficDao();
				Trafic trafic = traficDao.charger(ficname);

				//2. Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
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
