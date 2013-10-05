import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Search {
	
	static Stack<Actor> actors = new Stack<Actor>(); // track of  actors
	static Stack<Film> films = new Stack<Film>(); // track of films
	
	static String URL; // current URL
	
	// helping variables for URL
	static String _URL = "";
	static String filmography = ""; 
	
	// read URL-address from console
	public static void readUrl() {
		Scanner in = new Scanner(System.in);
		URL = in.nextLine();
		in.close();
	}
	
	// output
	static public void writeln(String output) {
		System.out.println(output);
	}
	
	// clear all files before starting search
	static public void clearFiles() throws IOException {
		FileWriter fstream = new FileWriter("files/actors.txt", false);
	    BufferedWriter fbw = new BufferedWriter(fstream);
	    fbw.close();
	    
	    FileWriter fstream1 = new FileWriter("files/usedActors.txt", false);
	    BufferedWriter fbw1 = new BufferedWriter(fstream1);
	    fbw1.close(); 
	}

	// enter point
	public static void main(String[] args) throws Exception {
		writeln("Enter the URL-address:");
		
		readUrl(); 	// read URL-address from console
		
		clearFiles();
				
		// search of actors in Wikipedia
		while (Actor.getTheNumberOfActors() < 1000) {
			filmography = "";
			
			switch (WebPage.getState()) {
			
			case "Create actor":
				_URL = URL;
				if (Actor.canBeCreated()) {
					actors.push(new Actor(URL, WebPage.downloadSourceCode(_URL + filmography)));
					if (!(actors.lastElement().filmography.isEmpty())) {
						URL = actors.lastElement().filmography.pop();
						WebPage.changeState("Create film");
					}
					else {
						actors.pop();
						WebPage.changeState("Logged film");
					}
				}
				else {
					if (films.isEmpty()) {
						writeln("Entered URL-adress does not represent an actor or an actress!");
						return;
					}
					if (!(films.lastElement().listOfActors.isEmpty())) {
						URL = films.lastElement().listOfActors.poll();
					}
					else {
						films.pop();
						WebPage.changeState("Logged actor");
					}
				}
				break;
				
			case "Create film":
				StringBuilder sourceCode = WebPage.downloadSourceCode(URL);
				if (Film.canBeCreated(sourceCode)) {
					films.push(new Film(URL, sourceCode));			
					if (!(films.lastElement().listOfActors.isEmpty())) {
					    URL = films.lastElement().listOfActors.poll();				
					    WebPage.changeState("Create actor");
					}
					else {
						films.pop();
						WebPage.changeState("Logged actor");
					}
				}
				else {
					if (!(actors.lastElement().filmography.isEmpty())) {
						URL = actors.lastElement().filmography.pop();
					}
					else {
						actors.pop();
						WebPage.changeState("Logged film");
					}
				}
				break;
				
			case "Logged actor":
				if (!(actors.lastElement().filmography.isEmpty())) {
					URL = actors.lastElement().filmography.pop();
					WebPage.changeState("Create film");
				}
				else {
					actors.pop();
					WebPage.changeState("Logged film");
				}
				break;
				
			case "Logged film":
				if (films.isEmpty()) {
					writeln("Can not find more actors using this URL-adress!");
					return;
				}
				if (!(films.lastElement().listOfActors.isEmpty())) {
				    URL = films.lastElement().listOfActors.poll();
				    WebPage.changeState("Create actor");
				}
				else {
					films.pop();
					WebPage.changeState("Logged actor");
				}
				break;			
			}
		}
		
		writeln("Done!");
		return;
	}
	
}
