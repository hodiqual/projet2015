/**
 * 
 */
package fr.iessa.dao.trafic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.regex.Pattern;

import fr.iessa.metier.trafic.Instant;
import fr.iessa.metier.trafic.PointFabrique;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.trafic.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 *
 */
public class TraficDao {
	
	public Trafic _trafic = new Trafic();
	
	public Trafic charger(String ficname) {
		
		try(Scanner scan = new Scanner(new FileReader(ficname));) {
			//scan ligne par ligne
			scan.useDelimiter("\n");
			
			//traitement ligne par ligne
			while(scan.hasNext())
			{
				Vol vol = chargerVol(scan.next());
				_trafic.ajout(vol);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _trafic;
	}
	
	/**
	 * @param ligneFormatVol String decrivant un vol, le format attendu 
	 * "DEP BCS1748 M M17 27L 1440 _ -955,-1104 -946,-1116 -938,-1128 -930,-1141".
	 * @return instance de la classe Vol decrit par la String ligneFormatVol.
	 */
	public Vol chargerVol(String ligneFormatVol)
	{
		Scanner scan = new Scanner(ligneFormatVol);
		scan.useDelimiter(" |\n");
		
		TypeVol typeVol = TypeVol.valueOf(scan.next());
		String id = scan.next();
		Categorie categorie = Categorie.from(scan.next());
		
		Vol vol = new Vol(typeVol, id, categorie);

		scan.next();scan.next(); // skip
		
		int secondes = scan.nextInt();
		
		scan.next(); // skip
		while(scan.hasNext()) //boucle sur les coordonnees.
		{		
			vol.ajout( InstantFabrique.get(secondes)
					 , PointFabrique.get( scan.next() ) );
			secondes += 5;
		}
		
		return 	vol;
	}
}
