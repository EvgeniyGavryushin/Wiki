/**
 * checks whether all the records unique
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Unique {
	
	static Stack<String> actors = new Stack<String>(); // logged actors
	
	public static void main(String[] args) throws IOException {
	    FileReader fstream = new FileReader("../bin/output/actors.txt");
	    BufferedReader fbr = new BufferedReader(fstream);
	    
	   
	    for ( String line = fbr.readLine(); line != null; line = fbr.readLine() ) {
	        if (actors.contains(line)) {
	        	System.out.println("ERROR");
	        	System.out.println(line);
	        	fbr.close();
	        	return;
	        }
	        else {
	        	actors.push(line);
	        }
	    }
	    fbr.close();
	    System.out.println("OK");
	}
}
