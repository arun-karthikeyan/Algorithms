

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA11727 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	
	for(int t=1;t<=testcases;++t)
	{
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] sals = new int[3];
		for(int i=0;i<3;++i)
		{
			sals[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(sals);
		pw.println("Case "+t+": "+sals[1]);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
