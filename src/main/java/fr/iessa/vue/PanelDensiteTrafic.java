
package fr.iessa.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.Vol;

/** 
 * Classe PanelDensiteTrafic affiche une courbe représentant le nombre d'avions en fonction du temps
 * @author THOMAS Raimana
 * @version 1.0 
 */
public class PanelDensiteTrafic extends JPanel implements PropertyChangeListener {

	/**
	 * Controleur de la MVC
	 */
	private final Controleur _controleur;
	
	/**
	 * Comme un tableau qui contient pour chaque instant la liste des vols présent à cette instant
	 * @see propertyChange(PropertyChangeEvent evt)
	 */
	private TreeMap<Instant, Set<Vol>> _volsParInstant;
	
	/**
	 * Courbe
	 * @see propertyChange(PropertyChangeEvent evt)
	 */
	private GeneralPath _courbe = new GeneralPath();
	
	/**
	 * Curseur pour repérer sur la courbe l'instant présent
	 * @see paintComponent(Graphics g)
	 * @see propertyChange(PropertyChangeEvent evt)
	 */
	private Line2D _curseur;
	//private JLabel _nbrVolscourant;
	
	/**
	 * Largeur et hauteur du panel.
	 * La largeur est celle du panel Tableau de bord.
	 */
	private final int _largeurPanel = 300, _hauteurPanel = 150;
  
    /** 
     * Construction du panel et mise en observé par le contrôleur
     * @ param _controleur
     */
    public PanelDensiteTrafic(Controleur c) {
    	
    	// Construction du panel
    	super();
    	this.setEnabled(false);
    	setMinimumSize(new Dimension(_largeurPanel, _hauteurPanel));
		setMaximumSize(new Dimension(_largeurPanel, _hauteurPanel));
		setPreferredSize(new Dimension(_largeurPanel, _hauteurPanel));
    	setBorder(BorderFactory.createTitledBorder("Densité du trafic : "));
    	
    	// Configuration du controleur
    	_controleur = c;
    	final ModeleEvent[] evts = {ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE, ModeleEvent.UPDATE_INSTANT};
		_controleur.ajoutVue(this, evts) ;
		
    }
    
    /** Cré le curseur */
    private void placeCurseur(Instant instantCourant) {
    	double y1 = 20;
    	double y2 = _hauteurPanel - 10;
    	double W = _largeurPanel -18;
    	// Transpose les deux points du curseur dans le repère de l'écran
    	double maxX = InstantFabrique.getMaximumInstant().getSeconds() - 10;
		double minX = InstantFabrique.getMinimumInstant().getSeconds() + 10;
		double x = ((W * instantCourant.getSeconds()) / (maxX - minX)) + 8;
		
    	// Créé le curseur
    	_curseur.setLine(x, y1, x, y2);
    	
    	repaint();
	}

	/** Dessine la courbe et le curseur */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Color colorToRestore = g2.getColor();
		Stroke strokeToRestore = g2.getStroke();
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(1));
		g2.draw(_courbe);
		if (_curseur != null){
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			g2.draw(_curseur);
		}

		g2.setColor(colorToRestore);
		g2.setStroke(strokeToRestore);
		
	}
    
    /** 
     * Actions realisées lors d'une demande de mise à jour par le contrôleur
     * _ Ajout de la valeur max de la densité
     * _ Cré la courbe
     * _ Réalise le transposition de repères
     */
    public void propertyChange(PropertyChangeEvent evt) {
    	
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case CHARGEMENT_TRAFIC_FICHIER_DONE:
				
				PanelDensiteTrafic.this.setEnabled(true);
				
				// Réécri le titre du panel avec la valeur maximum de la densitée du trafic
				int nbrVols, nbrMaxVols = 0;
				_volsParInstant = _controleur.getTrafic().getVolsParInstant();
				for (Map.Entry<Instant, Set<Vol>> entry : _volsParInstant.entrySet()) {
					Set<Vol> vols = entry.getValue();
					nbrVols = vols.size();
					if (nbrVols > nbrMaxVols) {
						nbrMaxVols = nbrVols;
					}
				}
				PanelDensiteTrafic.this.setBorder(BorderFactory.createTitledBorder("Densité du trafic : " + "Max = " + nbrMaxVols + " vols"));
				
				// Transforme la TreeMap _volsParInstant en Set sans la première valeur
				Instant premierInstant = _volsParInstant.firstKey();
				_courbe.moveTo(premierInstant.getSeconds(), _volsParInstant.get(premierInstant).size());
				Set<Entry<Instant, Set<Vol>>> set = _volsParInstant.tailMap(premierInstant, false).entrySet();
				
				// Créé la courbe dans un repère orthogonale
				for (Entry<Instant, Set<Vol>> entry : set) {
		        	_courbe.lineTo(entry.getKey().getSeconds(), entry.getValue().size());
				}
				
				// Transpose la courbe dans le repère de l'écran
				double minX = _courbe.getBounds().getMinX() - 10;
				double maxX = _courbe.getBounds().getMaxX() + 10;
				double minY = _courbe.getBounds().getMinY() - 3;
				double maxY = _courbe.getBounds().getMaxY() + 4;
				int W = _largeurPanel - 18;
				int H = _hauteurPanel;
				
				double xS = W / (maxX-minX);
		        double yS = H / (maxY-minY);
		       
		        AffineTransform transformationAffine = new AffineTransform();
		        transformationAffine.translate((-xS*(minX))+8, yS*(maxY));
		        transformationAffine.scale(xS, -yS);
		        _courbe.transform(transformationAffine);
		        
		        // Création du curseur
		    	_curseur = new Line2D.Double();
		        placeCurseur(InstantFabrique.getMinimumInstant());
		        
				break;
				
			case UPDATE_INSTANT:
				Instant instantCourant = (Instant)evt.getNewValue();
				placeCurseur(instantCourant);
				
				break;
			
			case UPDATE_IS_TRAFIC_RUNNING:
				
				break;

			default:
				break;
		}
		
	}
    
}
