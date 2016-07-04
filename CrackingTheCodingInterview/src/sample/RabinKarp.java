package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RabinKarp {
	public static final int ALPHSIZE = 26;
	//only for lowercase letters & max key len = 13
	static int rabinKarpSearch(String str, String key){
		//precompute powers for generating fingerprint - MAX 14 for long
		long[] powers = new long[14];
		powers[0] = 1;
		for(int i=1; i<14; ++i){
			powers[i] = powers[i-1]*ALPHSIZE;
		}
		
		long[] strHashes = new long[str.length()-key.length()+1];
		long keyHash = 0;
		for(int i=key.length()-1, j=0; i>=0; --i, ++j){
			keyHash = keyHash + ((key.charAt(i)-'a')*powers[j]);
			strHashes[0] = strHashes[0] + ((str.charAt(i)-'a')*powers[j]);
		}
		
		for(int i=1, iLen = str.length()-key.length(), j=key.length(); i<=iLen; ++i, ++j){
			strHashes[i] = (((strHashes[i-1] - (str.charAt(j-key.length())-'a')*powers[key.length()-1])*ALPHSIZE) + (str.charAt(j)-'a')); 
		}
		
		for(int i=0, iLen=strHashes.length; i<iLen; ++i){
			if(strHashes[i]==keyHash && match(str,key,i)){
				return i;
			}
		}
		
		return -1;
	}
	
	static boolean match(String str, String key, int idx){
		for(int i=idx,j=0,jLen=key.length(); j<jLen; ++i,++j){
			if(str.charAt(i)!=key.charAt(j)){
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine().toLowerCase();
		String key = br.readLine().toLowerCase();
		System.out.println(rabinKarpSearch(str, key));
	}
}
