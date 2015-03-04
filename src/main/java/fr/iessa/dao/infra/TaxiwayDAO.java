/**
 * 
 */
package fr.iessa.dao.infra;

import java.util.Scanner;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Taxiway;

/**
 * Creer un taxiway a partir d'une description texte.
 * @author hodiqual
 */
public class TaxiwayDAO implements DAO<Taxiway> {

	LigneDAO _daoLigne;
	
	public TaxiwayDAO(DAO<Ligne> dao) {
		_daoLigne = (LigneDAO) dao;
	}

	
	@Override
	/**
	 * Charge en memoire une ligne de texte decrivant un taxiway 
	 * @param ligneAtraiter: decrit le taxiway de la maniere suivante
	 * "L K4 10 H D -858,1424;-898,1293"
	 */
	public Taxiway charger(String ligneAtraiter) {
		Scanner scan = new Scanner(ligneAtraiter);
		scan.useDelimiter(" |\n");
		scan.next(); //skip 'L'
		Taxiway result = new Taxiway( scan.next(), 
										_daoLigne.chargerVitesse(scan),
										_daoLigne.chargerCategorie(scan), 
										_daoLigne.chargerDirection(scan), 
										_daoLigne.chargerPoints(scan) );
		scan.close();
		return result;
	}

}
