package fr.iessa.vue;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
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
import fr.iessa.vue.trafic.ComponentVol;

public class PanelTrafic extends JPanel implements PropertyChangeListener, Observer{

	private final Echelle _echelle;
	private final Controleur _controleur;

    public PanelTrafic(Controleur controleur, Echelle echelle) {
        setOpaque(false);
        
        _controleur = controleur;

		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE };
		_controleur.ajoutVue(this,  evts) ;
		

		_echelle = echelle;
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
	private Trafic _trafic;
	
	private Map<Vol,ComponentVol> _volsADessiner;
	
	private final class InitializeComponentVols extends SwingWorker<Void,Void> {
		@Override
		protected Void doInBackground() throws Exception {
			
			_volsADessiner = _trafic.getVols().stream().collect(Collectors.toMap(Function.identity(), v -> new ComponentVol(v,_echelle)));

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_INSTANT };
			_controleur.ajoutVue(PanelTrafic.this,  evts) ;
			
			_echelle.addObserver(PanelTrafic.this);
			
			return null;
		}		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ModeleEvent property = ModeleEvent.valueOf(evt.getPropertyName());
		
		switch (property) {
		case CHARGEMENT_TRAFIC_FICHIER_DONE:
			_trafic = (Trafic) evt.getNewValue();
			new InitializeComponentVols().execute();
			break;
		case UPDATE_INSTANT:
			update((Instant)evt.getNewValue());
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
			_volsADessiner.values().forEach(cv -> cv.update(this) );
	}  
	
	private void update(Instant instant) {
		_volsADessiner.values().forEach(cv -> cv.update(this) );
		revalidate();
		repaint();
		LibereMemoire.controleMemoire();
	}  

}
