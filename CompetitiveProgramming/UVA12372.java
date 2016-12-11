

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA12372 {
	private static final String GOOD = "good", BAD = "bad";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	for(int t=1;t<=testcases;++t)
	{
		StringTokenizer st = new StringTokenizer(br.readLine());
		boolean fits = Integer.parseInt(st.nextToken())<21 && Integer.parseInt(st.nextToken())<21 && Integer.parseInt(st.nextToken())<21;
		pw.print("Case "+t+": ");
		if(fits)
		{
			pw.println(GOOD);
		}
		else
		{
			pw.println(BAD);
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
