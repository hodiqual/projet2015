/**
 * 
 */
package fr.iessa.vue;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observable;

/**
 * @author hodiqual
 *
 */
public class Echelle extends Observable{
	

	private static final int _minDestLargeur = 800;	
	private static int _minDestHauteur;
	
	private static int _minReelX, _maxReelX, _minReelY, _maxReelY;
	/** Permet de faire des marges autour de l'aeroport, exprimee en metre*/
	private static final int _margeReel = 100;
	
	private AffineTransform _zoomTransformation;
	private AffineTransform _mouseScroll = new AffineTransform(); 
	
	private int _zoomLevel = 1;
	
	private Point2D.Double _dxdyscroll = new Point2D.Double(); 
	
	public void setLimitesReelles(int minX, int maxX, int minY, int maxY){
		_minReelX = minX;
		_maxReelX = maxX;
		_minReelY = minY;
		_maxReelY = maxY;
		
		// Pour garder les proportions (_maxReelX-_minReelX) / (_maxReelY-_minReelY) == _minDestLargeur / _minDestHauteur
		_minDestHauteur = _minDestLargeur * (_maxReelY-_minReelY) / (_maxReelX-_minReelX) ;

		createZoomTransformation();
		updateGlobalTransformation();
	}
	
	/** 
	 * Calcul la transformation affine pour transposer des coordonnees du monde reel
	 * aux coordonnees systeme ecran.
	 * 
	 * @see http://www.ukonline.be/programmation/java/tutoriel/chapitre10/page6.php
	 * 
	 */
	public Echelle() {
		createZoomTransformation();
	}
	
	public void setZoomLevel(int zoomLevel) {
		_zoomLevel = zoomLevel;
		createZoomTransformation();
		updateGlobalTransformation();
		setChanged();
		notifyObservers(getAffineTransform());
	}
	
	private void updateGlobalTransformation() {
	}

	private void createZoomTransformation()  {
		double maxReelXmarge = _maxReelX + _margeReel;
		double minReelXmarge = _minReelX - _margeReel;
		double maxReelYmarge = _maxReelY + _margeReel;
		double minReelYmarge = _minReelY - _margeReel;
		
		double xScale = _minDestLargeur*_zoomLevel / (maxReelXmarge-minReelXmarge);
        double yScale = _minDestHauteur*_zoomLevel / (maxReelYmarge-minReelYmarge);
        
        _zoomTransformation = new AffineTransform();
        
        _zoomTransformation.translate(-xScale*(minReelXmarge), yScale*(maxReelYmarge));
        _zoomTransformation.scale(xScale, -yScale);
	}
	
	public AffineTransform getAffineTransform() {
		return _zoomTransformation;
	}	
}
