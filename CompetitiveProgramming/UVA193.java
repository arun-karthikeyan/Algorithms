import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.Iterator;

/**
 * Graph Coloring
 * 
 * @author arun
 *
 */
public class UVA193 {
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

	private static int n, k;
	private static BitSet[] adjList;
	private static BitSet result;
	private static int maxBlack;

	private static void solve(BitSet currentSet, int col) {
		if (maxBlack < col) {
			maxBlack = col;
			result = (BitSet) currentSet.clone();
		}
		
		for (int i = col; i < n; ++i) {
			if (!currentSet.get(i) && !currentSet.intersects(adjList[i])) {
				currentSet.set(i);
				solve(currentSet, col + 1);
				currentSet.clear(i);
			}
		}

	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			n = readInt();
			k = readInt();
			adjList = new BitSet[n];
			for (int i = 0; i < n; ++i) {
				adjList[i] = new BitSet(n);
			}
			for (int i = 0; i < k; ++i) {
				int from = readInt() - 1, to = readInt() - 1;
				adjList[from].set(to);
				adjList[to].set(from);
			}
			maxBlack = 0;
			BitSet tempset = new BitSet(n);
			solve(tempset, 0);
			pw.println(maxBlack);
			Iterator<Integer> stream = result.stream().iterator();
			if (stream.hasNext()) {
				pw.print((stream.next() + 1));
			}
			while (stream.hasNext()) {
				pw.print(" " + (stream.next() + 1));
			}
			pw.println();

		}

		pw.flush();
		pw.close();
	}
}
