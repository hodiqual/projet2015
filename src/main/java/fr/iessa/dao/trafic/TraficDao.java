/**
 * 
 */
package fr.iessa.dao.trafic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.PointFabrique;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 *
 */
public class TraficDao {
	
	public Trafic charger(String ficname) {
		
		Trafic _trafic = new Trafic();
		
		Set<Vol> vols = null;
		TraficDao traficDao = new TraficDao();
		try (Stream<String> lignes = Files.lines(Paths.get(ficname));) {
			vols = lignes.parallel()
					.map(traficDao::chargerVol)
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
		}
		_trafic.setVols(vols);
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
		
		scan.next();scan.next(); // skip
		
		int secondes = scan.nextInt();
		
		Vol vol = new Vol(typeVol, id, categorie, InstantFabrique.get(secondes));
		
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
