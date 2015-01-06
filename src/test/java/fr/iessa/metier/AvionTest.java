package fr.iessa.metier;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.iessa.metier.Avion;

public class AvionTest {

	@Test
	public void testAvion() {
		
		String nomAvion = "AF1234";
		
		Avion monAvion = new Avion(nomAvion);
		assertEquals(nomAvion, monAvion.getNom());
		
	}

	@Test
	public void testSetNom() {
		
		String nomAvion = "AF1234";
		
		Avion monAvion = new Avion("Qqconque");
		monAvion.setNom(nomAvion);
		assertEquals(nomAvion, monAvion.getNom());
	}

}
