/**
*Download the source code of the given web-page
*@author Evgeniy Gavryushin	
*@version 1.0 Oct 6, 2013
*/

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebPage {

	/** web-page current state (Actor or Film) */
	private static String state = "Create actor"; 
	
	/** 
	*Get source code of the web-page
	*@param URL 	Current URL-adress with which we download the source code of the page.
	*@return sb		The source code of the web-page
	*@exception     Thrown if it is impossible to download the source code
	*/
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
	
	/** Change the state of the web-page. The web-page can be in two states: "create actor" and "create film" */
	public static void changeState(String s) {
		state = s;
	}

	/** get state of web-page */
	public static String getState() {
		return state;
	}
	
}
