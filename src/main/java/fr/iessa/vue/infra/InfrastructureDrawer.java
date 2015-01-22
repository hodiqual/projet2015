/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Map;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Point;

/**
 * @author hodiqual
 *
 */
public class InfrastructureDrawer {
	
	private static LignesDrawer lignesDrawer = new LignesDrawer();
	
	private static PointsDrawer pointsDrawer = new PointsDrawer();
	
	private static RunwayDrawer runwaysDrawer = new RunwayDrawer();
	
	private Aeroport _aeroport = null;
	
	public double _minReelX, _maxReelX, _minReelY, _maxReelY;
	
	/** Permet de faire des marges autour de l'aeroport, exprimee en metre*/
	private final double _margeReel = 100.0D;
	
	private int _largeurImage, _hauteurImage;
	
	public AffineTransform _transfoAffine;
	
	public void dessineAeroport(Aeroport aeroport, Graphics2D g2, double largeurImage, double hauteurImage, AffineTransform mouseScroll )
	{
		// 1. Calcul ou recalcul de la transformation affine si necessaire
		if( aeroport != _aeroport || largeurImage != _largeurImage || hauteurImage != _hauteurImage)
		{
			calculeTransposition(aeroport, largeurImage, hauteurImage);
		}
		
		// 2. Sauvegarde la transformation courante
		AffineTransform transformToRestore = g2.getTransform();
		
		// 3. Appliquer la transformation
		g2.transform(mouseScroll);
		g2.transform(_transfoAffine);
		
		// 4. Faire les dessins
		lignesDrawer.dessine(aeroport, g2);
		runwaysDrawer.dessine(aeroport, g2);

		lignesDrawer.dessineMarquageAuSol(aeroport, g2);
		pointsDrawer.dessine(aeroport, g2);
		
		// 5. restaure la transformation initiale.
		g2.setTransform(transformToRestore);
	}

	/** 
	 * Calcul la transformation affine pour transposer des coordonnees du monde reel
	 * aux coordonnees systeme ecran.
	 * 
	 * @see http://www.ukonline.be/programmation/java/tutoriel/chapitre10/page6.php
	 * 
	 */
	private void calculeTransposition(Aeroport aeroport, double largeurImage, double hauteurImage) {
		
		//faut-il recalculer les _minReelX, _maxReelX, _minReelY, _maxReelY
		if(aeroport != _aeroport)
		{			
			_aeroport = aeroport;
			
			_minReelX = _minReelY = Integer.MAX_VALUE;
			_maxReelX = _maxReelY = Integer.MIN_VALUE;
			 
	        trouverLimitesReels(aeroport.get_lignes());
	        trouverLimitesReels(aeroport.get_taxiway());
	        trouverLimitesReels(aeroport.get_pushbacks());
	        trouverLimitesReels(aeroport.get_points());
		}
		
		double maxReelXmarge = _maxReelX + _margeReel;
		double minReelXmarge = _minReelX - _margeReel;
		double maxReelYmarge = _maxReelY + _margeReel;
		double minReelYmarge = _minReelY - _margeReel;
		
		double xScale = largeurImage / (maxReelXmarge-minReelXmarge);
        double yScale = hauteurImage / (maxReelYmarge-minReelYmarge);
        
        _transfoAffine = new AffineTransform();
        
        _transfoAffine.translate(-xScale*(minReelXmarge), yScale*(maxReelYmarge));
        _transfoAffine.scale(xScale, -yScale);
		
	}

	private void trouverLimitesReels(List<? extends Ligne> lignes) {
       
		for (Ligne ligne : lignes) {
			GeneralPath path = ligne.get_lignePointAPoint();
			double l_minReelX = path.getBounds().getMinX();
			double l_maxReelX = path.getBounds().getMaxX();
			double l_minReelY = path.getBounds().getMinY();
			double l_maxReelY = path.getBounds().getMaxY();
			
			_minReelX = Double.min(_minReelX, l_minReelX);
			_minReelY = Double.min(_minReelY, l_minReelY);
			_maxReelX = Double.max(_maxReelX, l_maxReelX);
			_maxReelY = Double.max(_maxReelY, l_maxReelY);	
		}
		
		
		
	}
	
	private void trouverLimitesReels(Map<String, Point> _points) {
		for (Map.Entry<String, Point> entry : _points.entrySet()) {
			Point point = entry.getValue();
			
			double p_ReelX = point.getX();
			double p_ReelY = point.getY();
			
			_minReelX = Double.min(_minReelX, p_ReelX);
			_minReelY = Double.min(_minReelY, p_ReelY);
			_maxReelX = Double.max(_maxReelX, p_ReelX);
			_maxReelY = Double.max(_maxReelY, p_ReelY);
       
		}
	}

}
