/**
*Helps to create the track of found films with their cast
*@author Evgeniy Gavryushin	
*@version 1.0 Oct 6, 2013
*/

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Film {
	
	/** Logged films */
	public static Stack<String> usedFilms = new Stack<String>();
	
	/** List of actors in the film */
	public Queue<String> listOfActors = new LinkedList<String>();
	
	/** 
	*Create film and its cast
	*@param URL 		Current URL-adress of the film
	*@param sourceCode 	Downloaded source code of the film
	*/
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

	/**
	*Checks if the film can be created
	*@return 	"true" - if yes, "false" - if no
	*/
	public static boolean canBeCreated(StringBuilder sourceCode) {
		if (!(Film.wasLogged(Search.URL)) &&  Film.parseURL(sourceCode)) {
			return true;
		}
		return false;
	}
	
	/** 
	*Get the cast of the film
	*@param start 		Index of the symbol in the source code from which the search of the cast will start
	*@param end 		Index of the symbol in the source code to which the search of the cast will continue
	*@param sourceCode 	Source code of the web-page
	*@param pattern 	RegExp which help to find the film's cast in the source code
	*@return flag		If cast was found return "false" else return "true"
	*/
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
	
	/** 
	*Check whether the web-page has article Cast else the downloaded web-page is not about the film
	*@param sourceCode  Source code of the film's web-page
	*@return 			"true" - if the web-page is about the film, "fasle" - if not
	*/
	private static boolean parseURL(StringBuilder sourceCode) {		
		if (sourceCode.toString() == "-1") return false;
		
		Pattern p = Pattern.compile("id=\"Cast\">");
		Matcher m = p.matcher(sourceCode);
		
		if (m.find()) return true;
		else return false;
	}
	
	/** 
	*Check whether we have already met the film
	*@param URL 	The URL-adress of film
	*@return 		"true" - if we hav already met, "false" - if not 
	*/
	private static boolean wasLogged(String URL) {
		if (usedFilms.contains(URL)) return true;
		else return false;
	}
}
