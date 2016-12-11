

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UVA10919 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	StringTokenizer st = new StringTokenizer(br.readLine());
	int k = Integer.parseInt(st.nextToken());
	while(k!=0)
	{
		int c = Integer.parseInt(st.nextToken());
		HashSet<Integer> courses = new HashSet<Integer>(k+1);
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<k;++i){
			courses.add(Integer.parseInt(st.nextToken()));
		}
		boolean pass = true;
		for(int i=0;i<c;++i){
			st = new StringTokenizer(br.readLine());
			int courseCount = Integer.parseInt(st.nextToken()), req = Integer.parseInt(st.nextToken());
			int satisfied = 0;
			for(int j=0;j<courseCount;++j)
			{
				if(courses.contains(Integer.parseInt(st.nextToken())))
				{
					satisfied++;
				}
			}
			if(satisfied<req)
			{
				pass = false;
			}
		}
		
		if(pass){
			pw.println("yes");
		}else{
			pw.println("no");
		}
		
		st = new StringTokenizer(br.readLine());
		k = Integer.parseInt(st.nextToken());
//		c = Integer.parseInt(br.readLine());
		
	}
	pw.flush();
	pw.close();
	br.close();
}
}
