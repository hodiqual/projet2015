package fr.iessa.vue;

import java.net.URL;

public final class Ressources {
	

	  public static final String GRASS_1 = "Grass_1.png";
	  public static final String GRASS_2 = "Grass_2.jpg";
	  public static final String GRASS_6 = "Grass_6.png";
	  
	  public static final String AVION_HIGH_RED   	 = "avion_h_r.png";
	  public static final String AVION_MEDIUM_RED    = "avion_m_r.png";
	  public static final String AVION_LIGHT_RED     = "avion_l_r.png";
	  
	  public static final String AVION_HIGH_BLUE   	 = "avion_h_b.png";
	  public static final String AVION_MEDIUM_BLUE   = "avion_m_b.png";
	  public static final String AVION_LIGHT_BLUE    = "avion_l_b.png";
	  
	  public static final String AVION_HIGH_BLUEF  	 = "avion_h_bf.png";
	  public static final String AVION_MEDIUM_BLUEF  = "avion_m_bf.png";
	  public static final String AVION_LIGHT_BLUEF   = "avion_l_bf.png";
	  
	  public static final String AVION_HIGH_VIOLET   = "avion_h_m.png";
	  public static final String AVION_MEDIUM_VIOLET = "avion_m_m.png";
	  public static final String AVION_LIGHT_VIOLET  = "avion_l_m.png";
	  
	  public static final String AVION_HIGH_ORANGE   = "avion_h_o.png";
	  public static final String AVION_MEDIUM_ORANGE = "avion_m_o.png";
	  public static final String AVION_LIGHT_ORANGE  = "avion_l_o.png";
	  
	  public static final String AVION_HIGH_VERT     = "avion_h_v.png";
	  public static final String AVION_MEDIUM_VERT   = "avion_m_v.png";
	  public static final String AVION_LIGHT_VERT    = "avion_l_v.png";
	  

	  public static final String PUNAISE_COLLISION   = "icone_punaise.png";
	  
	  private static final String PREFIX = "fr/iessa/vue/";
	  
	  public static URL get(String name) {
		    final URL result = Thread.currentThread().getContextClassLoader().getResource(PREFIX + name);
		    if (result == null){
		    	new IllegalStateException("Unable to load resource: " + name).printStackTrace();
		    	System.exit(-1);
		    	return null;
		    }  
		    else
		      return result;
		  }
}
