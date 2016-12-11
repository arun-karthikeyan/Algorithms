

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA11942 {
	private static final String START = "Lumberjacks:";
	private static final String ORDERED = "Ordered", UNORDERED="Unordered";
	private static final int COUNT = 10;
	public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int n = Integer.parseInt(br.readLine());
	
	if(n>0)
	{
		pw.println(START);
		while(n-->0)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int prevLength = Integer.parseInt(st.nextToken());
			boolean SL = true;
			boolean LS = true;
			for(int i=1;i<COUNT;++i){
				int currentLength = Integer.parseInt(st.nextToken());
				if(currentLength<prevLength){
					SL = false;
				}
				else
					if(currentLength>prevLength){
						LS = false;
					}
				prevLength = currentLength;
			}
			if(SL || LS)
			{
				pw.println(ORDERED);
			}
			else
			{
				pw.println(UNORDERED);
			}
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
