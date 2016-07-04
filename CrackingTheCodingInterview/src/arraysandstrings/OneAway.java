package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OneAway {
	static boolean isOneAway(String one, String two){
		int len1 = one.length(), len2 = two.length();
		if(Math.abs(len1-len2)>1){
			return false;
		}
		if(len1==len2){
			return checkOneReplace(one, two);
		}
		else
		if(len1>len2){
			return checkOneAdd(one, two);
		}
		else{
			return checkOneAdd(two, one);
		}
	}

	static boolean checkOneAdd(String one, String two){
		//assuming one is always one bigger than two in size
		boolean deleted = false;
		for(int i=0, j=one.length(), l=two.length(), k=0;i<j && k<l; ++i, ++k){
			if(one.charAt(i)!=two.charAt(k)){
				if(deleted){
					return false;
				}
				deleted = true;
				--k;
			}
		}
		return true;
	}
	
	static boolean checkOneReplace(String one, String two){
		boolean replaced = false;
		
		for(int i=0, j=one.length();i<j;++i){
			if(one.charAt(i)!=two.charAt(i)){
				if(replaced){
					return false;
				}
				replaced = true;
			}
		}
		return true;
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str1 = br.readLine(), str2 = br.readLine();
		System.out.println(isOneAway(str1, str2));
	}
}