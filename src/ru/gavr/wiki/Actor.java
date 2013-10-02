import java.util.Stack;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Actor {
	
	public static Stack<String> usedActors = new Stack<String>(); // logged actors
	
	private static int theNumberOfActors = 0;
	
	private static int start;
		
	public Stack<String> filmography = new Stack<String>(); // actor's filmography
		
	// create actor and its filmography
	Actor(String URL, StringBuilder sourceCode) throws IOException {
		int tableStart = 0;
		int tableEnd = start;
		int ulStart = 0;
		int ulEnd = start;
		
		boolean flag = true;
		
		while (flag) {
			tableStart = sourceCode.indexOf("<table", tableEnd + 10);

		    if (tableStart != -1) {	
		    	tableEnd = sourceCode.indexOf("</table>", tableStart + 10);
		    	
		    	String pattern = "(<th|<td)[^<]*>[0-9]*((<i>.*<a href=.*</a>.*</i>.*)|(<a href=.*<i>.*</i>.*</a>))";
		    	
		    	flag = getFilmography(tableStart, tableEnd, sourceCode, pattern);
		    }
		    else {
		    	ulStart = sourceCode.indexOf("<ul", ulEnd + 10);
		    	if (ulStart != -1) {
		    		ulEnd = sourceCode.indexOf("</ul>", ulStart + 10);
		    		
		    		String pattern = "<li[^<]*>[0-9]*((<i>.*<a href=.*</a>.*</i>.*)|(<a href=.*<i>.*</i>.*</a>))";
		    		
		    		flag = getFilmography(ulStart, ulEnd, sourceCode, pattern);
		    	}
		    	else
		    		break;
		    }
		}
		
		System.out.println();
		
		String name = getName(sourceCode);
		usedActors.push(name);
		writeToFile(Search.URL, name);
		increaseTheNumberOfActors();
	}
	
	// get the filmography of the actor
	private boolean getFilmography(int start, int end, StringBuilder sourceCode, String pattern) {
		boolean flag = true;
		
		Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(sourceCode);
	    
    	m.region(start, end);
    	
    	while (m.find() && 
	    		(end < sourceCode.indexOf("id=\"External_links\">") 
	    				||  sourceCode.indexOf("id=\"External_links\">") < 0)) {
			flag = false;
    		
			int index = m.group().indexOf("<a href=");
			
			String address = "http://en.wikipedia.org";
			for (int i = index + 9; m.group().charAt(i) != '\"' && m.group().charAt(i) != '&'; i++) {
				address += m.group().charAt(i);	
			}	
				
			filmography.push(address);
			System.out.println(address);
		}
    	
    	return flag;
	}
	
	// write actor into the file 
	private void writeToFile(String URL, String name) throws IOException {
	    FileWriter fstream = new FileWriter("files/actors.txt", true);
	    BufferedWriter fbw = new BufferedWriter(fstream);
	    fbw.write(name);
	    fbw.newLine();
	    fbw.close();
	    
	    FileWriter fstream1 = new FileWriter("files/usedActors.txt", true);
	    BufferedWriter fbw1 = new BufferedWriter(fstream1);
	    fbw1.write(URL);
	    fbw1.newLine();
	    fbw1.close();
	    
	}
	
	public static int getTheNumberOfActors() {
		return theNumberOfActors;
	}
	
	private static void increaseTheNumberOfActors() {
		theNumberOfActors++;
		System.out.println(theNumberOfActors);
	}
	
	public static String getName(StringBuilder sourceCode) {
		String name = "";
		int index = sourceCode.indexOf("<title>");
		int edge = sourceCode.indexOf(" filmography - Wikipedia, the free encyclopedia</title>");
		if (edge == -1) {
			edge = sourceCode.indexOf(" - Wikipedia, the free encyclopedia</title>");
		}
		for (index += 7; index < edge && 
				sourceCode.charAt(index) != '('; index++) {
			name += sourceCode.charAt(index);
		}
		return name;
	}
		
	// check whether the required information exists
	public static boolean parseURL(StringBuilder sourceCode) {		
		if (sourceCode.toString() == "-1") return false;
		
		Pattern p = Pattern.compile("id=\"((Selected_filmography)|(Film)|(Filmography))\">");
		Matcher m = p.matcher(sourceCode);
		
		if (m.find()) {
			start = m.start();
			
			p = Pattern.compile("Main article: <a href=\".*_filmography");
			m = p.matcher(sourceCode);
			
			if (m.find()) { 
				start = 0;
				
				Search.filmography += "_filmography";
				
				if (Search.URL.indexOf("_(actor)") != -1 || 
						Search.URL.indexOf("_(actress)") != -1) {
					Search._URL = "";
					for (int i = 0; i < Search.URL.indexOf("_(act"); i++)
						Search._URL += Search.URL.charAt(i);
				}
			}
			return true;
		}
		else return false;
	}
	
	// check on whether actor was met
	public static boolean wasLogged(String name) {
		if (usedActors.contains(name)) return true;
		else return false;
	}
}
