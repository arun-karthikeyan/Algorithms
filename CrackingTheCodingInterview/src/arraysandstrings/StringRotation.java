package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringRotation {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str1 = br.readLine();
		String str2 = br.readLine();
		//code to check if str2 is a rotation of str1
		if((str2+str2).contains(str1)){
			System.out.println("Rotation");
		}else{
			System.out.println("Not a Rotation");
		}
	}
}
