package fr.iessa.dao.infra;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import org.junit.Test;

import fr.iessa.dao.infra.InfrastructureDAO.Lookup;
import fr.iessa.metier.infra.Aeroport;

public class InfrastructureDAOTest {

	@Test
	public void testReconnaireUneLigneDeTypePoint() 
	{
		String ligneDecrivantUnPoint = "P F70 0 2193,-299";
		Lookup actual = InfrastructureDAO.findLookup(ligneDecrivantUnPoint);
		assertSame(Lookup.POINT, actual);
	}

	@Test
	public void testReconnaireUneLigneDeTypeLigne() 
	{
		String ligneDecrivantUneLigne = "L _ 3 H S -450,927;-452,927;-461,929;-467,933;-472,938;-474,945;-476,953;-477,962";
		Lookup actual = InfrastructureDAO.findLookup(ligneDecrivantUneLigne);
		assertSame(Lookup.LIGNE, actual);
	}

	@Test
	public void testReconnaireUneLigneDeTypeTaxiway() 
	{
		String ligneDecrivantUneLigne = "L AGN 3 H S -753,777;-758,769;-765,747;-759,724;-745,700;-724,678;-700,660;-677,647;-657,642";
		Lookup actual = InfrastructureDAO.findLookup(ligneDecrivantUneLigne);
		assertSame(Lookup.TAXIWAY, actual);
	}

	@Test
	public void testReconnaireUneLigneDeTypePushback() 
	{
		String ligneDecrivantUneLigne = "L _ -3 H S -657,642;-677,647;-700,660;-724,678;-745,700;-759,724;-765,747;-758,769";
		Lookup actual = InfrastructureDAO.findLookup(ligneDecrivantUneLigne);
		assertSame(Lookup.PUSHBACK, actual);
	}

	@Test
	public void testReconnaireUneLigneDeTypeRunway() 
	{
		String ligneDecrivantUneLigne = "R 09R-27L 09R 27L -2545,1201;1639,1555 K1;K2;K3;K6;K7;Y1;Y10;Y11;Y12;Y13;Y2;Y3;Y4;Y5;Y6;Y7;Y8";
		Lookup actual = InfrastructureDAO.findLookup(ligneDecrivantUneLigne);
		assertSame(Lookup.RUNWAY, actual);
	}
	
	@Test
	public void testCharger() throws FileNotFoundException, NoSuchElementException
	{
		 Aeroport aeroport = InfrastructureDAO.charger("lfpg.txt");
		 assertEquals("LFPG", aeroport.get_nom());
		 
		 assertEquals(1184 , aeroport.get_lignes().size(), 0);
		 aeroport.get_lignes().forEach( l -> assertNotNull(l));
		 
		 assertEquals(1040 , aeroport.get_pushbacks().size(), 0);
		 aeroport.get_pushbacks().forEach( l -> assertNotNull(l));
		 
		 assertEquals(2828 , aeroport.get_taxiway().size(), 0);
		 aeroport.get_taxiway().forEach( l -> assertNotNull(l));
		 
		 assertEquals(4 , aeroport.get_runways().size(), 0);
		 aeroport.get_runways().forEach( l -> assertNotNull(l));
		 
		 assertEquals(585 , aeroport.get_points().size(), 0);
		 aeroport.get_points().forEach( (s,p) -> { assertNotNull(s); assertNotNull(p); 
				 									assertEquals(s,p.get_nom()); } );	 
	}

}
