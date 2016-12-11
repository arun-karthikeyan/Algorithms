

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA661 {
	private static int max(int a, int b)
	{
		return a>b?a:b;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken()), c = Integer.parseInt(st.nextToken());
	int sequence = 0;
	while(n!=0 || m!=0 || c!=0)
	{
		int[] devicePower = new int[n];
		for(int i=0;i<n;++i)
		{
			devicePower[i] = Integer.parseInt(br.readLine().trim());
		}
		int currentTotalPower = 0;
		boolean[] deviceStatus = new boolean[n];
		int maxPower = 0;
		boolean fuseBlown = false;
		for(int i=0;i<m;++i)
		{
			int currentDevice = Integer.parseInt(br.readLine().trim())-1;
			if(deviceStatus[currentDevice])
			{
				currentTotalPower-=devicePower[currentDevice];
				deviceStatus[currentDevice] = false;
			}
			else
			{
				currentTotalPower+=devicePower[currentDevice];
				deviceStatus[currentDevice] = true;
				maxPower = max(maxPower, currentTotalPower);
				if(currentTotalPower>c)
				{
					fuseBlown = true;
				}
			}
		}
		pw.println("Sequence "+(++sequence));
		if(fuseBlown)
		{
			pw.println("Fuse was blown.");
		}
		else
		{
			pw.println("Fuse was not blown.");
			pw.println("Maximal power consumption was "+maxPower+" amperes.");
		}
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
//		if(n!=0 || m!=0 || c!=0)
//		{
			pw.println();
//		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
