/**
 * 
 */
package fr.iessa.metier.trafic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author hodiqual
 *
 */
public class Trafic {
	
	private Hashtable<Instant, List<Vol> > _instantVersVols;
	
	public void ajout(Vol vol)
	{
		for(Instant instant : vol.getInstantVersCoord().keySet() )
		{
			if(_instantVersVols.containsKey(instant) == false) 
				_instantVersVols.put(instant, new ArrayList<Vol>());
			
			_instantVersVols.get(instant).add(vol);		
		}
	}
	
	public List<Vol> getVols(Instant temps)
	{
		return _instantVersVols.get(temps);
	}
}
