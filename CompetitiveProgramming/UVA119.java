

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA119 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String start = br.readLine();
	
	while(start!=null){
		int groupSize = Integer.parseInt(start);
		HashMap<String,Integer> groupMap = new HashMap<String,Integer>(11);
		String[] groupMembers = new String[groupSize];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0;i<groupSize;++i)
		{
			groupMembers[i] = st.nextToken();
			groupMap.put(groupMembers[i], i);
		}
		int[] amounts = new int[groupSize];
		for(int i=0;i<groupSize;++i)
		{
			st = new StringTokenizer(br.readLine());
			String from = st.nextToken();
			int amount = Integer.parseInt(st.nextToken());
			int toCount = Integer.parseInt(st.nextToken());
			if(amount>0 && toCount>0)
			{
			int share = amount/toCount;
			amount = share * toCount;
			amounts[groupMap.get(from)]-=amount;
			for(int j=0;j<toCount;++j)
			{
				amounts[groupMap.get(st.nextToken())]+=share;
			}
			}
		}
		for(int i=0;i<groupSize;++i)
		{
			pw.println(groupMembers[i]+" "+amounts[i]);
		}
		start = br.readLine();
		if(start!=null)
		{
			pw.println();
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
