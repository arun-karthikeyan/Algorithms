import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class UVA11988 {
	private static final char HOME = '[', END = ']';
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String currentRow;
	while((currentRow=br.readLine())!=null){
		LinkedList<String> correctedList = new LinkedList<String>();
		int idx = 0, len = currentRow.length();
		char lastJump = END;
		StringBuilder sb = new StringBuilder();
		while(idx<len){
			char currentChar = currentRow.charAt(idx++);
			if(currentChar==HOME || currentChar==END){
				if(sb.length()>0){
					if(lastJump==HOME){
						correctedList.addFirst(sb.toString());
					}else{
						correctedList.addLast(sb.toString());
					}
					sb = new StringBuilder();
				}
				lastJump = currentChar;
			}else{
				sb.append(currentChar);
			}
		}
		if(sb.length()>0){
			if(lastJump==HOME){
				correctedList.addFirst(sb.toString());
			}else{
				correctedList.addLast(sb.toString());
			}
			sb = new StringBuilder();
		}
		
		Iterator<String> correctedOp = correctedList.iterator();
		while(correctedOp.hasNext()){
			pw.print(correctedOp.next());
		}
		pw.println();
		
		
	}

	br.close();
	pw.flush();
	pw.close();
}
}
