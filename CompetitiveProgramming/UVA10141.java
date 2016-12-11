

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10141 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int reqNo = Integer.parseInt(st.nextToken()), propNo = Integer.parseInt(st.nextToken());
	int testcase = 0;
	while(reqNo!=0 || propNo!=0)
	{
		for(int i=0;i<reqNo;++i){
			br.readLine();
		}
		String propName = br.readLine();
		st = new StringTokenizer(br.readLine());
		double minPrice = Double.parseDouble(st.nextToken());
		int maxReqs = Integer.parseInt(st.nextToken());
		for(int i=0;i<maxReqs;++i)
		{
			br.readLine();
		}
		for(int i=1;i<propNo;++i){
			String currentPropName = br.readLine();
			st = new StringTokenizer(br.readLine());
			double currentPrice = Double.parseDouble(st.nextToken());
			int currentReqsCount = Integer.parseInt(st.nextToken());
			if((currentReqsCount>maxReqs) || ((currentReqsCount==maxReqs) && (currentPrice<minPrice)))
			{
				propName = new String(currentPropName);
				minPrice = currentPrice;
				maxReqs = currentReqsCount;
			}
			for(int j=0;j<currentReqsCount;++j)
			{
				br.readLine();
			}
		}
		pw.println("RFP #"+(++testcase));
		pw.println(propName);
		
		st = new StringTokenizer(br.readLine());
		reqNo = Integer.parseInt(st.nextToken());
		propNo = Integer.parseInt(st.nextToken());
		if(reqNo!=0 || propNo!=0)
		{
			pw.println();
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
