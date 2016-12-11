

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA12250 {

	private static final HashMap<String,String> lang;
	static{
		lang = new HashMap<String,String>();
		lang.put("ZDRAVSTVUJTE", "RUSSIAN");
		lang.put("HELLO","ENGLISH");
		lang.put("HOLA", "SPANISH");
		lang.put("CIAO", "ITALIAN");
		lang.put("HALLO", "GERMAN");
		lang.put("BONJOUR", "FRENCH");
	}
	private static final String EOF = "#", UNKNOWN = "UNKNOWN";
	public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String word = "";
	int t = 1;
	while(!(word=br.readLine().trim()).equals(EOF))
	{
		String detectedLanguage = lang.get(word);
		if(detectedLanguage!=null)
		{
			pw.println("Case "+t+": "+detectedLanguage);
		}
		else
		{
			pw.println("Case "+t+": "+UNKNOWN);
		}
		++t;
	}

	pw.flush();
	pw.close();
	br.close();
}
}
