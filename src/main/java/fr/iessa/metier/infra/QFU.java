package fr.iessa.metier.infra;

import fr.iessa.metier.type.TypeQFU;


/**
 * Classe mere decrivant les QFU.
 * @author duvernal
 * @see <a href="http://fr.wikipedia.org/wiki/QFU">http://fr.wikipedia.org/wiki/QFU</a>
 */
public class QFU {
	
	/** Le degre de la piste */
	String _degre;
	
	/** Le type de QFU (L ou R) */
	TypeQFU _type;

	/** Definit un QFU
	* @param degre
	* Le degre de la piste
	* @param type 
	* Le type de QFU (L ou R)
	*/	
// Constructor
public QFU(String degre, TypeQFU type) {
	_degre = degre;
	_type = type;
	}

// Accessors 
	
/**
* @return the complete QFU
*/
public String toString()
{
	return _degre + _type;
}

}