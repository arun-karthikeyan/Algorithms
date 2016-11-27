import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class UVA10813 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static int readByte() {
		if (totalchars < 0)
			return 0;
		if (offset >= totalchars) {
			offset = 0;
			try {
				totalchars = stream.read(buffer);
			} catch (IOException e) {
				return 0;
			}
			if (totalchars <= 0)
				return -1;
		}
		return buffer[offset++];
	}
 
	private static int readInt() {
		int number = readByte();
 
		while (eolchar(number))
			number = readByte();
 
		int sign = 1;
		int val = 0;
 
		if (number == '-') {
			sign = -1;
			number = readByte();
		}
 
		do {
			if ((number < '0') || (number > '9'))
				return 0;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));
 
		return sign * val;
	}
 
	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}
	public static void main(String[] args) throws Exception {
		stream = System.in;
		//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = readInt();
		while (testcases-- > 0) {
			int currentNum = -1;
			HashMap<Integer, Integer> rowMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> colMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> diagMap = new HashMap<Integer, Integer>();
			

			for(int i=0; i<2; ++i){
				for(int j=0; j<5; ++j){
					currentNum = readInt();
					rowMap.put(currentNum, i);
					colMap.put(currentNum, j);
					
					if(i==j){
						diagMap.put(currentNum, 0);
					}else if((i+j)==4){
						diagMap.put(currentNum, 1);
					}
					
				}
			}
			currentNum = readInt();
			rowMap.put(currentNum, 2);
			colMap.put(currentNum, 0);
			
			currentNum = readInt();
			rowMap.put(currentNum, 2);
			colMap.put(currentNum, 1);
			
			currentNum = readInt();
			rowMap.put(currentNum, 2);
			colMap.put(currentNum, 3);
			
			currentNum = readInt();
			rowMap.put(currentNum, 2);
			colMap.put(currentNum, 4);
			
			for(int i=3; i<5; ++i){
				for(int j=0; j<5; ++j){
					currentNum = readInt();
					rowMap.put(currentNum, i);
					colMap.put(currentNum, j);
					if(i==j){
						diagMap.put(currentNum, 0);
					}else if((i+j)==4){
						diagMap.put(currentNum, 1);
					}
					
				}
			}
			
			 int[] rowSums = new int[5], colSums = new int[5], diagSums = new int[2];
			 Arrays.fill(diagSums, 1);
			 rowSums[2]++;colSums[2]++;
			 int wonNum = -1;
			 currentNum = 0;
			 while(currentNum<75){
				 int temp = readInt();
				 if(wonNum!=-1){
					 currentNum++;
					 continue;
				 }
				 if(rowMap.containsKey(temp)){
					 rowSums[rowMap.get(temp)]++;
					 if(rowSums[rowMap.get(temp)]==5){
						 wonNum = currentNum+1;
					 }
				 }
				 if(wonNum!=-1){
					 currentNum++;
					 continue;
				 }
				 if(colMap.containsKey(temp)){
					 colSums[colMap.get(temp)]++;
					 if(colSums[colMap.get(temp)]==5){
						 wonNum = currentNum+1;
					 }
				 }
				 if(wonNum!=-1){
					 currentNum++;
					 continue;
				 }
				 if(diagMap.containsKey(temp)){
					 diagSums[diagMap.get(temp)]++;
					 if(diagSums[diagMap.get(temp)]==5){
						 wonNum = currentNum+1;
					 }
				 }
				 currentNum++;
			 }
			 pw.println("BINGO after "+wonNum+" numbers announced");
		}

		pw.flush();
		pw.close();
	}
}
