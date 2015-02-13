package fr.iessa.vue;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import org.junit.Test;

import fr.iessa.dao.infra.AeroportDAO;
import fr.iessa.dao.infra.PlateformeDAO;
import fr.iessa.metier.infra.Aeroport;

public class EchelleTest {

	@Test
	public void testGetAffineTransform() throws FileNotFoundException, NoSuchElementException {
		
		Echelle echelle = new Echelle();
		
		echelle.setLimitesReelles(-3879, 4571, -2219, 1880);

		double[] expectedTransAffine = { 0.09248554913294797,               0.0, 0.0,
										 -0.0902535473365899, 368.0, 178.702023726448 };
		double[] actualTransAffine = new double[6];
		echelle.getAffineTransform().getMatrix(actualTransAffine);
		assertArrayEquals(expectedTransAffine, actualTransAffine, 0.000001);
	}
}


