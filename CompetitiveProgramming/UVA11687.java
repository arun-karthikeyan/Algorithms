

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11687 {
	private static final String END = "END", ONE = "1";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		String digits = "";
		while (!(END).equals(digits = br.readLine().trim())) {
			int length = digits.length();
			if(length==1)
			{
				if(digits.equals(ONE))
				{
					pw.println(ONE);
				}
				else
				{
					pw.println(2);
				}
			}
			else
				if(length<10){
					pw.println(3);
				}else{
					pw.println(4);
				}
					
			
		}

		pw.flush();
		pw.close();
		br.close();
	}
}
