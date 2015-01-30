/**
 * 
 */
package fr.iessa.metier;

import java.util.Arrays;
import java.util.Observable;

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
	
	
	// ******************* PEUT ETRE LA METTRE VOLATILE ou SYNCHRONIZED ***************************
	private int _indexInstantCourant;
	
	public Horloge()
	{
		_instantsOrdonnees = (Instant[]) InstantFabrique.getInstants().toArray();
		_indexInstantCourant = 0;
	}
	
	public Instant getInstantCourant() {
		return _instantsOrdonnees[_indexInstantCourant];
	}
	
	public void setInstantCourant( Instant instant ) {
		setIndexCourant(Arrays.binarySearch(_instantsOrdonnees, instant));
	}
	
	public void tick() {
		setIndexCourant(_indexInstantCourant+1);
	}
	
	private void setIndexCourant( int newIndex ) {
		_indexInstantCourant = newIndex;
		setChanged();
		notifyObservers(getInstantCourant());
	}

	public void initialise() {
		setIndexCourant(0);
	}
	
}
