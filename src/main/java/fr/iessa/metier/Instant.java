package fr.iessa.metier;

import java.text.DecimalFormat;
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
	private final String _affichage;
	private static final DecimalFormat f = new DecimalFormat("00");
	
	private Instant(int secondes)
	{
		_secondes = secondes;
		
		StringBuffer buffer = new StringBuffer(8);
		buffer.append( f.format(_secondes/3600) +":");
		int reste = _secondes%3600;
		buffer.append( f.format(reste/60) +":");
		buffer.append( f.format(reste%60) );
		
		_affichage = buffer.toString();
	}
	
	public int getSeconds() {
		return _secondes;
	}
	
	public String toString()  {
		return _affichage;
	}

	@Override
	public int compareTo(Instant o) {
		return this._secondes - o._secondes;
	}
	
	public static class InstantFabrique {
		
		private final static TreeMap<Integer, Instant> _instantsSingleton = new TreeMap<>();
		public final static int _pasEntreInstant = 5;
		static {
			for (int i = 0; i < 24*3600; i+=_pasEntreInstant) {
				get(i);
			}
		}
		
		public static Instant get(int secondes) {
			
			Instant uniqueInstant = _instantsSingleton.get(secondes);
			
			if(uniqueInstant==null)
			{
				uniqueInstant = new Instant(secondes);
				_instantsSingleton.put(secondes, uniqueInstant);
			}
			
			return uniqueInstant;
		}
		
		public static Instant getPreviousInstant(Instant instant) {
			return get(instant.getSeconds()-_pasEntreInstant);
		}
		
		public static Set<Entry<Integer, Instant>> getAll() {
			return _instantsSingleton.entrySet();
		}
		
		public static TreeSet<Instant> getInstants() {
			return new TreeSet<Instant>(_instantsSingleton.values());
		}
		
		public static Instant getInstantLePlusProche(int secondes)  {
			int ecartArrondiParDefaut = Integer.MAX_VALUE;
			Integer arrondiParDefaut = _instantsSingleton.floorKey(secondes);
			if(arrondiParDefaut!=null)
				ecartArrondiParDefaut = secondes - arrondiParDefaut;
			int ecartArrondiParExces = Integer.MAX_VALUE;
			Integer arrondiParExces = _instantsSingleton.ceilingKey(secondes);
			if(arrondiParExces!=null)
				ecartArrondiParExces  = arrondiParExces - secondes;
			
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
