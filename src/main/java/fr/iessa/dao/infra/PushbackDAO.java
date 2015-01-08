/**
 * 
 */
package fr.iessa.dao.infra;

import java.util.Scanner;

import fr.iessa.dao.core.DAO;
import fr.iessa.dao.infra.PushbackDAO;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Pushback;

/**
 * @author hodiqual
 *
 */
public class PushbackDAO implements DAO<Pushback> {
	
	LigneDAO _daoLigne;
	
	public PushbackDAO(DAO<Ligne> _dao)
	{
		_daoLigne = (LigneDAO) _dao;
	}

	@Override
	/**
	 * Charge en memoire une ligne de texte decrivant un pushback 
	 * ligneAtraiter: decrit le pushback de la maniere suivante
	 * "L _ -3 H S -657,642;-677,647;-700,660;-724,678;-745,700;-759,724;-765,747;-758,769"
	 */
	public Pushback charger(String ligneAtraiter) {
		Scanner scan = new Scanner(ligneAtraiter);
		scan.useDelimiter(" |\n");
		scan.next(); //skip L
		scan.next(); //skip _
		Pushback result = new Pushback( _daoLigne.chargerVitesse(scan),
										_daoLigne.chargerCategorie(scan), 
										_daoLigne.chargerDirection(scan), 
										_daoLigne.chargerPoints(scan) );
		scan.close();
		return result;
	}

}
