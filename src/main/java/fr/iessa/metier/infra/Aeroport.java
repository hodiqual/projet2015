/**
 * 
 */
package fr.iessa.metier.infra;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author hodiqual
 *
 */
public class Aeroport {
	
	private String _nom;
	
	private List<Point> _points = new ArrayList<Point>(100);
	
	private List<Ligne> _lignes = new ArrayList<Ligne>(100);
	
	private List<Taxiway> _taxiway = new ArrayList<Taxiway>(100);
	
	private List<Pushback> _pushbacks = new ArrayList<Pushback>(100);
	
	private List<Runway> _runways = new ArrayList<Runway>();
	
	
	public Aeroport(String nom)
	{
		_nom = nom;
	}
	
	public void add(Point p){
		_points.add(p);
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
	
	
	

}
