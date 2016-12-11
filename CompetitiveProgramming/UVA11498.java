

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11498 {
	private static final int X = 0, Y = 1;
	private static final String DIVISA = "divisa", NW = "NO", NE = "NE", SW = "SO", SE = "SE";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	
	int testcases = Integer.parseInt(br.readLine());

	while(testcases>0)
	{
	StringTokenizer st = new StringTokenizer(br.readLine());
	int[] divisionPoint = new int[2];
	divisionPoint[X] = Integer.parseInt(st.nextToken());
	divisionPoint[Y] = Integer.parseInt(st.nextToken());
	while(testcases-->0)
	{
		st = new StringTokenizer(br.readLine());
		int[] currentPoint = new int[2];
		currentPoint[X] = Integer.parseInt(st.nextToken());
		currentPoint[Y] = Integer.parseInt(st.nextToken());
		
		if(currentPoint[X]==divisionPoint[X] || currentPoint[Y]==divisionPoint[Y])
		{
			pw.println(DIVISA);
		}
		else
			if(currentPoint[X]>divisionPoint[X])
			{
				if(currentPoint[Y]>divisionPoint[Y])
				{
					pw.println(NE);
				}
				else
				{
					pw.println(SE);
				}
			}
			else
			{
				if(currentPoint[Y]>divisionPoint[Y])
				{
					pw.println(NW);
				}
				else
				{
					pw.println(SW);
				}
			}
			
	}
	testcases = Integer.parseInt(br.readLine());
	}
	pw.flush();
	pw.close();
	br.close();
}
}
