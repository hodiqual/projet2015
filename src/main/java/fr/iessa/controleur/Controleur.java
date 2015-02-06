/**
 * 
 */
package fr.iessa.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.Horloge;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.vue.Echelle;

/**
 * @author hodiqual
 *
 */
public class Controleur {
	
	/** Contiendra le trafic lorsqu'il sera charge dans l'application. */
	private Trafic _trafic;
	
	/**
	 * @return the _trafic
	 */
	public Trafic getTrafic() {
		return _trafic;
	}

	/** Horloge de la plateforme */
	private Horloge _horloge;
	
	/** Permet de notifier la vue en garantissant que cela soit dans l'Event Dispatch Thread*/
	private SwingPropertyChangeSupport _swingObservable;
	
	public Controleur() {
		// Les observers seront notifies seulement dans l'Event Dispatch Thread
		_swingObservable = new SwingPropertyChangeSupport(this, true);
	}
	
	public void ajoutVue(PropertyChangeListener vue, ModeleEvent[] events) {
		for (ModeleEvent modeleEvent : events) {
			_swingObservable.addPropertyChangeListener(modeleEvent.toString(), vue);
		}
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
				
				//1. Chargement fichier trafic  et pre_calcul les vols par instant
				TraficDao traficDao = new TraficDao();
				Trafic trafic = traficDao.charger(ficname);
				_trafic = trafic;
				
				//2. Creer Horloge
				_horloge = new Horloge();
				
				//3. Enregistre le trafic a l'horloge 
				_horloge.addObserver(trafic);
				
				//4. Initialise horloge
				_horloge.initialise();
				
				//5. Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
			    
				return trafic;
			}

			//process & publish pour la gestion des resultats intermediaires, PROGRESS fire le TRAVAIL EN COURS 
			// pour que la vue affiche une animation en rond travail en cours avec un tool tips

			public void done(){
				try {
					Trafic trafic = get();
					_trafic = trafic;
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _trafic));
					runTrafic();
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
	
	private boolean _isTraficRunning = false;
	
	private void updateInstant(Instant instant){
		Instant oldInstant = _horloge.getInstantCourant();
		if(instant == null)
			_horloge.tick();
		else
			_horloge.setInstantCourant(instant);
		
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_INSTANT.toString(), oldInstant, _horloge.getInstantCourant());
	}
    
	private int _dureeIntervalle = 40; //  40 milliseconds 25 update par seconde
	
	public void setDureeInterval( int milliseconds ){
		int oldUpdateInterval = _dureeIntervalle;
		_dureeIntervalle = milliseconds;
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_DUREE_INTERVALLE.toString(), oldUpdateInterval, _dureeIntervalle);	
	}
	
	public final Thread _horlogeManager = new Thread() {
        @Override
        public void run() {
           while (true) {        	
        	   
        	   if(_isTraficRunning)
        	   {   
        		   System.out.println("ALIVEEEEE");
        		   updateInstant(null);
        		   try {
        			   Thread.sleep(_dureeIntervalle);  
        		   } catch (InterruptedException ignore) {}
        	   }
        	   else
        		   System.err.println("DEAAAADDDD");   
           }
        }
     };
	
	public void runTrafic(){
		_isTraficRunning = true;
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_IS_TRAFIC_RUNNING.toString(), !_isTraficRunning, _isTraficRunning);
				
		if(_horlogeManager.isAlive() == false)
			_horlogeManager.start();
	}
	
	public void stopTrafic(){
		_isTraficRunning = false;
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_IS_TRAFIC_RUNNING.toString(), !_isTraficRunning, _isTraficRunning);
	}
	
	public void setInstant(int secondes){
		
		SwingWorker<Void,Void> sw = new SwingWorker<Void,Void>(){
			private boolean backupIsTraficRunning = _isTraficRunning;
			
			protected Void doInBackground(){
				_isTraficRunning=false;
				updateInstant(InstantFabrique.getInstantLePlusProche(secondes));
				_isTraficRunning=backupIsTraficRunning;
				return null;
			}
			
			public void done(){
				try {
					Void object = get();
				} catch (InterruptedException | ExecutionException e) {
					System.err.println("Erreur du controleur");
					e.printStackTrace();
				}
			}         
		};
		
		//On lance le SwingWorker
		sw.execute();		
	}

	public boolean isTraficRunning() {
		return _isTraficRunning;
	}
	
	
	
	

}
