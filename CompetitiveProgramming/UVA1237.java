import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Expert Enough ? - Works faster with a HashSet than an ArrayList
 * 
 * @author arun
 *
 */
public class UVA1237 {
	private static final boolean SP = true, EP = false;

	static class Price {
		String make;
		boolean type;
		int price;

		public Price(String make, int price, boolean type) {
			this.make = make;
			this.price = price;
			this.type = type;
		}
	}

	private static int binarySearch(Price[] db, int key) {
		int low = 0, high = db.length - 1;
		while (low <= high) {
			int mid = (high + low) >> 1;
			if (key < db[mid].price) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return low - 1;
	}

	private static final String UNDETERMINED = "UNDETERMINED";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			int d = Integer.parseInt(br.readLine());
			Price[] db = new Price[d * 2];
			for (int i = 0; i < d; ++i) {
				String[] carInfo = br.readLine().split(" ");
				db[i * 2] = new Price(carInfo[0], Integer.parseInt(carInfo[1]), SP);
				db[i * 2 + 1] = new Price(carInfo[0], Integer.parseInt(carInfo[2]), EP);
			}
			Arrays.sort(db, new java.util.Comparator<Price>() {
				@Override
				public int compare(Price arg0, Price arg1) {
					// TODO Auto-generated method stub
					return arg0.price - arg1.price;
				}
			});
			HashSet<String> make = new HashSet<String>();
			String[] results = new String[2 * d];
			for (int i = 0, iLen = db.length; i < iLen; ++i) {
				if (db[i].type == SP) {
					make.add(db[i].make);
				} else {
					make.remove(db[i].make);
				}
				if (make.size() == 1) {
					results[i] = make.iterator().next();
				} else {
					results[i] = UNDETERMINED;
				}
			}
			int q = Integer.parseInt(br.readLine());
			for (int i = 0; i < q; ++i) {
				int budget = Integer.parseInt(br.readLine());
				int pos = binarySearch(db, budget);
				if (pos == -1 || pos == db.length) {
					pw.println(UNDETERMINED);
				} else {
					pw.println(results[pos]);
				}
			}
			if (testcases != 0) {
				pw.println();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
