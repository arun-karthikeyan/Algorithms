

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA12157 {
	private static final String MILE = "Mile", JUICE = "Juice", EQUAL = "Mile Juice";
	private static final int MILECOST = 10, JUICECOST = 15;
	private static final double MILESECS = 30d, JUICESECS = 60d;
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	
	int testcases = Integer.parseInt(br.readLine());
	for(int t=1;t<=testcases;++t){
		int n = Integer.parseInt(br.readLine());
		int mileTotal = 0, juiceTotal = 0;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<n;++i){
			int duration = Integer.parseInt(st.nextToken())+1;
			int mileUnits = (int) Math.ceil(duration/MILESECS);
			int juiceUnits = (int) Math.ceil(duration/JUICESECS);
			mileTotal = mileTotal + (mileUnits*MILECOST);
			juiceTotal = juiceTotal + (juiceUnits*JUICECOST);
		}
		pw.print("Case "+t+": ");
		if(mileTotal<juiceTotal)
		{
			pw.println(MILE+" "+mileTotal);
		}
		else
			if(juiceTotal<mileTotal){
				pw.println(JUICE+" "+juiceTotal);
			}
			else
			{
				pw.println(EQUAL+" "+mileTotal);
			}
	}
	

	pw.flush();
	pw.close();
	br.close();
}
}
