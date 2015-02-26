/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;

/**
 * @author hodiqual
 */
public class Trafic implements Observer {
	
	/**
	 * L'ensemble des vols
	 */
	private Set<Vol> _vols = null;
	
	/**
	 * Les vols par instant
	 */
	private TreeMap<Instant, Set<Vol>> _volsParInstant = null;
	
	/**
	 * La liste des collisions recensees.
	 */
	private List<Collision> _collisions = null;
	
	
	/**
	 * Trouve les collisions
	 */
	public void computeCollision()
	{		
		ConcurrentHashMap<Instant, Map<Point,List<Vol> > > volsParCoordParInstant
		 = new ConcurrentHashMap<Instant, Map<Point,List<Vol>> >();
		_volsParInstant.keySet().parallelStream().forEach( i -> volsParCoordParInstant.put(i, new HashMap<Point,List<Vol>>()));
		_volsParInstant.entrySet().parallelStream()
								  .forEach( e -> volsParCoordParInstant.put(e.getKey(),
										  						e.getValue().stream()
										  									.collect(Collectors.groupingBy(v -> v.getCoord(e.getKey())))) 
										  );
		
		_collisions = new ArrayList<Collision>();
		
		volsParCoordParInstant.entrySet().parallelStream().forEach(e ->
		{
			e.getValue().entrySet().stream().filter( z -> z.getValue().size() > 1 )
			.forEach(z -> 
			{	
				z.getValue().forEach(v -> v.setADesCollisions(true));
				_collisions.add(new Collision(e.getKey(), z.getKey(), z.getValue()));
			}
			);
		}
		 );
		
		
	}
	
	/**
	 * Range les vols par instant
	 */
	private void computeDelta()
	{
		TreeSet<Instant> allOrderedInstants = InstantFabrique.getInstants();
		ConcurrentMap<Instant, Set<Vol>> volsParInstant = allOrderedInstants.parallelStream()
				 .collect( Collectors.toConcurrentMap( Function.identity()
													   ,(Instant i) -> _vols.stream()
																			.filter(v -> v.estSurLaPlateforme(i) )
																			.collect( Collectors.toSet() ) ) );
		
		_volsParInstant = new TreeMap<Instant, Set<Vol>>(volsParInstant);
	}

	/**
	 * @param vols  the vols to set.
	 */
	public void setVols(Set<Vol> vols) {
		_vols = vols;
		computeDelta();
		computeCollision();
	}

	/**
	 * Observe le temps qui defile et met a jour les vols en consequence.
	 */
	@Override
	public void update(Observable o, Object i) {
		Instant instant = (Instant)i;
		Set<Vol> volsAInstant = _volsParInstant.get(instant);
		volsAInstant.stream().forEach( v -> v.updateCoordCourantes(instant) );
		
		_vols.parallelStream().filter( v -> volsAInstant.contains(v) == false )
							  .forEach( v -> v.updateCoordCourantes(null) );
	}

	/**
	 * @param filtre criteres de recherche
	 * @return les vols selon les criteres de recherche
	 */
	public Set<Vol> getVols(Predicate<Vol> filtre) {
		return _vols.stream().filter(filtre).collect(Collectors.toSet());
	}
	
	/**
	 * @return L'ensemble des vols qui composent le trafic
	 */
	public Set<Vol> getVols() {
		return _vols;	
	}

	/**
	 * @return L'ensemble des vols sur la plateforme groupés par instant.
	 */
	public TreeMap<Instant, Set<Vol>> getVolsParInstant() {
		return _volsParInstant;
	}	
	
	/**
	 * @return L'ensemble des vols qui composent le trafic a l'instant @param instant.
	 */
	public Set<Vol> getVols(Instant instant) {
		return _volsParInstant.get(instant);
	}


	/**
	 * @return L'ensemble des collisions du trafic.
	 */
	public List<Collision> getCollisions() {
		return _collisions;
	}
	
}
