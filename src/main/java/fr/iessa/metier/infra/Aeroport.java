/**
 * 
 */
package fr.iessa.metier.infra;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

/**
 * @author hodiqual
 * Classe metier qui definit la plateforme aeroportuaire 
 */
public class Aeroport {

	private String _nom;
	
	private Map<String, Point> _points = new Hashtable<String, Point>(100);
	
	private List<Ligne> _lignes = new ArrayList<Ligne>(100);
	
	private List<Taxiway> _taxiway = new ArrayList<Taxiway>(100);
	
	private List<Pushback> _pushbacks = new ArrayList<Pushback>(100);
	
	private List<Runway> _runways = new ArrayList<Runway>();
	
	
	public Aeroport(String nom)
	{
		_nom = nom;
	}
	
	public void add(Point p){
		_points.put(p.get_nom(), p);
	}
	
	public void add(Ligne l){
		_lignes.add(l);
	}
	
	public void add(Taxiway t){
		_taxiway.add(t);
	}
	
	public void add(Pushback p){
		_pushbacks.add(p);
	}
	
	public void add(Runway r){
		_runways.add(r);
	}

	@Override
	public String toString() {
		return "Aeroport [_nom=" + _nom + ", _points=" + _points.size() + ", _lignes="
				+ _lignes.size() + ", _taxiway=" + _taxiway.size() + ", _pushbacks="
				+ _pushbacks.size() + ", _runways=" + _runways.size() + "]";
	}
	
	/**
	 * @return the _nom
	 */
	public String get_nom() {
		return _nom;
	}

	/**
	 * @return the _points
	 */
	public Map<String, Point> get_points() {
		return _points;
	}

	/**
	 * @return the _lignes
	 */
	public List<Ligne> get_lignes() {
		return _lignes;
	}

	/**
	 * @return the _taxiway
	 */
	public List<Taxiway> get_taxiway() {
		return _taxiway;
	}

	/**
	 * @return the _pushbacks
	 */
	public List<Pushback> get_pushbacks() {
		return _pushbacks;
	}

	/**
	 * @return the _runways
	 */
	public List<Runway> get_runways() {
		return _runways;
	}

	public void initialiseRunway() {
		_runways.forEach( r -> r.initialisePath(_points));		
	}

	private double _minX, _maxX, _minY, _maxY;
	
	public void initialiseBounds() {
		
		_minX = _minY = Integer.MAX_VALUE;
		_maxX = _maxY = Integer.MIN_VALUE;
		
		List<Ligne> allLignes = new ArrayList<Ligne>(_lignes);
		allLignes.addAll(_taxiway);
		allLignes.addAll(_pushbacks);
		trouverLimitesReels(allLignes);
		
        trouverLimitesReels(get_points());
	}
	
	private void trouverLimitesReels(List<? extends Ligne> lignes) {
       
		double l_minReelX = lignes.parallelStream()
										.mapToDouble(l -> l.get_lignePointAPoint().getBounds().getMinX() )
										.min().getAsDouble();
	       
		double l_minReelY = lignes.parallelStream()
											.mapToDouble(l -> l.get_lignePointAPoint().getBounds().getMinY() )
											.min().getAsDouble();
	       
		double l_maxReelX = lignes.parallelStream()
											.mapToDouble(l -> l.get_lignePointAPoint().getBounds().getMaxX() )
											.max().getAsDouble();
	       
		double l_maxReelY = lignes.parallelStream()
											.mapToDouble(l -> l.get_lignePointAPoint().getBounds().getMaxY() )
											.max().getAsDouble();
		

		_minX = Double.min(_minX, l_minReelX);
		_minY = Double.min(_minY, l_minReelY);
		_maxX = Double.max(_maxX, l_maxReelX);
		_maxY = Double.max(_maxY, l_maxReelY);	
	}
	
	private void trouverLimitesReels(Map<String, Point> _points) {
		for (Map.Entry<String, Point> entry : _points.entrySet()) {
			Point point = entry.getValue();
			
			double p_ReelX = point.getX();
			double p_ReelY = point.getY();
			
			_minX = Double.min(_minX, p_ReelX);
			_minY = Double.min(_minY, p_ReelY);
			_maxX = Double.max(_maxX, p_ReelX);
			_maxY = Double.max(_maxY, p_ReelY);    
		}
	}

	/**
	 * @return the _minX
	 */
	public double getMinX() {
		return _minX;
	}

	/**
	 * @return the _maxX
	 */
	public double getMaxX() {
		return _maxX;
	}

	/**
	 * @return the _minY
	 */
	public double getMinY() {
		return _minY;
	}

	/**
	 * @return the _maxY
	 */
	public double getMaxY() {
		return _maxY;
	}
	
	

}
