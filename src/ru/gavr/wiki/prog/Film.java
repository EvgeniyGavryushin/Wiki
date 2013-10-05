import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Film {
	
	public static Stack<String> usedFilms = new Stack<String>(); // logged films
	
	public Queue<String> listOfActors = new LinkedList<String>();// list of actors in the film
	
	// create film and its cast
	Film(String URL, StringBuilder sourceCode) {
		int tableStart = 0;
		int tableEnd = sourceCode.indexOf("id=\"Cast\"");
		int ulStart = 0;
		int ulEnd = sourceCode.indexOf("id=\"Cast\"");
		
		boolean flag = true;
		
		while (flag) {
		    ulStart = sourceCode.indexOf("<ul>", ulEnd + 10);
		   	if (ulStart != -1) {
		 		ulEnd = sourceCode.indexOf("</ul>", ulStart + 10);
		   		
	    		String pattern = "<li[^<]*>([0-9]*|<b>)<a href=.*</a>";
	    		
	    		flag = getActors(ulStart, ulEnd, sourceCode, pattern);
	    	}
		   	else {
		   		tableStart = sourceCode.indexOf("<table", tableEnd + 10);
		   		if (tableStart != -1) {
		   			ulEnd = sourceCode.indexOf("</ul>", ulStart + 10);
			   		
		    		String pattern = "(<td|<th)><a href=.*</a>";
		    		
		    		flag = getActors(tableStart, tableEnd, sourceCode, pattern);
		   		}
		   		else
		    		break;
		   	}
		}
		
		System.out.println();
		usedFilms.push(URL);
	}

	public static boolean canBeCreated(StringBuilder sourceCode) {
		if (!(Film.wasLogged(Search.URL)) &&  Film.parseURL(sourceCode)) {
			return true;
		}
		return false;
	}
	
	// get the cast of the film
	private boolean getActors(int start, int end, StringBuilder sourceCode, String pattern) {
		boolean flag = true;
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(sourceCode);
		
		m.region(start, end);
		
		while (m.find()) {
			flag = false;		
			
			int index = m.group().indexOf("<a href=");
			
			String address = "http://en.wikipedia.org";
			for (int i = index + 9; m.group().charAt(i) != '\"'; i++) {
				address += m.group().charAt(i);
			}	
			
			listOfActors.add(address);
			System.out.println(address);
		}
		return flag;
	}
	
	// find the required information
	private static boolean parseURL(StringBuilder sourceCode) {		
		if (sourceCode.toString() == "-1") return false;
		
		Pattern p = Pattern.compile("id=\"Cast\">");
		Matcher m = p.matcher(sourceCode);
		
		if (m.find()) return true;
		else return false;
	}
	
	// check on whether the film was met
	private static boolean wasLogged(String URL) {
		if (usedFilms.contains(URL)) return true;
		else return false;
	}
}
