/**
 * 
 */
package fr.iessa.metier.trafic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.iessa.metier.trafic.Instant.InstantFabrique;

/**
 * @author hodiqual
 */
public class Trafic {
	
	private ArrayList<Vol> _vols = new ArrayList<>();
	
	public void ajout(Vol vol)
	{
		_vols.add(vol);
	}
	
	public Set<Vol> getVols(Instant temps)
	{
		return _vols.stream()
				    .filter( vol -> vol.get_instantVersCoord().containsKey(temps) )
				    .collect(Collectors.toSet());
	}
	
	public Set<Vol> getVolsToAdd(Instant precedent, Instant courant) 
	{
		return _vols.stream()
                    .filter( vol -> vol.get_instantVersCoord().containsKey(courant)
                    		&& !vol.get_instantVersCoord().containsKey(precedent) )
                    .collect(Collectors.toSet());
	}
	
	public Set<Vol> getVolsToRemove(Instant precedent, Instant courant) 
	{
		return _vols.stream()
                .filter( vol -> !vol.get_instantVersCoord().containsKey(courant)
                		&& vol.get_instantVersCoord().containsKey(precedent) )
                .collect(Collectors.toSet());
	}
	
	private Map<Instant, Set<Vol>> _deltaAdd = new Hashtable<>(); 
	private Map<Instant, Set<Vol>> _deltaRemove = new Hashtable<>(); 
	private Map<Instant, Set<Vol>> _current = new Hashtable<>(); 
	
	public void computeDelta()
	{

		Instant previousInstant = null;
		
		for (Entry<Integer, Instant>  entry : InstantFabrique.getAll()) {
			Instant instant = entry.getValue();
			_current.put(instant, getVols(instant));
			if(previousInstant != null)
			{
				_deltaAdd.put(instant, getVolsToAdd(previousInstant,instant));
				_deltaRemove.put(instant, getVolsToRemove(previousInstant,instant));
			}
			else
			{
				_deltaAdd.put(instant, getVols(instant));
			}
			previousInstant = instant;			
		}
		
	}
	
	
}
