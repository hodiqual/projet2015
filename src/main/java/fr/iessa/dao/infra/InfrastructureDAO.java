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
 * @author hodiqual
 *
 */
public class InfrastructureDAO {
	
	
	public enum Lookup
	{
		  AEROPORT	(new AeroportDAO())
		, LIGNE		(new LigneDAO())
		, POINT		(new PointDAO())
		, @SuppressWarnings("unchecked")
		PUSHBACK	(new PushbackDAO(LIGNE._dao))
		, RUNWAY	(new RunwayDAO())
		, TAXIWAY	(new TaxiwayDAO(LIGNE._dao))
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
	
	public static Aeroport charger(String ficname)
	{
		Aeroport result = null;
		//Java 7 try-with-ressource -> Scanner implements Closeable -> AUTOCLOSE  gere aussi le cas null
		try(Scanner scan = new Scanner(new FileReader(ficname));) {
			//scan ligne par ligne
			scan.useDelimiter("\n");
			
			result = ((AeroportDAO)Lookup.AEROPORT.get()).charger(scan.next());
			
			//traitement ligne par ligne
			while(scan.hasNext())
			{
				String ligne = scan.next();
				
				Lookup typeLigne = findLookup(ligne);
						
						
				Scanner scanByLine = new Scanner(ligne);
				scanByLine.useDelimiter(";|\n");
				
			
				scanByLine.close();
			}		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

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
