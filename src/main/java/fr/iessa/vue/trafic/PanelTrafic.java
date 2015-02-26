package fr.iessa.vue.trafic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.Collision;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.ChargeEnCoursLayerUI;
import fr.iessa.vue.Echelle;

/**
 * Panel qui affiche le trafic
 * @author hodiqual
 *
 */
public class PanelTrafic extends JPanel implements PropertyChangeListener, Observer{

	private final Echelle _echelle;
	
	private final Controleur _controleur;
    
	private Trafic _trafic;
	
	private Map<Vol,ComponentVol> _volsADessiner = new HashMap<Vol,ComponentVol>();
	
	private Map<Collision,ComponentCollision> _collisionsADessiner = new HashMap<Collision,ComponentCollision>();

	private ChargeEnCoursLayerUI layerUI;

    public PanelTrafic(Controleur controleur, Echelle echelle) {
        setOpaque(false);
        
        _controleur = controleur;

		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE
									, ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_ERREUR
									, ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_EN_COURS};
		
		_controleur.ajoutVue(this,  evts) ;
        
        setTrafic(_controleur.getTrafic());
		
		_echelle = echelle;
    }
    
    private void setTrafic(Trafic trafic) {
    	_trafic = trafic;
    	
    	if(_trafic != null)
    	{
    		new InitializeComponentVols().execute();
    	}	
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
	
	private final class InitializeComponentVols extends SwingWorker<Void,Void> {
		@Override
		protected Void doInBackground() throws Exception {
			
			_volsADessiner.clear();
			_volsADessiner.putAll(_trafic.getVols().stream().collect(Collectors.toMap(Function.identity(), v -> new ComponentVol(v,_echelle))));

			_collisionsADessiner.clear();
			_collisionsADessiner.putAll(_trafic.getCollisions().stream().collect(Collectors.toMap(Function.identity(), v -> new ComponentCollision(v,_controleur,_echelle,_volsADessiner))));
			_collisionsADessiner.values().forEach(cc -> PanelTrafic.this.add(cc));
			_collisionsADessiner.values().forEach(cc -> cc.update());
			
			final ModeleEvent[] evts = { ModeleEvent.UPDATE_INSTANT, ModeleEvent.SHOW_COLLISION, ModeleEvent.HIDE_COLLISION };
			_controleur.ajoutVue(PanelTrafic.this,  evts) ;
			
			_echelle.addObserver(PanelTrafic.this);
			
			return null;
		}
		
		protected void done() {
			try {
				Void object = get();
				_obs.setChanged();
				_obs.notifyObservers(_volsADessiner);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}finally{
				if(layerUI!=null)
					layerUI.stop();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ModeleEvent property = ModeleEvent.valueOf(evt.getPropertyName());
		
		switch (property) {
		case CHARGEMENT_TRAFIC_FICHIER_EN_COURS:
			if(layerUI!=null)
				layerUI.start();
			break;
		case CHARGEMENT_TRAFIC_FICHIER_DONE:
			setTrafic((Trafic) evt.getNewValue());
			break;
		case CHARGEMENT_TRAFIC_FICHIER_ERREUR:
			if(layerUI!=null)
				layerUI.stop();
			break;
		case UPDATE_INSTANT:
			update((Instant)evt.getNewValue());
			break;
		case SHOW_COLLISION:
			_collisionsADessiner.values().forEach(cc -> cc.setVisible(true));
			break;
		case HIDE_COLLISION:
			_collisionsADessiner.values().forEach(cc -> cc.setVisible(false));
			break;
		default:
			break;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g); 

		_volsADessiner.values().stream()
						.filter(cv -> cv.isHighlighted() && cv.isShowing())
						.forEach(cv -> cv.drawChemin(g) );
	}

	@Override
	//Update a une observation de changement de _echelle
	public void update(Observable o, Object arg) {
			_volsADessiner.values().forEach(cv -> cv.update(this) );
			_collisionsADessiner.values().forEach(cc -> cc.update() );
			revalidate();
			repaint();
	}  
	
	private void update(Instant instant) {
		_volsADessiner.values().forEach( cv -> cv.update(this) );
		revalidate();
		repaint();
	}

	public void setChargeEnCoursLayerUI(ChargeEnCoursLayerUI layerUI) {
		this.layerUI = layerUI;	
	}

	public Map<Vol, ComponentVol> getVolsADessiner() {
		return _volsADessiner;
	}  
	
	private class WorkDoneObservable extends Observable{
		public void setChanged(){
			super.setChanged();
		}
	}
	
	private final WorkDoneObservable _obs = new WorkDoneObservable();
	
	public void addObserver(Observer o) {
		_obs.addObserver(o);
	}

}
