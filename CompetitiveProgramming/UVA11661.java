

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11661 {
	@SuppressWarnings("unused")
	private static final char R='R', D='D', Z='Z', EMPTY='.';
	private static final int NOTSEEN = -1;
	private static int min(int a, int b){
		return a<b?a:b;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int roadLength = 0;
	while((roadLength=Integer.parseInt(br.readLine()))!=0)
	{
		int closestR = NOTSEEN, closestD = NOTSEEN;
		String road = br.readLine();
		int minDist = Integer.MAX_VALUE;
		for(int i=0;i<roadLength;++i){
			char currentStop = road.charAt(i);
			if(currentStop==R){
				closestR = i;
				if(closestD!=NOTSEEN)
				{
					minDist = min(minDist, closestR-closestD);
				}
			}
			else
				if(currentStop == D){
					closestD = i;
					if(closestR!=NOTSEEN){
						minDist = min(minDist, closestD - closestR);
					}
				}
				else
					if(currentStop == Z)
					{
						minDist = 0;
						break;
					}
		}
		pw.println(minDist);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
