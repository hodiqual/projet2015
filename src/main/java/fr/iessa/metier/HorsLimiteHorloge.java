/**
 * 
 */
package fr.iessa.metier;

/**
 * Exception si on est hors limite de l'horloge
 * @author hodiqual
 *
 */
public class HorsLimiteHorloge extends Exception {

	public HorsLimiteHorloge(int newIndex) {
		super("Depassement de la limite de l'horloge " + newIndex);
	}

}
