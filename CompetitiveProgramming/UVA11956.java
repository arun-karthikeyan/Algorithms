

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA11956 {
	private static final char GT = '>', LT = '<', PS = '+', MS = '-', DT = '.';
	private static final HashMap<Character,Integer> pointerMap,byteMap;
	static{
		pointerMap = new HashMap<Character,Integer>(5);
		byteMap = new HashMap<Character,Integer>(5);
		
		pointerMap.put(GT,1);
		pointerMap.put(LT,-1);
		pointerMap.put(PS,0);
		pointerMap.put(MS,0);
		pointerMap.put(DT,0);
		
		byteMap.put(GT,0);
		byteMap.put(LT,0);
		byteMap.put(PS,1);
		byteMap.put(MS,-1);
		byteMap.put(DT,0);
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = Integer.parseInt(br.readLine());
		for (int t = 1; t <= testcases; ++t) {
			String commands = br.readLine().trim();
			int pointer = 0;
			int[] byteArray = new int[100];
			pw.print("Case " + t + ":");
			for (int i = 0, j = commands.length(); i < j; ++i) {
				char currentCommand = commands.charAt(i);
				pointer = (pointer + pointerMap.get(currentCommand) + 100)%100;
				byteArray[pointer] = ((byteArray[pointer] + byteMap.get(currentCommand)) & 0xff);
			}
			for(int i=0;i<100;++i)
			{
				pw.print(" "+format(Integer.toHexString(byteArray[i])));
			}
			pw.println();
		}

		pw.flush();
		pw.close();
		br.close();
	}
	private static String format(String value){
		StringBuilder sb = new StringBuilder();
		int len = value.length();
		if(len==1){
			sb.append("0");
		}
		for(int i=0;i<len;++i)
		{
			sb.append(Character.toUpperCase(value.charAt(i)));
		}
		return sb.toString();
	}
}
