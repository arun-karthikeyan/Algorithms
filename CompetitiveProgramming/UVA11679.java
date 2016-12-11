

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11679 {
	private static final String S="S",N="N";
	public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int banks = Integer.parseInt(st.nextToken()), debentures = Integer.parseInt(st.nextToken());
	while(banks!=0 || debentures!=0)
	{
		int[] reserves = new int[banks];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<banks;++i)
		{
			reserves[i] = Integer.parseInt(st.nextToken());
		}
		for(int i=0;i<debentures;++i)
		{
			st = new StringTokenizer(br.readLine());
			int b1 = Integer.parseInt(st.nextToken())-1, b2 = Integer.parseInt(st.nextToken())-1, value = Integer.parseInt(st.nextToken());
			reserves[b1]-=value;
			reserves[b2]+=value;
		}
		
		boolean bailout = false;
		for(int i=0;i<banks;++i)
		{
			if(reserves[i]<0)
			{
				bailout = true;
				break;
			}
		}
		
		if(bailout)
		{
			pw.println(N);
		}
		else
		{
			pw.println(S);
		}
		st = new StringTokenizer(br.readLine());
		banks = Integer.parseInt(st.nextToken());
		debentures = Integer.parseInt(st.nextToken());
	}

	pw.flush();
	pw.close();
	br.close();
}
}
