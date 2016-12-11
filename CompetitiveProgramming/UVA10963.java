

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10963 {
	private static final String YES = "yes", NO = "no";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	while(testcases-->0)
	{
		br.readLine();
		int cols = Integer.parseInt(br.readLine().trim());
		StringTokenizer st = new StringTokenizer(br.readLine());
		cols--;
		int gapSpace = Integer.parseInt(st.nextToken()) - Integer.parseInt(st.nextToken());
		boolean possible = true;
		while(cols-->0)
		{
			st = new StringTokenizer(br.readLine());
			if(gapSpace!=(Integer.parseInt(st.nextToken()) - Integer.parseInt(st.nextToken())))
			{
				possible = false;
//				break;
			}
		}
		if(possible)
		{
			pw.println(YES);
		}
		else{
			pw.println(NO);
		}
		if(testcases!=0)
		{
			pw.println();
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
