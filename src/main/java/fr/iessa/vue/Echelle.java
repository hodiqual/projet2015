/**
 * 
 */
package fr.iessa.vue;

import java.awt.geom.AffineTransform;

/**
 * @author hodiqual
 *
 */
public class Echelle {
	

	private static final int _minDestLargeur = 800;	
	private static int _minDestHauteur;
	
	private static int _minReelX, _maxReelX, _minReelY, _maxReelY;
	/** Permet de faire des marges autour de l'aeroport, exprimee en metre*/
	private final int _margeReel = 100;
	
	private AffineTransform _transfoAffine;
	
	public static void setLimitesReelles(int minX, int maxX, int minY, int maxY){
		_minReelX = minX;
		_maxReelX = maxX;
		_minReelY = minY;
		_maxReelY = maxY;
		
		// Pour garder les proportions (_maxReelX-_minReelX) / (_maxReelY-_minReelY) == _minDestLargeur / _minDestHauteur
		_minDestHauteur = _minDestLargeur * (_maxReelY-_minReelY) / (_maxReelX-_minReelX) ;
	}
	
	/** 
	 * Calcul la transformation affine pour transposer des coordonnees du monde reel
	 * aux coordonnees systeme ecran.
	 * 
	 * @see http://www.ukonline.be/programmation/java/tutoriel/chapitre10/page6.php
	 * 
	 */
	public Echelle(int zoomLevel) {

		double maxReelXmarge = _maxReelX + _margeReel;
		double minReelXmarge = _minReelX - _margeReel;
		double maxReelYmarge = _maxReelY + _margeReel;
		double minReelYmarge = _minReelY - _margeReel;
		
		double xScale = _minDestLargeur*zoomLevel / (maxReelXmarge-minReelXmarge);
        double yScale = _minDestHauteur*zoomLevel / (maxReelYmarge-minReelYmarge);
        
        _transfoAffine = new AffineTransform();
        
        _transfoAffine.translate(-xScale*(minReelXmarge), yScale*(maxReelYmarge));
        _transfoAffine.scale(xScale, -yScale);
		
	}
	
	public AffineTransform getAffineTransform() {
		return _transfoAffine;
	}	
}
