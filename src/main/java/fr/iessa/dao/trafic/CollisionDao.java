/**
 * 
 */
package fr.iessa.dao.trafic;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.iessa.metier.trafic.Collision;

/**
 * 
 * @author hodiqual
 *
 */
public class CollisionDao {
	
	public void sauvegardeTrafic(String ficname, List<Collision> collisions) throws FileNotFoundException, IOException
	{
		
		Collections.sort(collisions);
		
		//Java 7 try-with-ressource -> BufferedWriter implements Closeable -> AUTOCLOSE gere aussi le cas null
		try (BufferedWriter bf=new BufferedWriter(new FileWriter(ficname));) 
		{
			for (Collision collision : collisions) {
				bf.write(collision.toString());
				bf.newLine();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
