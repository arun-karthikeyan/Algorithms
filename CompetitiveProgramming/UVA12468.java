

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA12468 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
	while(a!=-1 || b!=-1)
	{
		int total = Math.abs(b-a);
		if(total>50)
		{
			total = 100-total;
		}
		pw.println(total);
		st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		
	}

	pw.flush();
	pw.close();
	br.close();
}
}
