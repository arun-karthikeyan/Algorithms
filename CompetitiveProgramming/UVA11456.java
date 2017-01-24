import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Train sorting, required some thinking to understand the solution
 * not really straight forward for first timers
 * @author arun
 *
 */
public class UVA11456 {
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

	private static int binarySearchInsertPos(ArrayList<Integer> array, int key, Comparator<Integer> c) {
		int low = 0, high = array.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, compare = c.compare(key, array.get(mid));
			if (compare < 0) {
				high = mid - 1;
			} else if (compare > 0) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		Comparator<Integer> ac = Comparator.naturalOrder();
		Comparator<Integer> dc = Comparator.reverseOrder();
		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			ArrayList<Integer> lis = new ArrayList<Integer>();
			ArrayList<Integer> lds = new ArrayList<Integer>();
			int max = 0, array[] = new int[n];
			for(int i=0; i<n; ++i){
				array[i] = readInt();
			}
			for (int i = n-1; i >=0; --i) {
				int currentVal = array[i];
				int lisIp = binarySearchInsertPos(lis, currentVal, ac);
				int ldsIp = binarySearchInsertPos(lds, currentVal, dc);
				max = Math.max(max, ldsIp + lisIp + 1);
				if (lisIp < lis.size()) {
					lis.set(lisIp, currentVal);
				} else {
					lis.add(currentVal);
				}
				if (ldsIp < lds.size()) {
					lds.set(ldsIp, currentVal);
				} else {
					lds.add(currentVal);
				}
			}
			pw.println(max);
		}

		pw.flush();
		pw.close();
	}
}
