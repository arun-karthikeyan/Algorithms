import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA639 {
	private static int n, OK, END, walls;
	private static final HashMap<Integer, Integer> LOOKUP;
	static {
		LOOKUP = new HashMap<Integer, Integer>();
		for (int i = 0, vals = 1; i <= 16; ++i, vals *= 2) {
			LOOKUP.put(vals, i);
		}
	}

	private static int solve(int used) {
		int check = used | walls;
		if (check == OK) {
			return 0;
		}
		int max = -1;
		int vb = OK & (~check);
		while (vb > 0) {
			int p = vb & (-vb);
			vb -= p;
			int newUsed = p;
			// takes care of marking invalid rows
			int temp = p << n;
			while ((walls & temp) == 0 && temp < END) {
				newUsed |= temp;
				temp <<= n;
			}
			temp = p >> n;
			while ((walls & temp) == 0 && temp > 0) {
				newUsed |= temp;
				temp >>= n;
			}
			// takes care of marking invalid columns
			temp = LOOKUP.get(p);
			int clow = 1 << ((temp / n) * n), chigh = clow << n;
			temp = p << 1;
			while ((walls & temp) == 0 && temp < chigh) {
				newUsed |= temp;
				temp <<= 1;
			}
			temp = p >> 1;
			while ((walls & temp) == 0 && temp >= clow) {
				newUsed |= temp;
				temp >>= 1;
			}
			max = max(max, solve(used | newUsed) + 1);
		}
		return max;

	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
//		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("testop.txt"))));

		while ((n = Integer.parseInt(br.readLine())) != 0) {
			OK = (1 << (n * n)) - 1;
			END = OK + 1;
			walls = 0;
			for (int i = 0, b = 1; i < n; ++i) {
				String line = br.readLine();
				for (int j = 0; j < n; ++j, b *= 2) {
					if (line.charAt(j) == 'X') {
						walls |= b;
					}
				}
			}
			pw.println(solve(0));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
