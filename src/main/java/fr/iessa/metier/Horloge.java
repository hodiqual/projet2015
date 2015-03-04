/**
 * 
 */
package fr.iessa.metier;

import java.util.Arrays;
import java.util.Observable;
import java.util.TreeSet;

import fr.iessa.metier.Instant.InstantFabrique;


/**
 * Modelise le temps qui defile
 * @author hodiqual
 *
 */
public class Horloge extends Observable {

	/**
	 * Les instants ordonnees selon le nombre de secondes croissant
	 */
	private final Instant[] _instantsOrdonnees;
	
	private int _sens = 1;
	
	private int _indexInstantCourant;
	
	/**
	 * Constructeur
	 */
	public Horloge()
	{		
		_instantsOrdonnees = InstantFabrique.getInstants().toArray(new Instant[0]);
		_indexInstantCourant = 0;
	}
	
	/**
	 * 
	 * @return l'instant courant.
	 */
	public Instant getInstantCourant() {
		return _instantsOrdonnees[_indexInstantCourant];
	}
	
	/**
	 * Force l'instant courant
	 * @param instant instant a laquelle on desire fixer l'instant courant.
	 * @throws HorsLimiteHorloge si l'instant est hors limite
	 */
	public void setInstantCourant( Instant instant ) throws HorsLimiteHorloge {
		setIndexCourant(Arrays.binarySearch(_instantsOrdonnees, instant));
	}
	
	/**
	 * Avance ou recule vers l'instant suivant/precedant selon le _sens
	 * @throws HorsLimiteHorloge si l'instant est hors limite
	 */
	public void tick() throws HorsLimiteHorloge {
		setIndexCourant(_indexInstantCourant+ _sens * 1);
	}
	
	/**
	 * Fixe l'instant courant
	 * @param newIndex l'index de l'instant desire.
	 * @throws HorsLimiteHorloge
	 */
	private void setIndexCourant( int newIndex ) throws HorsLimiteHorloge {
		if( newIndex < 0 || newIndex >= _instantsOrdonnees.length )
			throw new HorsLimiteHorloge(newIndex);
		_indexInstantCourant = newIndex;
		setChanged();
		notifyObservers(getInstantCourant());
	}
	
	/**
	 * fixe le sens du defilement des instants
	 * @param estChronologique true si sens chronologique, false si sens inverse
	 */
	public void setSens( boolean estChronologique ) {
		_sens = estChronologique ? 1 : -1;
	}
	
	/**
	 * Initialise l'instant 0.
	 * @throws HorsLimiteHorloge si hors temps limite.
	 */
	public void initialise() throws HorsLimiteHorloge {
		setIndexCourant(0);
	}
	
}
