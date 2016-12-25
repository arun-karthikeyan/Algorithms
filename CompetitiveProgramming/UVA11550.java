import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class UVA11550 {
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

	private static long readLong() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		long val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9')) {
				// return sign*val;
				return 0;
			}
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	static class Edge {
		int v1, v2;

		public Edge(int v1, int v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public int hashCode() {
			return (v1 * 23 + v2 * 7) % Integer.MAX_VALUE;
		}

		public boolean equals(Object o) {
			Edge o2 = (Edge) o;
			return ((this.v1 == o2.v1 && this.v2 == o2.v2) || (this.v2 == o2.v1 && this.v1 == o2.v2));
		}

	}

	private static final String YES = "Yes", NO = "No";

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			int V = readInt(), E = readInt();
			int[][] incidenceMatrix = new int[V][E];
			for (int i = 0; i < V; ++i) {
				for (int j = 0; j < E; ++j) {
					incidenceMatrix[i][j] = readInt();
				}
			}
			boolean result = true;
			HashSet<Edge> edgeSet = new HashSet<Edge>();
			for (int i = 0; i < E; ++i) {
				ArrayList<Integer> vertices = new ArrayList<Integer>();
				for (int j = 0; j < V; ++j) {
					if (incidenceMatrix[j][i] == 1) {
						vertices.add(j);
					}
				}
				if (vertices.size() == 2) {
					if (!edgeSet.add(new Edge(vertices.get(0), vertices.get(1)))) {
						result = false;
						break;
					}
				} else {
					result = false;
					break;
				}
			}
			if (result) {
				pw.println(YES);
			} else {
				pw.println(NO);
			}
		}

		pw.flush();
		pw.close();
	}
}
