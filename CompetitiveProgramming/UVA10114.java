
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10114 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int duration = Integer.parseInt(st.nextToken());
	double downPayment = Double.parseDouble(st.nextToken()), loan = Double.parseDouble(st.nextToken());
	int depCount = Integer.parseInt(st.nextToken());
	
	while(duration>=0)
	{
		double[] dep = new double[duration+1];
		st = new StringTokenizer(br.readLine());
		int month = Integer.parseInt(st.nextToken());
		double percent = Double.parseDouble(st.nextToken());
		dep[month] = percent;
		int j = 1;
		for(int i=1;i<depCount;++i)
		{
			st = new StringTokenizer(br.readLine());
			month = Integer.parseInt(st.nextToken());
			for(int k=j;k<month;++k,++j)
			{
				dep[k] = percent;
			}
			percent = Double.parseDouble(st.nextToken());
			dep[j++] = percent;
		}
		while(j<=duration)
		{
			dep[j++] = percent;
		}
		double carValue = downPayment + loan;
		double monthlyRepay = loan/duration;
		
		//for month 0
		double owes = loan;
		carValue = (carValue*(1-dep[0]));
		if(owes<carValue || duration==0)
		{
			pw.println("0 months");
		}
		else
		{
		for(int i=1;i<=duration;++i)
		{
			owes -= monthlyRepay;
			carValue = (carValue*(1-dep[i]));
			
			if(owes<carValue)
			{
				if(i==1)
				{
					pw.println("1 month");
				}
				else
				{
					pw.println(i+" months");
				}
				break;
			}
		}
		}
			
		
		st = new StringTokenizer(br.readLine());
		duration = Integer.parseInt(st.nextToken());
		downPayment = Double.parseDouble(st.nextToken());
		loan = Double.parseDouble(st.nextToken());
		depCount = Integer.parseInt(st.nextToken());
		
	}
	pw.flush();
	pw.close();
	br.close();
}
}
