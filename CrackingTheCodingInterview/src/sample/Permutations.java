package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//	print all permutations of the given string - recursive
public class Permutations {
	
	static void permute(String prefix, String remaining){
		if(remaining.length()==0){
			System.out.println(prefix);
		}
		
		for(int i=0, j=remaining.length(); i<j; ++i){
			permute(prefix+remaining.charAt(i), remaining.substring(0,i)+remaining.substring(i+1));
		}
	}
	
	public static void main(String[] args) throws Exception {
		String str;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter the string to permute");
		str = br.readLine();
		permute("",str);
		
	}
}
