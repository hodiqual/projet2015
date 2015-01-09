package memoire;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class TestMemoireEtRapiditeLectureFichier {
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }

	public TestMemoireEtRapiditeLectureFichier(){
		// TODO Auto-generated method stub

	}
	
	public static void charger(String ficname, ArrayList<String> list)
	{
		try {
			Scanner scan = new Scanner(new FileReader(ficname));
			scan.useDelimiter("\n");
			while(scan.hasNext())
			{
				String ligne = scan.next();
				Scanner scanByLine = new Scanner(ligne);
				scanByLine.useDelimiter(";|\n| |,");

				while(scanByLine.hasNext())
				{
					list.add(scanByLine.next());
				}
				scanByLine.close();
			}
			scan.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String ficname = "lfpg.txt";
		ArrayList<String> list = new ArrayList<String>();
		//Java 7 try-with-ressource -> Scanner implements Closeable -> AUTOCLOSE  gere aussi le cas null
		charger(ficname, list);	
		System.out.println("Taille de lfpg: " + list.size());
		charger("lfpg.txt", list);	
		System.out.println("Taille de lfpg: " + list.size());
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
		System.out.println("Taille de lfpg: " + list.size());
	}

}
