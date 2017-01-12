import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Watering Grass - Greedy + some preliminary geometry
 * 
 * @author arun
 *
 */
public class UVA10382 {
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

	static class Interval {
		double start;
		double end;

		public Interval(double start, double end) {
			this.start = start;
			this.end = end;
		}
	}

	private static double min(double a, double b) {
		return a < b ? a : b;
	}

	private static double max(double a, double b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		double n, l, w;
		while (true) {
			n = readInt();
			l = readInt();
			w = readInt();
			if (n == 0d && l == 0d && w == 0d) {
				break;
			}
			ArrayList<Interval> intervals = new ArrayList<Interval>((int) n);

			for (int i = 0; i < n; ++i) {
				double p = readInt(), r = readInt();
				if (2 * r >= w) {
					double dx = Math.sqrt(r * r - (w * w / 4d));
					intervals.add(new Interval(max(p - dx, 0), min(p + dx, l)));
				}
			}

			if (intervals.isEmpty()) {
				pw.println("-1");
				continue;
			}

			Collections.sort(intervals, new java.util.Comparator<Interval>() {
				@Override
				public int compare(Interval arg0, Interval arg1) {
					// TODO Auto-generated method stub
					int c1 = Double.compare(arg0.start, arg1.start);
					if (c1 == 0) {
						return Double.compare(arg1.end, arg0.end);
					} else {
						return c1;
					}
				}

			});

			int sprinklerCount = 1;
			Interval in = intervals.get(0);
			if (in.start != 0) {
				pw.println("-1");
				continue;
			}
			double lengthCovered = in.end;
			boolean possible = true;

			for (int i = 1, iLen = intervals.size(); i < iLen && lengthCovered < l;) {
				in = intervals.get(i);
				if (in.start > lengthCovered) {
					possible = false;
					break;
				}
				double maxPossibleCoverage = in.end;
				int idx = i;
				i++;
				while (i < iLen && (in = intervals.get(i)).start <= lengthCovered) {
					if (maxPossibleCoverage < in.end) {
						maxPossibleCoverage = in.end;
						idx = i;
					}
					++i;
				}
				lengthCovered = intervals.get(idx).end;
				sprinklerCount++;
			}
			if (!possible || lengthCovered < l) {
				pw.println("-1");
			} else {
				pw.println(sprinklerCount);
			}
		}

		pw.flush();
		pw.close();
	}
}
