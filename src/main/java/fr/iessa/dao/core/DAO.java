/**
 * 
 */
package fr.iessa.dao.core;

/**
 * Interface ˆ implementer pour le chargement d'un composant de la plateforme.
 * @author hodiqual
 */
public interface DAO < T > {
	
	/**
	 * @param ligneAtraiter  doit decrire un composant de la plateforme.
	 * @return la classe metier correspondant a la description dans lignaAtraiter.
	 */
	public T charger(String ligneAtraiter);

}
