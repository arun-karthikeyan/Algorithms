

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA1124 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String line = "";
	while((line=br.readLine())!=null)
	{
		pw.println(line);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
