/**
 * 
 */
package fr.iessa.dao.infra;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Aeroport;

/**
 * @author hodiqual
 *
 */
public class AeroportDAO implements DAO<Aeroport> {

	@Override
	public Aeroport charger(String ligneAtraiter) {
		
		return new Aeroport();
	}

}
