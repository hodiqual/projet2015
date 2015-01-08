package fr.iessa.metier.infra;

import fr.iessa.metier.type.TypeQFU;


/**
 * Classe mère décrivant les QFU.
 * @author duvernal
 *
 */
public class QFU {
	
	String _degre;
	
	TypeQFU _type;


public String toString()
{
	return _degre + _type;
}
}