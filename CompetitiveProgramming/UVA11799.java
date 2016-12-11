

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11799 {
	private static int max(int a, int b){
		return a>b?a:b;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	
	for(int t=1;t<=testcases;++t){
		StringTokenizer st = new StringTokenizer(br.readLine());
		int max = Integer.parseInt(st.nextToken());
		while(st.hasMoreTokens())
		{
			max = max(max,Integer.parseInt(st.nextToken()));
		}
		pw.println("Case "+t+": "+max);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
