import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA11566 {
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

	private static int N, X, T, K, memoi[][][] = new int[100][23][1001], dumplings[][] = new int[100][2];
	private static final int PRICE = 0, FAVOR = 1;

	private static int solve(int idx, int moneyUsed, int dumplingsCount) {
		if (moneyUsed < 0 || dumplingsCount < 0) {
			return Integer.MIN_VALUE;
		}
		if (idx == K) {
			return 0;
		}
		if (memoi[idx][dumplingsCount][moneyUsed] == -1) {
			int maxVal = solve(idx + 1, moneyUsed, dumplingsCount);
			for (int i = 1; i <= 2; ++i) {
				maxVal = Math.max(maxVal, (i * dumplings[idx][FAVOR])
						+ solve(idx + 1, moneyUsed - (i * dumplings[idx][PRICE]), dumplingsCount - i));
			}
			memoi[idx][dumplingsCount][moneyUsed] = maxVal;
		}
		return memoi[idx][dumplingsCount][moneyUsed];
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			N = readInt();
			X = readInt();
			T = readInt();
			K = readInt();
			if (N == 0 && X == 0 && T == 0 && K == 0) {
				break;
			}
			int dumplingsCount = 2 * (N + 1);
			int remMoney = ((X * (N + 1) * 10) / 11) - T * (N + 1);
			for (int i = 0; i < K; ++i) {
				dumplings[i][PRICE] = readInt();
				int favor = 0;
				for (int j = 0; j <= N; ++j) {
					favor += readInt();
				}
				dumplings[i][FAVOR] = favor;
				for (int j = 0; j <= dumplingsCount; ++j) {
					Arrays.fill(memoi[i][j], -1);
				}
			}
			int result = solve(0, remMoney, dumplingsCount);
			pw.printf("%.2f\n", result / (N + 1d));
		}

		pw.flush();
		pw.close();
	}
}
