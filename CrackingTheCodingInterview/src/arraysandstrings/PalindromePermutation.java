package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class PalindromePermutation {

	//the below method doesn't count spaces
	static boolean isPalindromePermutation(String str){
		
		HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
		
		for(int i=0, j=str.length();i<j;++i){
			if(str.charAt(i)<'a' || str.charAt(i)>'z'){
				continue;
			}
			
			if(charCountMap.containsKey(str.charAt(i))){
				charCountMap.put(str.charAt(i), charCountMap.get(str.charAt(i))+1);
			}else{
				charCountMap.put(str.charAt(i), 1);
			}
		}
		int noteven = 0;
		Iterator<Character> chars = charCountMap.keySet().iterator();
		
		while(chars.hasNext()){
			if(charCountMap.get(chars.next())%2!=0){
				noteven++;
			}
			if(noteven>1){
				return false;
			}
		}
		
		return true;
		
	}
	
	//using a different approach
	//Intuition : we don't need the actual count but only if more than one odd count exists
	static boolean isPalindromePermutationBitVector(String str){
		int oddCountCheck = 0;
		for(int i=0, j=str.length();i<j; ++i){
			if(str.charAt(i)<'a' || str.charAt(i)>'z'){
				continue;
			}
			oddCountCheck ^= (1<<(str.charAt(i)-'a'));
		}
		return (oddCountCheck&(oddCountCheck-1))==0;
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine().toLowerCase();
		boolean verdict = isPalindromePermutationBitVector(str);
		if(verdict){
			System.out.println("Palindrome Permutation");
		}else{
			System.out.println("Not Palindrome Permutation");
		}
	}
}
