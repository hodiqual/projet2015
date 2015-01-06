package fr.iessa.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.iessa.metier.Avion;

public class ApplicationTest {
	
	@Test
	public void testAvion() {
		
		String nomAvion = "AF1234";
		
		Avion monAvion = new Avion(nomAvion);
		assertEquals(nomAvion, monAvion.getNom());
		
	}

}
