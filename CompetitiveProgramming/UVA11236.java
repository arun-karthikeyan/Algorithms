import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Grocery Store (special thanks to Duncan-Smith for the hint) The trick is to
 * find an upper bound and lower bound after converting everything into cents
 * 
 * @author arun
 *
 */

public class UVA11236 {
	private static final long POW = (long) 1e6;
	private static final long MAX = (long) 2e9;

	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		for (long a = 1, aLen = 2000; a <= aLen; ++a) {
			long pow = (a * a);
			pow *= pow;
			if (pow > MAX)
				break;
			for (long b = a, bLen = aLen - a; b <= bLen; ++b) {
				pow = a * b * b * b;
				if (pow > MAX)
					break;
				for (long c = b, cLen = bLen - b; c <= cLen; ++c) {
					pow = a * b * c * c;
					if (pow > MAX)
						break;
					long x = (a + b + c), y = (a * b * c);
					long num = x * POW;
					long den = y - POW;
					if (den <= 0 || (num % den) != 0)
						continue;
					long d = num / den;
					if (d < c || (y * d) > MAX || (x + d) > 2000)
						continue;
					pw.printf("%d.%02d %d.%02d %d.%02d %d.%02d\n", a / 100, a % 100, b / 100, b % 100, c / 100, c % 100,
							d / 100, d % 100);
				}
			}
		}
		pw.flush();
		pw.close();
	}
}
