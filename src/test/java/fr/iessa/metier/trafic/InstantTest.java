package fr.iessa.metier.trafic;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;

/**
 * 
 * @author thomasra
 *
 */
public class InstantTest {

	@Test
	public void testGet() {
		
		int secondes = 1440;
		
		Instant premiereDemande = InstantFabrique.get(secondes);
		
		Instant deuxiemeDemande = InstantFabrique.get(secondes);
		
		assertSame("les deux demandes doivent retourner la meme instance d'Instant", 
						premiereDemande, deuxiemeDemande);
	}

}
