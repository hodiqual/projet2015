/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;

/**
 * @author hodiqual
 */
public class Trafic implements Observer {
	
	private Set<Vol> _vols = null;
	

	public Set<Vol> getVolsToAdd(Instant courant) 
	{
		Instant precedent = InstantFabrique.getInstants().lower(courant);
		
		return _vols.stream()
                    .filter( vol -> vol.estSurLaPlateforme(courant)
                    		&& !vol.estSurLaPlateforme( precedent ) )
                    .collect(Collectors.toSet());
	}
	
	public Set<Vol> getVolsToRemove(Instant courant) 
	{
		Instant precedent = InstantFabrique.getInstants().lower(courant);
		
		return _vols.stream()
                .filter( vol -> !vol.estSurLaPlateforme(courant)
                		&& vol.estSurLaPlateforme(precedent) )
                .collect(Collectors.toSet());
	}
	

	private TreeMap<Instant, Set<Vol>> _volsParInstant;
	private TreeMap<Instant, Set<Vol>> _volsARajouterParInstant; 
	private TreeMap<Instant, Set<Vol>> _volsASupprParInstant; 
	
	public void computeCollision()
	{
		ConcurrentHashMap<Instant, Map<Point,List<Vol> > > collisions
		 = new ConcurrentHashMap<Instant, Map<Point,List<Vol>> >();
		_volsParInstant.keySet().parallelStream().forEach( i -> collisions.put(i, new HashMap<Point,List<Vol>>()));
		_volsParInstant.entrySet().parallelStream()
								  .forEach( e -> collisions.put(e.getKey(),
										  						e.getValue().stream()
										  									.collect(Collectors.groupingBy(v -> v.getCoord(e.getKey())))) 
										  );
		
		collisions.entrySet().parallelStream().forEach(e ->
		{
			e.getValue().entrySet().stream().filter( z -> z.getValue().size() > 1 )
			.forEach(z -> 
			{
				System.out.println( "Collision: " + e.getKey().getSeconds() + " " + z.getKey() + " " + z.getValue());
				z.getValue().forEach(v -> v.setADesCollisions(true));
			}
			);
		}
		 );
	}
	
	
	private void computeDelta()
	{
		
		TreeSet<Instant> allOrderedInstants = InstantFabrique.getInstants();
		ConcurrentMap<Instant, Set<Vol>> volsParInstant = allOrderedInstants.parallelStream()
				 .collect( Collectors.toConcurrentMap( Function.identity()
													   ,(Instant i) -> _vols.stream()
																			.filter(v -> v.estSurLaPlateforme(i) )
																			.collect( Collectors.toSet() ) ) );
		
		_volsParInstant = new TreeMap<Instant, Set<Vol>>(volsParInstant);
		/*_volsARajouterParInstant = new TreeMap<Instant, Set<Vol>>();
		allOrderedInstants.parallelStream()
								 .filter(i -> InstantFabrique.getInstants().lower(i) != null)
								 .forEach(i -> 
								 	_volsARajouterParInstant.put(i,getVolsToAdd(i)));*/
		
	/*	volsParInstant.entrySet().parallelStream()
		 .filter(e -> InstantFabrique.getInstants().lower(e.getKey()) != null)
		 .forEach(e -> 
		 	_volsASupprParInstant.get(InstantFabrique.getInstants().lower(e.getKey())).removeAll(e.getValue())); */
		
		
		//Set<Vol> toAdd.removeAll(volsPreviousInstant);
		//Set<Vol> toAdd.
		//_volsARajouterParInstant = new TreeMap<Instant, Set<Vol>>(volsParInstant);
		//_volsASupprParInstant = new TreeMap<Instant, Set<Vol>>(volsParInstant);
		
		//Instant[] instantsArray = (Instant[]) allOrderedInstants.toArray();
		//Set<Vol>[] aRajouter = (Set<Vol>[]) _volsARajouterParInstant.values().toArray();
		//volsParInstant.entrySet().parallelStream()
		//						 .forEach( e -> aRajouter[Arrays.binarySearch(instantsArray, e.getKey())+1]. );
		
		
		//ConcurrentMap<Instant, Set<Vol>> volsARajouterParInstant= new ConcurrentMap<Instant, Set<Vol>>(volsParInstant);
		//ConcurrentMap<Instant, Set<Vol>> volsASupprParInstant 	= new ConcurrentMap<Instant, Set<Vol>>(volsParInstant);
		
		
		/*Instant previousInstant = null;
		
		for (Entry<Integer, Instant>  entry : InstantFabrique.getAll()) {
			Instant instant = entry.getValue();
			if(previousInstant != null)
			{
				_volsARajouterParInstant.put(instant, getVolsToAdd(previousInstant,instant));
				_volsASupprParInstant.put(instant, getVolsToRemove(previousInstant,instant));
			}
			else
			{
				_volsARajouterParInstant.put(instant, getVols(instant));
			}
			previousInstant = instant;			
		}*/	
	}

	public void setVols(Set<Vol> vols) {
		_vols = vols;
		computeDelta();
		computeCollision();
	}

	@Override
	public void update(Observable o, Object i) {
		Instant instant = (Instant)i;
		Set<Vol> volsAInstant = _volsParInstant.get(instant);
		volsAInstant.stream().forEach( v -> v.updateCoordCourantes(instant) );
		
		_vols.parallelStream().filter( v -> volsAInstant.contains(v) == false )
							  .forEach( v -> v.updateCoordCourantes(null) );
	}

	/**
	 * @return L'ensemble des vols qui composent le trafic
	 */
	public Set<Vol> getVols() {
		return _vols;	
	}

	/**
	 * @return L'ensemble des vols qui composent le trafic a l'instant @param instant.
	 */
	public Set<Vol> getVols(Instant instant) {
		return _volsParInstant.get(instant);
	}

	public Set<Vol> getVols(Predicate<Vol> filtre) {
		// TODO Auto-generated method stub
		return _vols.stream().filter(filtre).collect(Collectors.toSet());
	}
	
	
}
