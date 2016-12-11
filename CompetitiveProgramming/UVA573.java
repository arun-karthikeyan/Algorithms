

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA573 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int h = Integer.parseInt(st.nextToken());
	double u = Integer.parseInt(st.nextToken());
	int d = Integer.parseInt(st.nextToken()), f = Integer.parseInt(st.nextToken());
	while(h!=0)
	{
		int day = 0;
		double distanceClimbed = 0d, deceleration = (u*f)/100;
		boolean success = false;
		while(true)
		{
			day++;
			if(u>0)
			{
			distanceClimbed = distanceClimbed + u;
			if(distanceClimbed>h)
			{
				success = true;
				break;
			}
			}
			distanceClimbed-=d;
			if(distanceClimbed<0)
			{
				break;
			}
			u-=deceleration;
		}

		if(success)
		{
			pw.println("success on day "+day);
		}
		else
		{
			pw.println("failure on day "+day);
		}
		
		st = new StringTokenizer(br.readLine());
		h = Integer.parseInt(st.nextToken());
		u = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		f = Integer.parseInt(st.nextToken());
	}

	pw.flush();
	pw.close();
	br.close();
}
}
