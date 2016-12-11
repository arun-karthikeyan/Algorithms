

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11683 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	StringTokenizer st = new StringTokenizer(br.readLine());
	int a = Integer.parseInt(st.nextToken());
	while(a!=0){
		int c = Integer.parseInt(st.nextToken());
		int totalOns = 0, currentOn = 0;
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<c;++i){
			int currentWidth = Integer.parseInt(st.nextToken());
			int requiredOns = a-currentWidth;
			if(currentOn<requiredOns)
			{
				totalOns = totalOns + (requiredOns-currentOn);
				currentOn = requiredOns;
			}
			currentOn = requiredOns;
		}
		pw.println(totalOns);
		
		st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken());
	}

	pw.flush();
	pw.close();
	br.close();
}
}
