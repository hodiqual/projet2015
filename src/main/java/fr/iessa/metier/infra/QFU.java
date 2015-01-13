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

	
// Accessors 
	
/**
* @return the complete QFU
*/
public String toString()
{
	return _degre + _type;
}


// Mutators
/**
 * @set the _degre
 */
public void setDegre(String _degre) {
	this._degre = _degre;
}
/**
 * @set the _type
 */
public void setType(TypeQFU _type) {
	this._type = _type;
}


}