package fr.iessa.metier;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * @author thomasra
 *
 */
public class Instant implements Comparable<Instant>{
	
	private final int _secondes;
	
	private final Date _date;
	
	private Instant(int secondes)
	{
		_secondes = secondes;
		_date = new Date();
	}
	
	public int getSeconds() {
		return _secondes;
	}

	@Override
	public int compareTo(Instant o) {
		return this._secondes - o._secondes;
	}
	
	public static class InstantFabrique {
		
		private static TreeMap<Integer, Instant> _instantsSingleton = new TreeMap<>();
		
		public static Instant get(int secondes) {
			
			Instant uniqueInstant = _instantsSingleton.get(secondes);
			
			if(uniqueInstant==null)
			{
				uniqueInstant = new Instant(secondes);
				_instantsSingleton.put(secondes, uniqueInstant);
			}
			
			return uniqueInstant;
		}
		
		public static Set<Entry<Integer, Instant>> getAll() {
			return _instantsSingleton.entrySet();
		}
		
		public static TreeSet<Instant> getInstants() {
			return new TreeSet<Instant>(_instantsSingleton.values());
		}
		
		public static Instant getInstantLePlusProche(int secondes)  {
			int arrondiParDefaut = _instantsSingleton.floorKey(secondes);
			int ecartArrondiParDefaut = secondes - arrondiParDefaut;
			int arrondiParExces = _instantsSingleton.ceilingKey(secondes);
			int ecartArrondiParExces  = arrondiParExces - secondes;
			if ( ecartArrondiParExces < ecartArrondiParDefaut) 
				return _instantsSingleton.get(arrondiParExces);
			else
				return _instantsSingleton.get(arrondiParDefaut);
		}
		
		public static Instant getMinimumInstant() {
			return _instantsSingleton.firstEntry().getValue();
		}
		
		public static Instant getMaximumInstant() {
			return _instantsSingleton.lastEntry().getValue();
		}
		
	}

}
