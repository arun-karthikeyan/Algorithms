import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * String Popping
 * 
 * @author arun
 *
 */
public class UVA1261 {
	private static boolean possible(String s) {
		if (s.length() == 0) {
			return true;
		}
		boolean result = false;
		int count = 1;
		for (int i = 1, iLen = s.length(); i < iLen && !result; ++i) {
			if (s.charAt(i) == s.charAt(i - 1)) {
				count++;
			} else {
				if (count > 1) {
					result = possible(s.substring(0, i - count) + s.substring(i));
				}
				count = 1;
			}
		}
		if (count > 1) {
			result = possible(s.substring(0, s.length() - count));
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			if (possible(br.readLine())) {
				pw.println(1);
			} else {
				pw.println(0);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
