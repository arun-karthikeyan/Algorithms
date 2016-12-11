

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11364 {
	private static int max(int a, int b)
	{
		return a>b?a:b;
	}
	private static int min(int a, int b)
	{
		return a<b?a:b;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	while(testcases-->0)
	{
		int n = Integer.parseInt(br.readLine().trim());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int max, min;
		max = min = Integer.parseInt(st.nextToken());
		for(int i=1;i<n;++i)
		{
			int currentVal = Integer.parseInt(st.nextToken());
			max = max(max,currentVal);
			min = min(min,currentVal);
		}
		pw.println(((max-min)*2));
	}

	pw.flush();
	pw.close();
	br.close();
}
}
