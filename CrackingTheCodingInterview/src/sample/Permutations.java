package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//	print all permutations of the given string - recursive
public class Permutations {
	/**
	 * Returns all permutations of a given string without repetition
	 * @param prefix - already permuted prefix
	 * @param remaining - remaining characters to be permuted
	 */
	static void permuteWoRep(String prefix, String remaining){
		if(remaining.length()==0){
			System.out.println(prefix);
			return;
		}
		
		for(int i=0, j=remaining.length(); i<j; ++i){
			permuteWoRep(prefix+remaining.charAt(i), remaining.substring(0,i)+remaining.substring(i+1));
		}
	}
	/**
	 * Returns all permutations of a given string with repetitions
	 * @param prefix - already permuted prefix
	 * @param remaining - remaining characters to be permuted
	 */
	static void permuteWRep(String prefix, String remaining){
		if(prefix.length()==remaining.length()){
			System.out.println(prefix);
			return;
		}
		
		for(int i=0, j=remaining.length(); i<j; ++i){
			permuteWRep(prefix+remaining.charAt(i), remaining);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String str;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to permute with repetitions");
		System.out.println("Enter 2 to permute without repetitions");
		int choice = Integer.parseInt(br.readLine());
		
		System.out.println("Enter the string to permute");
		str = br.readLine();
		
		if(choice==1){
			permuteWRep("", str);
		}else{			
			permuteWoRep("",str);
		}
		
		
	}
}
