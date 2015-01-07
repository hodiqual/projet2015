/**
 * 
 */
package fr.iessa.metier.type;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author hodiqual
 *
 */
public class CategorieTest {

	/**
	 * Test method for {@link fr.iessa.metier.type.Categorie#getAbreviation()}.
	 */
	@Test
	public void testGetAbreviation() {
		assertEquals("H", Categorie.HIGH.getAbreviation());
		assertEquals("M", Categorie.MEDIUM.getAbreviation());
		assertEquals("L", Categorie.LIGHT.getAbreviation());
	}

	/**
	 * Test method for {@link fr.iessa.metier.type.Categorie#from(java.lang.String)}.
	 */
	@Test
	public void testFrom() {
		assertSame(Categorie.HIGH,Categorie.from("H"));
		assertSame(Categorie.MEDIUM,Categorie.from("M"));
		assertSame(Categorie.LIGHT,Categorie.from("L"));
	}

}
