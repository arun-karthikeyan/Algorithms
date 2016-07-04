package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringCompression {
	
	
	static String compressedString(String orgStr){
		StringBuilder sb = new StringBuilder();
		int prevCharCount = 1;
		for(int i=1, j=orgStr.length(); i<j; ++i){
			if(orgStr.charAt(i)!=orgStr.charAt(i-1)){
				sb.append(orgStr.charAt(i-1)).append(prevCharCount);
				prevCharCount=1;
			}else{
				prevCharCount++;
			}
		}
		sb.append(orgStr.charAt(orgStr.length()-1)).append(prevCharCount);
		return sb.length()>=orgStr.length()?orgStr:sb.toString();
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(compressedString(str));
	}
}
