import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebPage {

	private static String state = "Create actor"; // web-page current state (Actor or Film)
	
	// get source code of the web-page
	public StringBuilder downloadSourceCode(String URL) throws Exception {
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
	
	// change state of the web-page
	public void changeState(String s) {
		state = s;
	}

	// get state of web-page
	public String getState() {
		return state;
	}
	
}
