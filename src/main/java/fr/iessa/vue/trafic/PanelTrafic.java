package fr.iessa.vue.trafic;

import java.awt.Dimension;
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

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.ChargeEnCoursLayerUI;
import fr.iessa.vue.Echelle;

public class PanelTrafic extends JPanel implements PropertyChangeListener, Observer{

	private final Echelle _echelle;
	
	private final Controleur _controleur;
    
	private Trafic _trafic;
	
	private Map<Vol,ComponentVol> _volsADessiner = new HashMap<Vol,ComponentVol>();

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

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_INSTANT };
			_controleur.ajoutVue(PanelTrafic.this,  evts) ;
			
			_echelle.addObserver(PanelTrafic.this);
			
			return null;
		}
		
		protected void done() {
			try {
				Void object = get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
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
		case UPDATE_INSTANT:
			update((Instant)evt.getNewValue());
			break;
			
		default:
			break;
		}
	}

	@Override
	//Update a une observation de changement de _echelle
	public void update(Observable o, Object arg) {
			_volsADessiner.values().forEach(cv -> cv.update(this) );
			revalidate();
			repaint();
	}  
	
	private void update(Instant instant) {
		_volsADessiner.values().forEach(cv -> cv.update(this) );
		revalidate();
		repaint();
	}

	public void setChargeEnCoursLayerUI(ChargeEnCoursLayerUI layerUI) {
		this.layerUI = layerUI;	
	}

	public Map<Vol, ComponentVol> getVolsADessiner() {
		return _volsADessiner;
	}  

}
