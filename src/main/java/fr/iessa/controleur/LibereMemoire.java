/**
 * 
 */
package fr.iessa.controleur;

/**
 * Permet de forcer le lancement du garbage collector
 * dans le cas ou beaucoup d'instances ont ete crees
 * et qui n'ont plus de reference.
 * @author hodiqual
 *
 */
public final class LibereMemoire {
	
	/**
	 * Force le garbage collector.
	 */
	public static void free() {
		// Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	}
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }
	  
	  /**
	   * Affiche la memoire courante utilisee par l'application
	   * @return la memoire en Mo
	   */
	  public static String controleMemoire(){

		    // Get the Java runtime
		    Runtime runtime = Runtime.getRuntime();
		    
		    // Run the garbage collector
		    runtime.gc();
		    
		    // Calculate the used memory
		    long memory = runtime.totalMemory() - runtime.freeMemory();
		    
		    // Calculate the used memory
		    memory = runtime.totalMemory() - runtime.freeMemory();
		    System.out.println("Used memory is bytes: " + memory);
		    System.out.println("Used memory is megabytes: "
		        + bytesToMegabytes(memory));
		    
		    return "" + bytesToMegabytes(memory);
	  }
	  
}
