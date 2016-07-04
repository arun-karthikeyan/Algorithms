package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * Implement an algorithm to determine if a string has all unique characters.
 * What if you cannot use any additional data structures ?
 * @author arun
 *
 */
public class IsUnique {
private static final int ALPSIZE = 26;
	static boolean isUniqueBoolVec(String str){
		if(str.length()>ALPSIZE){
			return false;
		}
		
		boolean[] alphs = new boolean[ALPSIZE];
		for(int i=0, j=str.length(); i<j; ++i){
			if(alphs[str.charAt(i)-'a']){
				return false;
			}
			alphs[str.charAt(i)-'a'] = true;
		}
		return true;
	}
	
	static boolean isUniqueHashTable(String str){
		if(str.length()>ALPSIZE){
			return false;
		}
		
		HashSet<Character> alphs = new HashSet<Character>();
		for(int i=0, j=str.length(); i<j; ++i){
			if(!alphs.add(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	static boolean isUniqueBitVector(String str){
		if(str.length()>ALPSIZE){
			return false;
		}
		
		int bitVec = 0;
		for(int i=0, j=str.length(); i<j; ++i){
			if((bitVec & (1<<(str.charAt(i)-'a')))>0){
				return false;
			}
			bitVec |= (1<<(str.charAt(i)-'a'));
		}
		return true;
	}
	static boolean isUniqueNoDataStructure(String str){
		if(str.length()>ALPSIZE){
			return false;
		}
		
		for(int i=0, j=str.length();i<j;++i){
			for(int k=i+1; k<j; ++k){
				if(str.charAt(i)==str.charAt(k)){
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine().toLowerCase();
		boolean verdict = isUniqueBitVector(str);
		if(verdict){
			System.out.println("Is unique");
		}else{
			System.out.println("Not unique");
		}
		
	}
	
}
