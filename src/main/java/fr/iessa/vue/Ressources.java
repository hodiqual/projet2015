package fr.iessa.vue;

import java.net.URL;

public final class Ressources {
	

	  public static final String GRASS_1 = "Grass_1.png";
	  public static final String GRASS_2 = "Grass_2.jpg";
	  public static final String GRASS_6 = "Grass_6.png";
	  
	  private static final String PREFIX = "fr/iessa/vue/";
	  
	  public static URL getResource(String name) {
		    final URL result = Thread.currentThread().getContextClassLoader().getResource(PREFIX + name);
		    if (result == null)
		      throw new IllegalStateException("Unable to load resource: " + name);
		    else
		      return result;
		  }
}
