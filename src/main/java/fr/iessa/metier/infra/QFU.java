package fr.iessa.metier.infra;

import fr.iessa.metier.type.TypeQFU;


/**
 * Classe mère décrivant les QFU.
 * @author duvernal
 * @see <a href="http://fr.wikipedia.org/wiki/QFU">http://fr.wikipedia.org/wiki/QFU</a>
 */
public class QFU {
	
	/** Le degré de la piste */
	String _degre;
	
	/** Le type de QFU (gauche ou droite) */
	TypeQFU _type;

	
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