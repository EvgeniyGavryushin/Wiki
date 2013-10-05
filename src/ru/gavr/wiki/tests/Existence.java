/**
 * checks whether all the records are correct
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Existence {
	
	public static StringBuilder downloadSourceCode(String URL) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
		    URLConnection connection = new URL(URL).openConnection();
		    
		    InputStream is = connection.getInputStream();
		    InputStreamReader reader = new InputStreamReader(is);
		    
		    char[] buffer = new char[256];
		    int rc;

		    while ((rc = reader.read(buffer)) != -1) 
			    sb.append(buffer, 0, rc);

		    reader.close();

		    return sb;
		}
		catch(Exception e) {
			return sb.append("-1");
		}
	}
	
	public static boolean parseURL(StringBuilder sourceCode) {
		if (sourceCode.toString() == "-1") return false;
		
		Pattern p = Pattern.compile("((F|f)ilm)|((F|f)ilmography)");
		Matcher m = p.matcher(sourceCode);
		
		if (m.find()) return true;
		else return false;
	}
	
	public static void main(String[] args) throws Exception {
		FileReader fstream = new FileReader("../../../../../bin/output/usedActors.txt");
	    BufferedReader fbr = new BufferedReader(fstream);
	    
	    int i = 1;
	    
	    for (String URL = fbr.readLine(); URL != null; URL = fbr.readLine() ) {
	    	System.out.println(i + ". " + URL);
	    	if (parseURL(downloadSourceCode(URL))) i++;
	    	else {
	    		System.out.println("ERROR");
	    		fbr.close();
	    		return;
	    	}	
	    }
	    fbr.close();
	    System.out.println("OK");
	}
}
