import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * Maximum product sequence
 * 
 * @author arun
 *
 */
public class UVA787 {
	private static BigInteger max(BigInteger a, BigInteger b) {
		return a.compareTo(b) > 0 ? a : b;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			String[] elements = line.split("\\s+");
			int len = elements.length - 1;
			if (len > 0) {
				BigInteger max = new BigInteger(elements[0]);
				BigInteger currentMax = new BigInteger(elements[0]);
				BigInteger negToRemove = null;
				int comp = max.compareTo(BigInteger.ZERO);
				if (comp < 0) {
					negToRemove = new BigInteger(elements[0]);
				} else if (comp == 0) {
					currentMax = BigInteger.ONE;
				}
				for (int i = 1; i < len; ++i) {
					BigInteger currentElement = new BigInteger(elements[i]);
					currentMax = currentMax.multiply(currentElement);
					comp = currentMax.compareTo(BigInteger.ZERO);
					if (comp < 0) {
						if (negToRemove == null) {
							max = max(max, currentElement);
							negToRemove = currentMax;
						} else {
							max = max(max, currentMax.divide(negToRemove));
						}
					} else if (comp == 0) {
						currentMax = BigInteger.ONE;
						max = max(max, BigInteger.ZERO);
						negToRemove = null;
					} else {
						max = max(max, currentMax);
					}
				}
				pw.println(max);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
