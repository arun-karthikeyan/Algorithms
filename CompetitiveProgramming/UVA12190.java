import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
/**
 * Electric Bill - Another interesting binary search the answer problem
 * @author arun
 *
 */
public class UVA12190 {
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

	private static int billToCWH(int bill) {
		int cwh = 0;
		if (bill > 200) {
			cwh = 100;
			bill -= 200;
			if (bill > 29700) {
				cwh = 10000;
				bill -= 29700;
				if (bill > 4950000) {
					cwh = 1000000;
					bill -= 4950000;
					if (bill > 0) {
						cwh = cwh + (bill / 7);
					}
				} else {
					cwh = cwh + (bill / 5);
				}
			} else {
				cwh = cwh + (bill / 3);
			}
		} else {
			cwh = bill / 2;
		}
		return cwh;
	}

	private static int cwhToBill(int cwh) {
		int bill = 0;
		if (cwh > 100) {
			bill = 200;
			cwh -= 100;
			if (cwh > 9900) {
				bill = bill + 29700;
				cwh -= 9900;
				if (cwh > 990000) {
					bill = bill + 4950000;
					cwh -= 990000;
					if (cwh > 0) {
						bill = bill + cwh * 7;
					}
				} else {
					bill = bill + cwh * 5;
				}
			} else {
				bill = bill + cwh * 3;
			}
		} else {
			bill = cwh * 2;
		}
		return bill;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// PrintWriter pw = new PrintWriter(new BufferedWriter(new
		// OutputStreamWriter(new FileOutputStream(new File("testop.txt")))));

		int a, b;
		while (true) {
			a = readInt();
			b = readInt();
			if (a == 0 && b == 0) {
				break;
			}
			int totalCWH = billToCWH(a);
			int low = 1, high = totalCWH / 2, myFinalBill = 1;
			while (low <= high) {
				int mid = (low + high) >> 1, myBill = cwhToBill(mid);
				int neighbourCWH = totalCWH - mid;
				int neighbourBill = cwhToBill(neighbourCWH);
				int difference = neighbourBill-myBill;
				if (b < difference) {
					low = mid + 1;
				} else if (b > difference) {
					high = mid - 1;
				} else {
					myFinalBill = myBill;
					break;
				}
			}
			pw.println(myFinalBill);
		}

		pw.flush();
		pw.close();
	}
}
