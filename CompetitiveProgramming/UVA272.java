

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA272 {
	private static final String leftQuotes = "``", rightQuotes = "''";
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		String currentLine = "";
		int quoteCount = 0;
//		int t = 2;
		while((currentLine=br.readLine())!=null)
//		while(t-->0)
		{
//			currentLine = br.readLine();
			StringBuilder sb = new StringBuilder();
			for(int i=0,j=currentLine.length();i<j;++i)
			{
				if(currentLine.charAt(i)=='"')
				{
					quoteCount++;
					if(quoteCount%2==0)
					{
						sb.append(rightQuotes);
					}
					else
					{
						sb.append(leftQuotes);
					}
				}
				else
				{
					sb.append(currentLine.charAt(i));
				}
			}
			pw.println(sb.toString());
		}
		pw.flush();
		pw.close();
		br.close();
	}

}
