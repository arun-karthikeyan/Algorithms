import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class UVA10172 {
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

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt(), s = readInt(), q = readInt(), totalCargoes = 0;
			Queue<Integer>[] stations = new LinkedList[n];

			for (int i = 0; i < n; ++i) {
				int qSize = readInt();
				stations[i] = new LinkedList<Integer>();
				for (int j = 0; j < qSize; ++j) {
					if (j < q) {
						stations[i].add(readInt() - 1);
						totalCargoes++;
					} else {
						readInt();
					}
				}
			}

			Stack<Integer> cargoCarrier = new Stack<Integer>();
			int steps = 0, loaded = 0, unloaded = 0, cargosDone = 0;
			while (!cargoCarrier.isEmpty() || cargosDone < totalCargoes) {
				// try to unload cargos to station A and station B
				while (!cargoCarrier.isEmpty()) {
					if(cargoCarrier.peek() == (steps % n)){
						cargoCarrier.pop();
						cargosDone++;
					}else if(stations[(steps%n)].size()<q){
						stations[(steps%n)].add(cargoCarrier.pop());
					}else{
						break;
					}
					unloaded++;
				}
				
				// try to load coargos from station B until capacity of stack
				while (!stations[(steps % n)].isEmpty() && (cargoCarrier.size() < s)) {
					cargoCarrier.push(stations[(steps % n)].remove());
					loaded++;
				}

				steps++;
			}
			int result = ((steps-1)*2)+loaded+unloaded;
			pw.println(result);
		}

		pw.flush();
		pw.close();
	}
}
