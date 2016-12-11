

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11764 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	for(int t=1;t<=testcases;++t)
	{
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int prevWall = Integer.parseInt(st.nextToken());
		int tall = 0, small = 0;
		while(n-->1)
		{
			int currentWall = Integer.parseInt(st.nextToken());
			if(currentWall>prevWall)
			{
				tall++;
			}
			else
				if(currentWall<prevWall)
			{
				small++;
			}
			prevWall = currentWall;
		}
		pw.println("Case "+t+": "+tall+" "+small);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
