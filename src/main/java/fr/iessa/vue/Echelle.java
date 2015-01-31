/**
 * 
 */
package fr.iessa.vue;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
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
	
	private AffineTransform _zoomTransformation = new AffineTransform();
	private AffineTransform _mouseScroll = new AffineTransform(); 
	private AffineTransform _globalTransformation = new AffineTransform();
	
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
	
	public void setZoomLevel(int zoomLevel, int limiteLargeur, int limiteHauteur) {
		_zoomLevel = zoomLevel;
		createZoomTransformation();
		updateGlobalTransformation();
		setChanged();
		notifyObservers(getAffineTransform());
	}
	
	public void setScroll(Point2D.Double ecartRelatif, int limiteLargeur, int limiteHauteur) {
		_dxdyscroll.x -= ecartRelatif.getX();
		_dxdyscroll.y -= ecartRelatif.getY();

		_dxdyscroll.x = java.lang.Double.max(_dxdyscroll.x, 0D);
		_dxdyscroll.y = java.lang.Double.max(_dxdyscroll.y, 0D);

		_dxdyscroll.x = java.lang.Double.min(_dxdyscroll.x, getDestLargeur()-limiteLargeur);
		_dxdyscroll.y = java.lang.Double.min(_dxdyscroll.y, getDestHauteur()-limiteHauteur);

		createScrollTransformation();
		updateGlobalTransformation();
		setChanged();
		notifyObservers(getAffineTransform());
	}
	
	private void createScrollTransformation() {
		_mouseScroll = new AffineTransform();
		_mouseScroll.translate(-(int)(_dxdyscroll.getX()), -(int)(_dxdyscroll.getY()));
	}

	private void updateGlobalTransformation() {
		_globalTransformation = new AffineTransform(_mouseScroll);
		_globalTransformation.concatenate(_zoomTransformation);
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
		return _globalTransformation;
	}

	public int getDestLargeur() {
		return _zoomLevel * _minDestLargeur;
	}

	public int getDestHauteur() {
		return _zoomLevel * _minDestHauteur;
	}		
}
