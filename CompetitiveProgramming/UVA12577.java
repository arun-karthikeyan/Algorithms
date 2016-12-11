

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA12577 {
	private static final HashMap<String,String> map;
	static{
		map = new HashMap<String,String>();
		map.put("Hajj", "Hajj-e-Akbar");
		map.put("Umrah","Hajj-e-Asghar");
	}
	private static final String EOF = "*";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int t = 1;
	String val = "";
	while(!(val = br.readLine().trim()).equals(EOF))
	{
		pw.println("Case "+t+++": "+map.get(val));
	}

	pw.flush();
	pw.close();
	br.close();
}
}
