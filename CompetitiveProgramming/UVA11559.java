

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11559 {
	private static final String STAYHOME = "stay home";
	private static int min(int a, int b)
	{
		return a<b?a:b;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String desc = "";
//	int testcases = 2;
//	while(testcases-->0)
	while((desc = br.readLine())!=null)
	{
		StringTokenizer st = new StringTokenizer(desc);
//		StringTokenizer st = new StringTokenizer(br.readLine());
		int participants = Integer.parseInt(st.nextToken()), budget = Integer.parseInt(st.nextToken()), hotels = Integer.parseInt(st.nextToken()), weeks = Integer.parseInt(st.nextToken());
		int minCost = Integer.MAX_VALUE;
		while(hotels-->0)
		{
			int totalCost = Integer.parseInt(br.readLine()) * participants;
			st = new StringTokenizer(br.readLine());
			if(totalCost <= budget)
			{
				for(int i=0;i<weeks;++i)
				{
					if(participants<=Integer.parseInt(st.nextToken()))
					{
						minCost = min(minCost, totalCost);
						break;
					}
				}
			}
			
		}
		if(minCost!=Integer.MAX_VALUE)
		{
			pw.println(minCost);
		}
		else
		{
			pw.println(STAYHOME);
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
