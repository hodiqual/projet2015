/**
 * 
 */
package fr.iessa.metier;

/**
 * @author hodiqual
 *
 */
public class HorsLimiteHorloge extends Exception {

	public HorsLimiteHorloge(int newIndex) {
		super("Depassement de la limite de l'horloge " + newIndex);
	}

}
