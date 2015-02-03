package memoire;

import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import aurelienribon.tweenengine.Tween;
import static java.util.concurrent.TimeUnit.SECONDS;
import sun.security.pkcs11.wrapper.Functions;
import fr.iessa.dao.trafic.TraficDao;
import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;


public class TestPleinDeTrafic {
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }

	public TestPleinDeTrafic(){
		// TODO Auto-generated method stub

	}
	
	public static void charger(String ficname, ArrayList<Trafic> list)
	{

		TraficDao traficDao = new TraficDao();
		
		list.add(traficDao.charger(ficname));
	}
	
	public static void main(String[] args) {
		

		
		long start = System.nanoTime();
		
		String ficname = "trafic.txt";
		Set<Vol> vols = null;
		TraficDao traficDao = new TraficDao();
		Trafic trafic = traficDao.charger(ficname);

		final double realTime = System.nanoTime()-start; 
		final int cores = Runtime.getRuntime().availableProcessors(); 
		System.out.println(" Cores: " + cores); 
		System.out.format(" Real time: %.2f s\n", realTime/SECONDS.toNanos(1));
		
		//
		

		
		
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    
	    // Calculate the used memory
	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("BEFORE Used memory is bytes: " + memoryBefore);
	    System.out.println("Used memory is megabytes: "
		        + bytesToMegabytes(memoryBefore));
		
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
	}

}
