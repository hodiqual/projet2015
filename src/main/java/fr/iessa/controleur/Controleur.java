/**
 * 
 */
package fr.iessa.controleur;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import javax.swing.SwingWorker;
import javax.swing.event.SwingPropertyChangeSupport;

import fr.iessa.dao.infra.PlateformeDAO;
import fr.iessa.dao.trafic.CollisionDao;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.Horloge;
import fr.iessa.metier.HorsLimiteHorloge;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.trafic.FiltreVol;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;
import fr.iessa.vue.Echelle;

/**
 * Implemente le controleur du pattern MVC.
 * Controle les entrees utilisateurs et notifies les observeurs des changements 
 * des objets metiers
 * @author hodiqual
 *
 */
/**
 * @author sb00by
 *
 */
public class Controleur {
	

	/** Contient la plateforme lorsqu'elle est chargee dans l'application. */
	private Aeroport _aeroport;

	/**
	 * @return the _aeroport
	 */
	public Aeroport getAeroport() {
		return _aeroport;
	}
	
	/** Contient le trafic lorsqu'il est charge dans l'application. */
	private Trafic _trafic = null;
	
	/**
	 * @return the _trafic
	 */
	public Trafic getTrafic() {
		return _trafic;
	}

	/** Horloge de la simulation. */
	private Horloge _horloge;
	
	/** Permet de notifier les vues en garantissant que cela soit dans l'Event Dispatch Thread. */
	private SwingPropertyChangeSupport _swingObservable;
	
	/** Filtre des vols */
	private FiltreVol _filtreVol;
	
	/** Constructeur */
	public Controleur() {
		// Les observers seront notifies seulement dans l'Event Dispatch Thread
		_swingObservable = new SwingPropertyChangeSupport(this, true);
	}
	
	/**
	 * Enregistrement des listeners qui oberservent les changements des objets metiers.
	 * Les listeners seront notifies dans l'EDT.
	 * @param vue sera notifie par les changements des objets metiers.
	 * @param events la liste des ModeleEvent auxquels la vue desire etre notifiee.
	 */
	public void ajoutVue(PropertyChangeListener vue, ModeleEvent[] events) {
		for (ModeleEvent modeleEvent : events) {
			_swingObservable.addPropertyChangeListener(modeleEvent.toString(), vue);
		}
	}
	
	/**
	 * Charge en asynchrone un fichier decrivant une plateforme.
	 * Le controleur peut publier ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR, 
	 * ModeleEvent.CHARGEMENT_CARTE_FICHIER_EN_COURS, CHARGEMENT_CARTE_FICHIER_DONE.
	 * @param ficname le chemin complet du fichier a charge.
	 */
	public void chargerCarte(String ficname) {
		//Controle de ficname
		if(ficname == null || ficname.equals("") )
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
				Aeroport aeroport = PlateformeDAO.charger(ficname);
				
				//2. Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
			    
				return aeroport;
			}

			//process & pusblish pour la gestion des resultats intermediaires

			public void done(){
				try {
				    _aeroport = get();
				    
				    System.out.println("CHARGEMENT_CARTE_FICHIER_DONE");
					LibereMemoire.controleMemoire();
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _aeroport));

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
	
	
	/**
	 * Charge en asynchrone un fichier decrivant le trafic.
	 * Le controleur peut publier ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR, 
	 * ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_EN_COURS, CHARGEMENT_TRAFIC_FICHIER_DONE,
	 * ModeleEvent.UPDATE_FILTRE_VOL
	 * @param ficname le chemin complet du fichier decrivant le trafic a charge.
	 */
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
			    
