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
public class DirectionTest {

	/**
	 * Test method for {@link fr.iessa.metier.type.Direction#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("double sens", Direction.DOUBLE.toString());
		assertEquals("sens unique", Direction.SINGLE.toString());
	}

	/**
	 * Test method for {@link fr.iessa.metier.type.Direction#getAbreviation()}.
	 */
	@Test
	public void testGetAbreviation() {
		assertEquals("D", Direction.DOUBLE.getAbreviation());
		assertEquals("S", Direction.SINGLE.getAbreviation());
	}

	/**
	 * Test method for {@link fr.iessa.metier.type.Direction#from(java.lang.String)}.
	 */
	@Test
	public void testFrom() {
		assertSame(Direction.DOUBLE, Direction.from("D"));
		assertSame(Direction.SINGLE, Direction.from("S"));
	}

}
