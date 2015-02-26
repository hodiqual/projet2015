/**
 * 
 */
package fr.iessa.dao.infra;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Aeroport;

/**
 * Creer un Aeroport a partir d'une description texte.
 * @author hodiqual
 */
public class AeroportDAO implements DAO<Aeroport> {

	/**
 	* @param ligneAtraiter contient le nom de l'aeroport.
 	*/
	public Aeroport charger(String ligneAtraiter) {		
		return new Aeroport(ligneAtraiter.trim());
	}

}
