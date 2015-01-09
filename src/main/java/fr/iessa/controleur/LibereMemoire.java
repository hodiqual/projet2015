/**
 * 
 */
package fr.iessa.controleur;

/**
 * Permet de forcer le lancement du garbage collector
 * dans le cas ou beaucoup d'instances ont été créés
 * et qui n'ont plus de référence.
 * @author hodiqual
 *
 */
public final class LibereMemoire {
	public static void free() {
		// Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	}
}
