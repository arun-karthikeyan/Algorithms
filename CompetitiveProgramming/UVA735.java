import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA735 {
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

	private static final String STAR = "**********************************************************************";
	private static final String END = "END OF OUTPUT";
	private static final String COMB = "NUMBER OF COMBINATIONS THAT SCORES ";
	private static final String PERM = "NUMBER OF PERMUTATIONS THAT SCORES ";
	private static final String NP1 = "THE SCORE OF ", NP2 = " CANNOT BE MADE WITH THREE DARTS.";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int sum;
		boolean[] dartScores = new boolean[61];
		dartScores[50] = true;// bulls eye shot
		dartScores[0] = true;// skip case
		for (int i = 1; i <= 20; ++i) {
			dartScores[i] = true;
			dartScores[2 * i] = true;
			dartScores[3 * i] = true;
		}
		int[] validScores = new int[43];
		for (int i = 0, j = 0; i < 61; ++i) {
			if (dartScores[i]) {
				validScores[j++] = i;
			}
		}

		while ((sum = readInt()) > 0) {
			int c = 0, p = 0;
			for (int i = 0; i < 43; ++i) {
				int d1 = validScores[i];
				for (int j = i; j < 43; ++j) {
					int d2 = validScores[j];
					for (int k = j; k < 43; ++k) {
						int d3 = validScores[k];
						int newScore = d1 + d2 + d3;
						if (newScore == sum) {
							c++;
							if (d1 == d2 && d2 == d3) {
								p++;
							} else if (d1 == d2 || d2 == d3) {
								p += 3;
							} else {
								p += 6;
							}
						}
					}
				}
			}
			if (p == 0) {
				pw.println(NP1 + sum + NP2 + "\n" + STAR);
			} else {
				pw.println(COMB + sum + " IS " + c + ".\n" + PERM + sum + " IS " + p + ".\n" + STAR);
			}
		}
		pw.println(END);

		pw.flush();
		pw.close();
	}
}
