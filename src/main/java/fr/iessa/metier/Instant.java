package fr.iessa.metier;

import java.text.DecimalFormat;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Decrit un instant.
 * @author hodiqual
 *
 */
public class Instant implements Comparable<Instant>{
	
	/**
	 * Nombre de secondes ecoulees depuis 00:00:00.
	 */
	private final int _secondes;
	
	/**
	 * La correspondance du nombre de seconde en format HH:MM:SS
	 */
	private final String _affichage;
	
	/**
	 * Format pour l'affichage des secondes
	 */
	private static final DecimalFormat f = new DecimalFormat("00");
	
	/**
	 * Constructeur.
	 * @param secondes Nombre de secondes ecoulees depuis 00:00:00.
	 */
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
	
	/**
	 * 
	 * @return _secondes
	 */
	public int getSeconds() {
		return _secondes;
	}
	
	@Override
	public String toString()  {
		return _affichage;
	}

	@Override
	public int compareTo(Instant o) {
		return this._secondes - o._secondes;
	}
	
	/**
	 * Assure une seule instance par Instant
	 * @author hodiqual
	 */
	public static class InstantFabrique {
		
		/**
		 * Container des uniques instances de chaque Instant.
		 */
		private final static TreeMap<Integer, Instant> _instantsSingleton = new TreeMap<>();
		
		/**
		 * Le pas entre chaque instant
		 */
		public final static int _pasEntreInstant = 5;
		
		/**
		 * Initialisation de tous les instants en 24h.
		 */
		static {
			for (int i = 0; i < 24*3600; i+=_pasEntreInstant) {
				get(i);
			}
		}
		
		/**
		 * @param secondes nombre de secondes ecoulees depuis 00:00:00.
		 * @return Instant correspondant a secondes.
		 */
		public static Instant get(int secondes) {
			
			Instant uniqueInstant = _instantsSingleton.get(secondes);
			
			if(uniqueInstant==null)
			{
				uniqueInstant = new Instant(secondes);
				_instantsSingleton.put(secondes, uniqueInstant);
			}
			
			return uniqueInstant;
		}
		
		/**
		 * @return tous les instants tries de facon ordonnee dans un set.
		 */
		public static TreeSet<Instant> getInstants() {
			return new TreeSet<Instant>(_instantsSingleton.values());
		}
		
		/**
		 * Retourne l'instant le plus proche (a _pasEntreInstant pres)
		 * @param secondes nombre de secondes depuis 00:00:00
		 * @return
		 */
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
		
		/**
		 * @return le premier instant
		 */
		public static Instant getMinimumInstant() {
			return _instantsSingleton.firstEntry().getValue();
		}
		

		
		/**
		 * @return le dernier instant
		 */
		public static Instant getMaximumInstant() {
			return _instantsSingleton.lastEntry().getValue();
		}
		
	}

}
