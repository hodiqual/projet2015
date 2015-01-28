package memoire;

import java.awt.Point;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;


public class TestDoublonPointTrafic {
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }

	public TestDoublonPointTrafic(){

	}
	
	public static void charger(String ficname, ArrayList<Point> list, Map<Point, String> map)
	{
		try (Scanner scan = new Scanner(new FileReader(ficname));){
			
			scan.useDelimiter("\n");
			while(scan.hasNext())
			{
				Scanner scanParLigne = new Scanner (scan.next()).useDelimiter("\n| |,");
				scanParLigne.next();scanParLigne.next();scanParLigne.next();scanParLigne.next();scanParLigne.next();scanParLigne.next();scanParLigne.next();
				
				while(scanParLigne.hasNext())
				{
					Point pt = new Point( Integer.valueOf(scanParLigne.next()), Integer.valueOf(scanParLigne.next()));
					list.add(pt);
					map.putIfAbsent(pt, pt.toString());
				}
				
				scanParLigne.close();

			}

		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String ficname = "trafic.txt";
		ArrayList<Point> list = new ArrayList<Point>();
		Map<Point, String> map = new Hashtable<Point, String>();
		//Java 7 try-with-ressource -> Scanner implements Closeable -> AUTOCLOSE  gere aussi le cas null
		charger(ficname, list, map );	
		
		System.out.println("Taille de la liste: " + list.size());		
		System.out.println("Taille de la map  : " + map.size());
		
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
	}

}
