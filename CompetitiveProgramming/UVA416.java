import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * LED Testing - pretty good problem, can be solved easily if one has good
 * knowledge of bitmasks
 * 
 * @author arun
 *
 */
public class UVA416 {
	private static final HashMap<Integer, Integer> LEDLOOKUP;

	static {
		LEDLOOKUP = new HashMap<Integer, Integer>();
		LEDLOOKUP.put(0, 0b1111110);
		LEDLOOKUP.put(1, 0b0110000);
		LEDLOOKUP.put(2, 0b1101101);
		LEDLOOKUP.put(3, 0b1111001);
		LEDLOOKUP.put(4, 0b0110011);
		LEDLOOKUP.put(5, 0b1011011);
		LEDLOOKUP.put(6, 0b1011111);
		LEDLOOKUP.put(7, 0b1110000);
		LEDLOOKUP.put(8, 0b1111111);
		LEDLOOKUP.put(9, 0b1111011);
	}

	private static int n, led[];
	private static final String MATCH = "MATCH";
	private static final String MISMATCH = "MISMATCH";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		while (true) {
			n = Integer.parseInt(br.readLine().trim());
			if (n == 0) {
				break;
			}
			led = new int[n];
			for (int i = 0; i < n; ++i) {
				String row = br.readLine().trim();
				for (int j = 0; j < 7; ++j) {
					led[i] = (led[i] << 1) + (row.charAt(j) == 'Y' ? 1 : 0);
				}
			}
			boolean result = false;
			for (int i = 9, iLen = n - 1; i >= iLen && !result; --i) {
				int j, k, bb = 0;
				for (j = 0, k = i; j < n; ++j, --k) {
					int bit = LEDLOOKUP.get(k);
					if (((led[j] & (~bb)) == led[j]) && ((led[j] | bit) == bit)) {
						bb |= ((~led[j]) & bit);
					} else {
						break;
					}
				}
				if (j == n) {
					result = true;
				}
			}
			if (result) {
				pw.println(MATCH);
			} else {
				pw.println(MISMATCH);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
