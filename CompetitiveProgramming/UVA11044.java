

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11044 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	while(testcases-->0)
	{
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken())-2, n = Integer.parseInt(st.nextToken())-2;
		pw.println((int)(Math.ceil(m/3d)*Math.ceil(n/3d)));
	}

	pw.flush();
	pw.close();
	br.close();
}
}
