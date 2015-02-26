/**
 * 
 */
package fr.iessa.vue;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Observable;


/**
 * @author hodiqual
 *
 */
public class Echelle extends Observable{
	
	/***/
	private static final int _minDestLargeur = 800;	
	private static int _minDestHauteur;
	
	/***/
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
	
	//http://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position
	public void setZoomLevel(int zoomLevel, Point position ,int limiteLargeur, int limiteHauteur) {
		
		double oldScaleX = _zoomTransformation.getScaleX();
		double oldScaleY = _zoomTransformation.getScaleY();
		
		_zoomLevel = zoomLevel;
		
		createZoomTransformation();
		
		double scaleX = _zoomTransformation.getScaleX() / oldScaleX;
		double scaleY = _zoomTransformation.getScaleY() / oldScaleY;
		
        _dxdyscroll.x = position.getX()*(scaleX-1) + scaleX*_dxdyscroll.x;
        _dxdyscroll.y = position.getY()*(scaleY-1) + scaleY*_dxdyscroll.y;
        
        modifieScroll(new Point2D.Double(), limiteLargeur, limiteHauteur);
		
		createScrollTransformation();
		
		updateGlobalTransformation();
		setChanged();
		notifyObservers(getAffineTransform());
	}
	
	private void modifieScroll(Point2D.Double ecartRelatif, int limiteLargeur, int limiteHauteur) {
		
		if(limiteLargeur>=getDestLargeur())
			_dxdyscroll.x = -(limiteLargeur-getDestLargeur())/2;
		else
		{
			_dxdyscroll.x -= ecartRelatif.getX();
			_dxdyscroll.x = java.lang.Double.max(_dxdyscroll.x, 0D);
			_dxdyscroll.x = java.lang.Double.min(_dxdyscroll.x, getDestLargeur()-limiteLargeur);
		}		
		
		if(limiteHauteur>=getDestHauteur())
			_dxdyscroll.y = -(limiteHauteur-getDestHauteur())/2;
		else
		{
			_dxdyscroll.y -= ecartRelatif.getY();
			_dxdyscroll.y = java.lang.Double.max(_dxdyscroll.y, 0D);
			_dxdyscroll.y = java.lang.Double.min(_dxdyscroll.y, getDestHauteur()-limiteHauteur);
		}	
		
	}
	
	public void setScroll(Point2D.Double ecartRelatif, int limiteLargeur, int limiteHauteur) {
		
		modifieScroll(ecartRelatif, limiteLargeur, limiteHauteur);

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
