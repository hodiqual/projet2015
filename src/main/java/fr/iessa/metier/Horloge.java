/**
 * 
 */
package fr.iessa.metier;

import java.util.Arrays;
import java.util.Observable;
import java.util.TreeSet;

import fr.iessa.metier.Instant.InstantFabrique;


/**
 * @author hodiqual
 *
 */
public class Horloge extends Observable {
	
	// ********************************************************
	// ********************************************************
	// ********************************************************
	// ********************************************************
	// ********************************************************
	// *********************** ATTENTION A SYNCHRONIZE *************************** //
	// ********************************************************
	// ********************************************************
	// ********************************************************
	// ********************************************************
	// ********************************************************
	

	private final Instant[] _instantsOrdonnees;
	
	private int _sens = 1;
	
	
	// ******************* PEUT ETRE LA METTRE VOLATILE ou SYNCHRONIZED ***************************
	private int _indexInstantCourant;
	
	public Horloge()
	{		
		_instantsOrdonnees = InstantFabrique.getInstants().toArray(new Instant[0]);
		_indexInstantCourant = 0;
	}
	
	public Instant getInstantCourant() {
		return _instantsOrdonnees[_indexInstantCourant];
	}
	
	public void setInstantCourant( Instant instant ) {
		setIndexCourant(Arrays.binarySearch(_instantsOrdonnees, instant));
	}
	
	public void tick() {
		setIndexCourant(_indexInstantCourant+ _sens * 1);
	}
	
	private void setIndexCourant( int newIndex ) {
		_indexInstantCourant = newIndex;
		setChanged();
		notifyObservers(getInstantCourant());
	}
	
	public void setSens( boolean estChronologique ) {
		_sens = estChronologique ? 1 : -1;
	}
	

	public void initialise() {
		setIndexCourant(0);
	}
	
}
