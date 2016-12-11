

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA621 {
	private static final char POSITIVE='+', NEGATIVE='-', INCOMPLETE='?', FAIL='*';
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	while(testcases-->0)
	{
		String currentResult = br.readLine().trim();
		if("1".equals(currentResult) || "4".equals(currentResult) || "78".equals(currentResult))
		{
			pw.println(POSITIVE);
		}
		else
		{
			if(currentResult.endsWith("35"))
			{
				pw.println(NEGATIVE);
			}
			else
				if(currentResult.startsWith("9") && currentResult.endsWith("4"))
				{
					pw.println(FAIL);
				}
				else
					if(currentResult.startsWith("190"))
					{
						pw.println(INCOMPLETE);
					}
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
