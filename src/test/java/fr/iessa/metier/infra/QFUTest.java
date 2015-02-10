/**
 * 
 */
package fr.iessa.metier.infra;

import static org.junit.Assert.*;

import org.junit.Test;
import fr.iessa.metier.type.TypeQFU;

/**
 * @author duvernal
 *
 */
public class QFUTest {
	/**
	 * Test method for {@link fr.iessa.metier.infra.QFU#toString()}.
	 */
	@Test
	public void testToString() {
		QFU qfutest = new QFU("09", TypeQFU.R);
		assertEquals("09R", qfutest.toString());
		
	}

	

}
