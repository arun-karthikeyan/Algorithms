package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class CheckPermutation {
	static boolean checkPermutation(String a, String b){
		if(a.length() != b.length()){
			return false;
		}
		
		HashMap<Character, Integer> chars = new HashMap<Character, Integer>();
		
		for(int i=0, j=a.length(); i<j; ++i){
			if(chars.containsKey(a.charAt(i))){
				chars.put(a.charAt(i), chars.get(a.charAt(i))+1);
			}else{
				chars.put(a.charAt(i), 1);
			}
		}
		
		for(int i=0, j=b.length(); i<j; ++i){
			if(!chars.containsKey(b.charAt(i)) || chars.get(b.charAt(i))==0){
				return false;
			}
			chars.put(b.charAt(i), chars.get(b.charAt(i))-1);
		}
		
		return true;
	}
	
	static boolean checkPermutationSort(String a, String b){
		if(a.length()!=b.length()){
			return false;
		}
		
		char[] charA = a.toCharArray();
		char[] charB = b.toCharArray();
		
		Arrays.sort(charA);
		Arrays.sort(charB);

		for(int i=0, j=charA.length; i<j; ++i){
			if(charA[i]!=charB[i]){
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String a = br.readLine();
		String b = br.readLine();
		
		if(checkPermutationSort(a, b)){
			System.out.println(a+" is a permutation of "+b);
		}else{
			System.out.println(a+" is not a permutation of "+b);
		}
	}
}
