import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
/**
 * Trouble of 13-Dots - Top down approach
 * @author arun
 *
 */
public class UVA10819 {
	private static int m, n, items[][] = new int[100][2], memoi[][] = new int[100][10201];
	private static final int COST = 0, FAVOUR = 1;

	private static int maxFavour(int purchaseAmount, int idx) {
		if (purchaseAmount > (m + 200)) {
			return Integer.MIN_VALUE;
		}
		if (idx == n) {
			if (purchaseAmount <= m || purchaseAmount > 2000) {
				return 0;
			}
			return Integer.MIN_VALUE;
		}
		if (memoi[idx][purchaseAmount] == -1) {
			memoi[idx][purchaseAmount] = Math.max(maxFavour(purchaseAmount, idx + 1),
					items[idx][FAVOUR] + maxFavour(purchaseAmount + items[idx][COST], idx + 1));
		}
		return memoi[idx][purchaseAmount];
	}
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
				return -1;
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
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while(true) {
			m = readInt();
			n = readInt();
			if(m==-1 && n==-1){
				break;
			}
			for (int i = 0; i < n; ++i) {
				items[i][COST] = readInt();
				items[i][FAVOUR] = readInt();
				Arrays.fill(memoi[i], -1);
			}
			pw.println(maxFavour(0, 0));
		}

		pw.flush();
		pw.close();
	}
}
