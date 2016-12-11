

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11586 {
	private static final String LOOP = "LOOP", NOLOOP = "NO LOOP";
	private static final char MALE = 'M', FEMALE = 'F';
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	while(testcases-->0)
	{
		String track = br.readLine().trim();
		int maleCount = 0, femaleCount = 0;
		for(int i=0,j=track.length();i<j;++i)
		{
			char currentPiece = track.charAt(i);
			if(currentPiece==MALE){
				maleCount++;
			}
			else
				if(currentPiece==FEMALE)
			{
				femaleCount++;
			}
		}
		if(maleCount!=1  && maleCount==femaleCount)
		{
			pw.println(LOOP);
		}
		else
		{
			pw.println(NOLOOP);
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
