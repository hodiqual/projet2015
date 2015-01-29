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
import fr.iessa.metier.trafic.Instant;
import fr.iessa.metier.trafic.Instant.InstantFabrique;
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
		//SLAnimator.start();
		
		String ficname = "trafic.txt";
		ArrayList<Trafic> list = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			//charger(ficname, list);	
		}
		
		Set<Vol> vols = null;
		TraficDao traficDao = new TraficDao();
		try (Stream<String> lignes = Files.lines(Paths.get(ficname));) {
			vols = lignes.parallel()
					.map(traficDao::chargerVol)
					.collect(Collectors.toSet());
					//.collect(Collectors.toCollection(TreeSet::new));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//vols.parallelStream().collect(collector)
		/*InstantFabrique.getInstants().parallelStream()
									 .map( instant -> vols.stream()
											 				.filter(v -> v.estSurLaPlateforme(instant) )
											 				.collect(Collectors.toSet())
									 .collect(Collectors.groupingByConcurrent(Functions.identity(),));*/
									 
		Set<Vol> vols1 = vols;
		Function<Instant, Set<Vol> > valueMapper= (Instant instant) -> vols1.stream()
																		.filter(v -> v.estSurLaPlateforme(instant) )
																		.collect( Collectors.toSet() );
		
		long start = System.nanoTime();
		
		  ConcurrentMap<Instant, Set<Vol>> volsParInstant = InstantFabrique.getInstants().parallelStream()
																						 .collect( Collectors.toConcurrentMap( Function.identity()
																															   ,(Instant i) -> vols1.stream()
																																					.filter(v -> v.estSurLaPlateforme(i) )
																																					.collect( Collectors.toSet() ) ) );
		 TreeMap<Instant, Set<Vol>> treeMap = new TreeMap(volsParInstant);
		 

		/*TreeMap<Instant, Set<Vol>> treeMapTest = InstantFabrique.getInstants().stream()
												.collect( Collectors.toMap( Function.identity()
																, valueMapper, (o,n) -> n, TreeMap::new ) );*/
		
		final double realTime = System.nanoTime()-start; 
		final int cores = Runtime.getRuntime().availableProcessors(); 
		System.out.println(" Cores: " + cores); 
		System.out.format(" Real time: %.2f s\n", realTime/SECONDS.toNanos(1));
		
		//
		
		
		
		/*treeMap.keySet().stream().filter( i -> i.getSeconds()%10 == 0 )
								.forEachOrdered( i -> System.out.println(i.getSeconds() + ": " + volsParInstant.get(i).size()) );
								*/
		//vols.parallelStream().
		
		//Trafic trafic = list.get(0);
		//trafic.computeDelta();
		

		Predicate <Vol> toAdd = new Predicate<Vol>() {

			@Override
			public boolean test(Vol t) {
				fr.iessa.metier.trafic.Instant instant = InstantFabrique.get(70000);

				fr.iessa.metier.trafic.Instant precedent = InstantFabrique.get(70000-5);
				
				
				return t.get_instantVersCoord().containsKey(instant) 
						&& t.get_instantVersCoord().containsKey(precedent);
			}
		};
		

		Predicate <Vol> toRemove = new Predicate<Vol>() {

			@Override
			public boolean test(Vol t) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		
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
