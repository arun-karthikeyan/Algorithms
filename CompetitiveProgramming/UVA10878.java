import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Decode the tape
 * 
 * @author arun
 *
 */
public class UVA10878 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		ArrayList<String> message = new ArrayList<String>();
		String line;
		while ((line = br.readLine()) != null) {
			message.add(line);
		}
		int messageSize = message.size() - 2;
		for (int i = 1; i < messageSize; ++i) {
			String currentChar = message.get(i);
			int c = 0;
			for (int j = 1, jLen = currentChar.length() - 1, shift = jLen - 3; j < jLen; ++j) {
				char ch = currentChar.charAt(j);
				if (ch != '.') {
					int bit = ch == ' ' ? 0 : 1;
					c |= (bit << (shift--));
				}
			}
			pw.print((char) c);
		}
		pw.println();
		br.close();
		pw.flush();
		pw.close();
	}
}