				return _trafic;
			}

			//process & publish pour la gestion des resultats intermediaires, PROGRESS fire le TRAVAIL EN COURS 
			// pour que la vue affiche une animation en rond travail en cours avec un tool tips

			public void done(){
				try {
					Trafic trafic = get();
					
					System.out.println("CHARGEMENT_TRAFIC_FICHIER_DONE");
					LibereMemoire.controleMemoire();
					
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, trafic));
					//runTrafic();
					
					_filtreVol = new FiltreVol(trafic);	
					evt = ModeleEvent.UPDATE_FILTRE_VOL;
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _filtreVol));
					
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
	
	
	/**
	 * Sauvegarde en asynchrone le rapport de collisions.
	 * Le controleur peut publier ModeleEvent.SAUVEGARDE_COLLISION_ERREUR, 
	 * ModeleEvent.SAUVEGARDE_COLLISION_DONE.
	 * @param ficname le chemin complet du fichier qui contiendra le rapport de collision.
	 */
	public void sauvegarderCollision(String ficname) {
		//Controle de ficname
		if(ficname == null || ficname.equals(""))
		{
			ModeleEvent evtfin = ModeleEvent.SAUVEGARDE_COLLISION_ERREUR;	
			_swingObservable.firePropertyChange(evtfin.toString(), null, "Le nom du fichier n'est pas renseigne");	
			return;
		}		

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){
			protected Void doInBackground() throws Exception {
				
				CollisionDao dao = new CollisionDao();
				dao.sauvegardeTrafic(ficname, _trafic.getCollisions());
			    
				return null;
			}

			//process & publish pour la gestion des resultats intermediaires, PROGRESS fire le TRAVAIL EN COURS 
			// pour que la vue affiche une animation en rond travail en cours avec un tool tips

			public void done(){
				try {
					get(); //Check if an error occurs
					
					//notifier la fin du chargement
					ModeleEvent evt = ModeleEvent.SAUVEGARDE_COLLISION_DONE;	
					_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, ficname));

				} catch (ExecutionException | InterruptedException e) {
					//Cas ou le doInBackground a lancé une exception ou a ete interrompu
					e.printStackTrace();
					
					ModeleEvent evt = ModeleEvent.SAUVEGARDE_COLLISION_ERREUR;	
					_swingObservable.firePropertyChange(evt.toString(), null, e.getCause().getMessage());
				}
			}         
		};
		
		//On lance le SwingWorker
		sw.execute();
	}
	
	
	/**
	 * Etat de la simulation s'il est en cours ou non. L'attribut est volatile
	 * car il peut etre lu ou ecrit par different thread (EDT ou Simulation Thread)
	 */
	private volatile boolean _isTraficRunning = false;
	
	/**
	 * Met a jour l'instant courant.
	 * Le controleur publie des ModeleEvent.UPDATE_INSTANT.
	 * Si le nouvel instant courant est hors limite de la simulation, la simulation s'arretera.
	 * @param instant qui deviendra le nouvel instant courant. Si instant == null 
	 * alors l'horloge se decalera d'un pas.
	 */
	private void updateInstant(Instant instant){
		
		Instant oldInstant = _horloge.getInstantCourant();

		try {
			if(instant == null)
				_horloge.tick();
			else
				_horloge.setInstantCourant(instant);
		} catch (HorsLimiteHorloge e) {
			stopTrafic();
		}
		
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_INSTANT.toString(), oldInstant, _horloge.getInstantCourant());
	}
    
	/**
	 * Met a jour l'instant courant.
	 * Le controleur publie des ModeleEvent.UPDATE_INSTANT.
	 * Si le nouvel instant courant est hors limite de la simulation, la simulation s'arretera.
	 * @param secondes deviendra le nouvel instant courant. Si secondes == 0 
	 * alors l'horloge se decalera d'un pas.
	 */
	public void updateInstant(float secondes){
		Instant oldInstant = _horloge.getInstantCourant();
		if(secondes == 0){
			try {
				_horloge.tick();
			} catch (HorsLimiteHorloge e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			this.setInstant((int)secondes);
		
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_INSTANT.toString(), oldInstant, _horloge.getInstantCourant());
	}
    
	
	/**
	 * @return le nombre de secondes correspondant a l'instant courant.
	 */
	public int getInstantCourant(){
	Instant InstantCourant = _horloge.getInstantCourant();
	int PositionSeconde = InstantCourant.getSeconds();
	return PositionSeconde;	
	}
	
	/**
	 * @return le nombre de secondes correspondant a l'instant precedant.
	 */
	public int getInstantPrecedant(){
	Instant InstantCourant = _horloge.getInstantCourant();
	int PositionSeconde = InstantCourant.getSeconds()-InstantFabrique._pasEntreInstant;
	return PositionSeconde;	
	}
	
	
	/**
	 * @return le nombre de secondes correspondant a l'instant suivant.
	 */
	public int getInstantSuivant(){
	Instant InstantCourant = _horloge.getInstantCourant();
	int PositionSeconde = InstantCourant.getSeconds()+InstantFabrique._pasEntreInstant;
	return PositionSeconde;	
	}
	
	/** 
	 * La resolution du temps de la simulation-rejeu, 
	 * correspond au nombre de secondes qui s'ecoule entre chaque instant.
	 */
	private int _dureeIntervalle = 60; //  40 milliseconds 25 update par seconde
	
	/**
	 * @param milliseconds sera le nouvelle resolution du temps, 
	 * le nombre de millisecondes entre l'affichage de 2 instants.
	 */
	public void setDureeInterval( int milliseconds ){
		int oldUpdateInterval = _dureeIntervalle;
		_dureeIntervalle = milliseconds;
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_DUREE_INTERVALLE.toString(), oldUpdateInterval, _dureeIntervalle);	
	}
	
	/**
	 * Thread en charge de mettre a jour l'instant courant et les coordonnees des vols
	 * toutes les _dureeIntervalle si _isTraficRunning == true.
	 */
	public final Thread _horlogeManager = new Thread("Simulation Thread") {
        @Override
        public void run() {
           while (true) {        	
        	   if(_isTraficRunning)
        	   {   
        		   updateInstant(null);
        		   try {
        			   Thread.sleep(_dureeIntervalle);  
        		   } catch (InterruptedException ignore) {}
        	   }  
           }
        }
     };
     
     
 	/**
 	 * @return l'etat de la simulation: running or not.
 	 */
 	public boolean isTraficRunning() {
 		return _isTraficRunning;
 	}
	
 	/**
 	 * Active le rejeu du trafic.
 	 */
	public void runTrafic(){
		_isTraficRunning = true;
		_swingObservable.firePropertyChange(ModeleEvent.UPDATE_IS_TRAFIC_RUNNING.toString(), !_isTraficRunning, _isTraficRunning);
				
		if(_horlogeManager.isAlive() == false)
			_horlogeManager.start();
	}
	

	
 	/**
 	 * Stop le rejeu du trafic.
 	 */
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
	

	/**
	 * @param filtreTypeVol the filtreTypeVol to set
	 */
	public void setFiltreTypeVol(TypeVol filtreTypeVol) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				_filtreVol.setFiltreTypeVol(filtreTypeVol);
				return null;
			}
			
			protected void done(){
				ModeleEvent evt = ModeleEvent.UPDATE_FILTRE_VOL;
				_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _filtreVol));		
			}
		};
		
		sw.execute();
	}

	/**
	 * @param filtreCategorie the filtreCategorie to set
	 */
	public void setFiltreCategorie(Categorie filtreCategorie) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				_filtreVol.setFiltreCategorie(filtreCategorie);	
				return null;
			}
			
			protected void done(){
				ModeleEvent evt = ModeleEvent.UPDATE_FILTRE_VOL;
				_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _filtreVol));		
			}
		};
		
		sw.execute();
	}

	/**
	 * @param filtreCollision the filtreCollision to set
	 */
	public void setFiltreCollision(Boolean filtreCollision) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				_filtreVol.setFiltreCollision(filtreCollision);	
				return null;
			}
			
			protected void done(){
				ModeleEvent evt = ModeleEvent.UPDATE_FILTRE_VOL;
				_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _filtreVol));		
			}
		};
		
		sw.execute();
	}

	public void setFiltrePremierInstant(int filtrePremierInstant) {
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				_filtreVol.setFiltrePremierInstant(InstantFabrique.getInstantLePlusProche(filtrePremierInstant));	
				return null;
			}
			
			protected void done(){
				ModeleEvent evt = ModeleEvent.UPDATE_FILTRE_VOL;
				_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, _filtreVol));		
			}
		};
		
		sw.execute();
	}
	
	public void showCollision(boolean hasToShow)  {
		ModeleEvent evt;
		if(hasToShow)
			evt = ModeleEvent.SHOW_COLLISION;
		else
			evt = ModeleEvent.HIDE_COLLISION;
		
		_swingObservable.firePropertyChange(new PropertyChangeEvent(this, evt.toString(), null, null));		
	}

}
