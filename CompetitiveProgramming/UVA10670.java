import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Work Reduction - Sample greedy
 * 
 * @author arun
 *
 */
public class UVA10670 {
	static class Agency {
		String name;
		int cost;

		public Agency(String name, int cost) {
			this.name = name;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), tc = 1;
		while (testcases-- > 0) {
			String[] info = br.readLine().trim().split(" ");
			int a = Integer.parseInt(info[0].trim()), b = Integer.parseInt(info[1].trim()),
					m = Integer.parseInt(info[2].trim());
			Agency[] agencies = new Agency[m];
			for (int i = 0; i < m; ++i) {
				info = br.readLine().trim().split(":");
				String name = info[0].trim();
				info = info[1].trim().split(",");
				int c1 = Integer.parseInt(info[1].trim());
				int c2 = Integer.parseInt(info[0].trim());
				int totalCost = 0;
				int rem = a;
				while (rem > b) {
					int p1 = (rem + 1) / 2;
					if ((rem - p1) >= b) {
						int unitRed = p1 * c2;
						rem -= p1;
						totalCost += (c1 < unitRed ? c1 : unitRed);
					} else {
						totalCost += ((rem - b) * c2);
						rem = b;
					}
				}
				agencies[i] = new Agency(name, totalCost);
			}
			Arrays.sort(agencies, new java.util.Comparator<Agency>() {
				@Override
				public int compare(Agency arg0, Agency arg1) {
					// TODO Auto-generated method stub
					int c1 = Integer.compare(arg0.cost, arg1.cost);
					if (c1 == 0) {
						return arg0.name.compareTo(arg1.name);
					}
					return c1;
				}
			});
			pw.println("Case " + (tc++));
			for (int i = 0; i < m; ++i) {
				pw.println(agencies[i].name + " " + agencies[i].cost);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
