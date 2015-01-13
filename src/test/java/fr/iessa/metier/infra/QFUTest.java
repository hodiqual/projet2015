/**
 * 
 */
package fr.iessa.metier.infra;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.iessa.metier.Avion;
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
		QFU qfutest = new QFU();
		qfutest.setDegre("09");
		qfutest.setType(TypeQFU.R);
		assertEquals("09R", qfutest.toString());
		
	}

	

}
