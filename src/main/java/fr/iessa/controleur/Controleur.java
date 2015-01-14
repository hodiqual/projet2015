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
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Point;
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
		if(ficname == null || ficname.equals("") )//fichierexistant)
		{
			ModeleEvent evtfin = ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR;	
			_swingObservable.firePropertyChange(evtfin.toString(), null, "Le nom du fichier n'est pas renseigne");	
			return;
		}		

		//Tache possiblement longue donc a faire dans un thread different de l'EDT 
		SwingWorker<BufferedImage[], ModeleEvent> sw = new SwingWorker<BufferedImage[], ModeleEvent>(){
			protected BufferedImage[] doInBackground() throws Exception {
				Aeroport aeroport = InfrastructureDAO.charger(ficname);
				publish(ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE);
				//Destruction des Scanner et des String qui ont permis le chargement et qui n'ont plus de reference.
			    LibereMemoire.free();
	
			    double minReelX, maxReelX, minReelY, maxReelY;
		        
		        Point ptInit = aeroport.get_points().get(0);
		        minReelX = maxReelX = ptInit.getX();
		        minReelY = maxReelY = ptInit.getY();
		        
		        for (Ligne ligne : aeroport.get_taxiway()) {
					GeneralPath path = ligne.get_lignePointAPoint();
					double l_minReelX = path.getBounds().getMinX();
					double l_maxReelX = path.getBounds().getMaxX();
					double l_minReelY = path.getBounds().getMinY();
					double l_maxReelY = path.getBounds().getMaxY();
					
					minReelX = (minReelX < l_minReelX) ? minReelX : l_minReelX;
					minReelY = (minReelY < l_minReelY) ? minReelY : l_minReelY;
					
					maxReelX = (maxReelX > l_maxReelX) ? maxReelX : l_maxReelX;
					maxReelY = (maxReelY > l_maxReelY) ? maxReelY : l_maxReelY;			
				}
		        
		        for (Ligne ligne : aeroport.get_pushbacks()) {
					GeneralPath path = ligne.get_lignePointAPoint();
					double l_minReelX = path.getBounds().getMinX();
					double l_maxReelX = path.getBounds().getMaxX();
					double l_minReelY = path.getBounds().getMinY();
					double l_maxReelY = path.getBounds().getMaxY();
					
					minReelX = (minReelX < l_minReelX) ? minReelX : l_minReelX;
					minReelY = (minReelY < l_minReelY) ? minReelY : l_minReelY;
					
					maxReelX = (maxReelX > l_maxReelX) ? maxReelX : l_maxReelX;
					maxReelY = (maxReelY > l_maxReelY) ? maxReelY : l_maxReelY;			
				}
		        
		        for (Ligne ligne : aeroport.get_lignes()) {
					GeneralPath path = ligne.get_lignePointAPoint();
					double l_minReelX = path.getBounds().getMinX();
					double l_maxReelX = path.getBounds().getMaxX();
					double l_minReelY = path.getBounds().getMinY();
					double l_maxReelY = path.getBounds().getMaxY();
					
					minReelX = (minReelX < l_minReelX) ? minReelX : l_minReelX;
					minReelY = (minReelY < l_minReelY) ? minReelY : l_minReelY;
					
					maxReelX = (maxReelX > l_maxReelX) ? maxReelX : l_maxReelX;
					maxReelY = (maxReelY > l_maxReelY) ? maxReelY : l_maxReelY;			
				}
		        
		        
		        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        int widthS = (int) screenSize.getWidth();
		        int heightS = (int) screenSize.getHeight();
		        
		        BufferedImage[] cartes = new BufferedImage[5];
		        for (int i = 0; i < cartes.length; i++) {
					
				
		        	double width = (5-i+0.5)*widthS;
		        	double height = (5-i+0.5)*heightS;
		        
		        LibereMemoire.controleMemoire();
				//Creer l'image background une fois pour toute.
				// http://research.jacquet.xyz/teaching/java/dessin/
				// http://docs.oracle.com/javase/tutorial/2d/images/drawonimage.html
				// http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
				BufferedImage carte = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
				LibereMemoire.controleMemoire();
		        //carte.setAccelerationPriority(arg0);
		        // r�cup�re un objet Graphics pour pouvoir dessiner sur l'image
		        // nous r�cup�rons en fait un objet Graphics2D, qui offre bien plus
		        // de fonctionnalit�s qu'un simple objet Graphics
		        Graphics2D g = (Graphics2D)carte.getGraphics();
		        // active le lissage des formes
		        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                 RenderingHints.VALUE_ANTIALIAS_ON);
		        g.setRenderingHint(RenderingHints.KEY_RENDERING,
		                 RenderingHints.VALUE_RENDER_QUALITY);
		        
		        
		        
		        double xScale = width  / (maxReelX-minReelX);
		        double yScale = height / (maxReelY-minReelY);
		        System.out.println("xScale: " + xScale);
		        System.out.println("yScale: " + yScale);
		        AffineTransform saveXform = g.getTransform();
		        AffineTransform toCenterAt = new AffineTransform();
		        
		            toCenterAt.translate(-xScale*minReelX, yScale*maxReelY);
		            toCenterAt.scale(xScale, -yScale);
		     
		            g.transform(toCenterAt);
		            //System.out.println(toCenterAt.createInverse());
		            LibereMemoire.controleMemoire();
		         // dessine des formes
		            g.setColor(Color.YELLOW);
			        for (Ligne ligne : aeroport.get_lignes()) {
						g.draw(ligne.get_lignePointAPoint());		
					}
			        LibereMemoire.controleMemoire();
			        g.setColor(Color.GREEN);
			        for (Ligne ligne : aeroport.get_taxiway()) {
						g.draw(ligne.get_lignePointAPoint());		
					}
		     
			        LibereMemoire.controleMemoire();
			        g.setColor(Color.RED);
			        for (Ligne ligne : aeroport.get_pushbacks()) {
						g.draw(ligne.get_lignePointAPoint());		
					}
			        LibereMemoire.controleMemoire();

		            
		            //retrieve the initial transform
		            g.setTransform(saveXform);
		       
			    //Et aussi graphics.dispose pour toute la memoire qui n'a plus de reference
			    //http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html#dispose()
			    g.dispose();
			    LibereMemoire.controleMemoire();
			    
			    cartes[i] = carte;
		        }
				return cartes;
			}

			//process & pusblish pour la gestion des resultats intermediaires

			public void done(){
				try {
					BufferedImage[] imageBackground = get();
					LibereMemoire.controleMemoire();
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
