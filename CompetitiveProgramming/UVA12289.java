

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12289 {
	private static final String ONE = "one", TWO = "two";
	private static int mismatchCount(String exp1, String exp2)
	{
		int count = 0;
		for(int i=0,j=exp1.length();i<j;++i)
		{
			if(exp1.charAt(i)!=exp2.charAt(i))
			{
				count++;
			}
		}
		return count;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	while(testcases-->0)
	{
		String word = br.readLine();
		if(word.length()!=3)
		{
			pw.println("3");
		}
		else
		{
			if(mismatchCount(word,ONE)<mismatchCount(word, TWO))
			{
				pw.println("1");
			}
			else
			{
				pw.println("2");
			}
			
		}
	}
	pw.flush();
	pw.close();
	br.close();
}
}
