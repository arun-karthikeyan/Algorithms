

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10424 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String name1 = "";
//	int testcases = 3;
	while((name1=br.readLine())!=null){
//	while(testcases-->0){
//	name1 = br.readLine();
		String name2 = br.readLine();
		int len1 = name1.length(), len2 = name2.length();
		int score1 = 0, score2 = 0;
		//For name1
		for(int i=0;i<len1;++i)
		{
			int currentChar = Character.toLowerCase(name1.charAt(i));
			if(Character.isLowerCase(currentChar))
			{
				score1 = score1 + (currentChar-'a'+1);
			}
			int newScore = score1;
			while(newScore>9)
			{
				score1 = newScore;
				newScore = 0;
				while(score1>0)
				{
					newScore = newScore + (score1%10);
					score1/=10;
				}
			}
			score1 = newScore;
		}
		
		//For name2
		for(int i=0;i<len2;++i)
		{
			int currentChar = Character.toLowerCase(name2.charAt(i));
			if(Character.isLowerCase(currentChar))
			{
				score2 = score2 + (currentChar-'a'+1);
			}
			int newScore = score2;
			while(newScore>9)
			{
				score2 = newScore;
				newScore = 0;
				while(score2>0)
				{
					newScore = newScore + (score2%10);
					score2/=10;
				}
			}
			score2 = newScore;
		}
		
		if(score1>score2)
		{
			pw.format("%.2f",((score2*100d/score1)));
			pw.println(" %");
		}
		else
		{
			pw.format("%.2f",((score1*100d/score2)));
			pw.println(" %");
		}
		
		
	}

	pw.flush();
	pw.close();
	br.close();
}
}
