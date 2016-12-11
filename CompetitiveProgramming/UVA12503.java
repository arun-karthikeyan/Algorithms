

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12503 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());	
	while(testcases-->0)
	{
		int currentPos = 0;
		int instLength = Integer.parseInt(br.readLine().trim());
		int[] inst = new int[instLength];
		for(int i=0;i<instLength;++i)
		{
			String currentInst = br.readLine().trim();
			if(currentInst.charAt(0)=='L'){
				inst[i] = -1;
			}
			else
				if(currentInst.charAt(0)=='R'){
					inst[i] = 1;
				}
				else{
					inst[i] = inst[Integer.parseInt(currentInst.substring(8))-1];
				}
			currentPos+=inst[i];
			
		}
		pw.println(currentPos);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
