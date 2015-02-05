package fr.iessa.vue;

import java.net.URL;

public final class Ressources {
	

	  public static final String GRASS_1 = "Grass_1.png";
	  public static final String GRASS_2 = "Grass_2.jpg";
	  public static final String GRASS_6 = "Grass_6.png";
	  
	  public static final String AVION_HIGH_BLUE   	 = "avion_h_b.png";
	  public static final String AVION_MEDIUM_BLUE   = "avion_m_b.png";
	  public static final String AVION_LIGHT_BLUE    = "avion_l_b.png";
	  
	  private static final String PREFIX = "fr/iessa/vue/";
	  
	  public static URL get(String name) {
		    final URL result = Thread.currentThread().getContextClassLoader().getResource(PREFIX + name);
		    if (result == null)
		      throw new IllegalStateException("Unable to load resource: " + name);
		    else
		      return result;
		  }
}
