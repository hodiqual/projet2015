package memoire;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.metier.infra.Aeroport;


public class TestMemoireEtRapiditeParDAO {
	
	  private static final long MEGABYTE = 1024L * 1024L;

	  public static long bytesToMegabytes(long bytes) {
	    return bytes / MEGABYTE;
	  }

	public TestMemoireEtRapiditeParDAO(){
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
	
	public static void main(String[] args) throws FileNotFoundException, NoSuchElementException {
		String ficname = "lfpg.txt";
		//Java 7 try-with-ressource -> Scanner implements Closeable -> AUTOCLOSE  gere aussi le cas null
		Aeroport aeroport = InfrastructureDAO.charger(ficname);
	    System.out.println(aeroport);
	    // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory Avant Garbage is bytes: " + memory);
	    System.out.println("Avant Garbage Avant Garbage Used memory is megabytes: "
		        + bytesToMegabytes(memory));
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
	}

}
