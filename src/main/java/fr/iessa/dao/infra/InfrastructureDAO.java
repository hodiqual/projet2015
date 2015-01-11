/**
 * 
 */
package fr.iessa.dao.infra;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Aeroport;

/**
 * Charge l'infrastructure d'une plateforme aerienne a partir d'un fichier
 * formatt� ligne par ligne d�crivant ses points, lignes, pushbacks, runway,
 * taxiway.
 * @author hodiqual
 *
 */
public class InfrastructureDAO {
	
	/** Singleton des instances des chargeurs. */ 
	public enum Lookup
	{
		  AEROPORT	(new AeroportDAO())
		, POINT		(new PointDAO())
		, LIGNE		(new LigneDAO())
		, @SuppressWarnings("unchecked")
		  PUSHBACK	(new PushbackDAO(LIGNE._dao))
		, @SuppressWarnings("unchecked")
		  TAXIWAY	(new TaxiwayDAO(LIGNE._dao))
		, RUNWAY	(new RunwayDAO())
		;
		
		private DAO _dao;
		
		private Lookup(DAO dao)
		{
			_dao = dao;
		}
		
		public DAO get()
		{
			return _dao;
		}
		
	}
	
	/**
	 * 
	 * @param ficname fichier format� contenant la description de la plateforme.
	 * @return l'infracstructure Aeroport contenant tous ces points,
	 *         lignes, pushbacks, taxiways, et runways.
	 */
	public static Aeroport charger(String ficname)
	{
		Aeroport result = null;
		
		try(Scanner scan = new Scanner(new FileReader(ficname));) {
			//scan ligne par ligne
			scan.useDelimiter("\n");
			
			result = ((AeroportDAO)Lookup.AEROPORT.get()).charger(scan.next());
			
			//traitement ligne par ligne
			while(scan.hasNext())
			{
				String ligne = scan.next();
				
				//on trouve le type de ligne
				Lookup typeLigne = findLookup(ligne);
				
				//puis on charge la ligne en fonction de son type
				switch (typeLigne) {
				case POINT:
					result.add( ((PointDAO)typeLigne.get()).charger(ligne));
					break;
				case LIGNE:
					result.add( ((LigneDAO)typeLigne.get()).charger(ligne));
					break;
				case TAXIWAY:
					result.add( ((TaxiwayDAO)typeLigne.get()).charger(ligne));
					break;
				case PUSHBACK:
					result.add( ((PushbackDAO)typeLigne.get()).charger(ligne));
					break;
				case RUNWAY:
					result.add( ((RunwayDAO)typeLigne.get()).charger(ligne));
					break;
				}
				
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	/** @return  le type d'infrastructure de la plateforme d�crite par la ligne du fichier. */
	public static Lookup findLookup(String ligne) {
		
		if(ligne.startsWith("P"))
		{
			//si Point, la ligne commence par P
			return Lookup.POINT;
		}
		else if(ligne.startsWith("L"))
		{
			if(ligne.matches("L _ [0-9].*")) 
			{
				//si Ligne, la ligne commence par P _ [0-9]
				return Lookup.LIGNE;
			} 
			else if (ligne.matches("L [A-Za-z].*")) 
			{
				//si Ligne, la ligne commence par P [a-Z]
				return Lookup.TAXIWAY;				
			}
			else if(ligne.matches("L _ -.*"))
				return Lookup.PUSHBACK;
		}
		else if(ligne.startsWith("R"))
		{
			//si RUNWAY, la ligne commence par R
			return Lookup.RUNWAY;
		}
			
		return null;
	}
}
