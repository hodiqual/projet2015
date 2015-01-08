package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

public class LigneDAOTest {

	//l'objet a testé
	private LigneDAO _ligneDAO  = new LigneDAO();

	@Test
	public void testChargerLigne1()
	{
		String ligneDuFichierTexte = "L _ 10 H D -854,-1341;-959,-1466";
		Ligne actual =  _ligneDAO.charger(ligneDuFichierTexte);
		
		assertEquals(10, actual.get_vitesseDeRoulage());
		assertSame(Categorie.HIGH, actual.get_categorie());
		assertEquals(Direction.DOUBLE, actual.get_direction());
		assertTrue( actual.get_lignePointAPoint().contains( new Point(-854,-1341) ) );
		assertTrue( actual.get_lignePointAPoint().contains( new Point(-959,-1466) ) );
		
	}

}
