

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11172 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = Integer.parseInt(br.readLine());
		while(testcases-->0)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int val1 = Integer.parseInt(st.nextToken()), val2 = Integer.parseInt(st.nextToken());
			if(val1>val2)
			{
				pw.println(">");
			}
			else
				if(val1<val2)
				{
					pw.println("<");
				}
				else
					{
						pw.println("=");
					}
		}
		

		pw.flush();
		pw.close();
		br.close();
	}

}
