import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 2D Heiroglyphs decoder
 * 
 * @author arun
 *
 */
public class UVA10851 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			ArrayList<String> encodedString = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null && line.length() > 0) {
				encodedString.add(line);
			}
			int messageLen = encodedString.get(0).length() - 2;
			int[] message = new int[messageLen];
			for (int j = 1; j < 9; ++j) {
				String currentRow = encodedString.get(j);
				for (int i = 1; i <= messageLen; ++i) {
					int bit = currentRow.charAt(i) == '/' ? 0 : 1;
					message[i - 1] |= (bit << (j - 1));
				}
			}
			for (int i = 0; i < messageLen; ++i) {
				pw.print((char) message[i]);
			}
			pw.println();

		}

		br.close();
		pw.flush();
		pw.close();
	}

}
