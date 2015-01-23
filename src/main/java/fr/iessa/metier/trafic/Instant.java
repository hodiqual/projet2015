package fr.iessa.metier.trafic;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * @author thomasra
 *
 */
public class Instant {
	
	private final int _secondes;
	
	private final Date _date;
	
	private Instant(int secondes)
	{
		_secondes = secondes;
		_date = new Date();
	}
	
	public static class InstantFabrique {
		
		private static Map<Integer, Instant> _instantsSingleton = new Hashtable<>();
		
		public static Instant get(int secondes) {
			
			Instant uniqueInstant = _instantsSingleton.get(secondes);
			
			if(uniqueInstant==null)
			{
				uniqueInstant = new Instant(secondes);
				_instantsSingleton.put(secondes, uniqueInstant);
			}
			
			return uniqueInstant;
		}
		
	}

}
