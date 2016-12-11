import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

public class HelperMethod {
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

	private static String make32BitBin(int val) {
		String bin = Integer.toBinaryString(val);
		StringBuilder sb = new StringBuilder();
		for (int i = bin.length(); i < 32; ++i) {
			sb.append("0");
		}
		return sb.toString() + bin;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("testop.txt")), true);

		int n = 10000;
		boolean printCount = false, printBase = false, printResultMap = false, printResultMapCount = true;
		for (int i = 1; i <= n; ++i) {
			pw.print(i + " : ");
			if (printBase) {
				pw.print("{2");
			}
			int count = 1;
			HashMap<Integer, String> resultMap = new HashMap<Integer, String>();
			HashMap<Integer, Integer> resultMapCounter = new HashMap<Integer, Integer>();
			for (int j = 2; j <= i; ++j) {
				int maxPower = (int) (Math.log(i) / Math.log(j));

				int exp = (i / (int) (Math.pow(j, maxPower)));
//				if (exp == 1 && maxPower>1) {
				if(exp==1){
					if (printResultMap) {
						if (resultMap.containsKey(maxPower)) {
							resultMap.put(maxPower, resultMap.get(maxPower) + ", " + j);
						} else {
							resultMap.put(maxPower, String.valueOf(j));
						}
					}
					if (printResultMapCount) {
						if (resultMapCounter.containsKey(maxPower)) {
							resultMapCounter.put(maxPower, resultMapCounter.get(maxPower) + 1);
						} else {
							resultMapCounter.put(maxPower, 1);
						}
					}
					if (printBase) {
						pw.print(", " + j);
					}
					count++;
				}

			}
			
			if (printResultMap) {
				Iterator<Integer> resultMapIterator = resultMap.keySet().iterator();
				pw.print("{");
				if (resultMapIterator.hasNext()) {
					int currentKey = resultMapIterator.next();
					pw.print("{" + currentKey + " : " + resultMap.get(currentKey)+"}");
				}
				while (resultMapIterator.hasNext()) {
					int currentKey = resultMapIterator.next();
					pw.print(", {" + currentKey + " : " + resultMap.get(currentKey)+"}");
				}
				pw.print("}");
			}
			
			if (printResultMapCount) {
				Iterator<Integer> resultMapIterator = resultMapCounter.keySet().iterator();
				pw.print("{");
				if (resultMapIterator.hasNext()) {
					int currentKey = resultMapIterator.next();
					pw.print("{" + currentKey + " : " + resultMapCounter.get(currentKey)+"}");
				}
				while (resultMapIterator.hasNext()) {
					int currentKey = resultMapIterator.next();
					pw.print(", {" + currentKey + " : " + resultMapCounter.get(currentKey)+"}");
				}
				pw.print("}");
			}
			if (printBase) {
				pw.print("}");
			}
			if (printCount) {
				pw.print("c = " + count);
			}
			pw.println();

		}

		pw.flush();
		pw.close();
	}
}
