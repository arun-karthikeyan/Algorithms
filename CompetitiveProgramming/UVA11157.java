import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Dynamic Frog - Greedy
 * 
 * @author arun
 *
 */
public class UVA11157 {
	private static final int TYPE = 0, DISTANCE = 1, ACTIVE = 2;
	private static final int BIG = 0, SMALL = 1;
	private static final int YES = 0, NO = 1;

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static int getMinMaxJump(int n, int d, int[][] stones) {
		int[][] landscape = new int[n + 2][3];
		landscape[0][TYPE] = BIG;
		landscape[0][DISTANCE] = 0;
		landscape[1][TYPE] = SMALL;
		landscape[1][DISTANCE] = d;
		for (int i = 0; i < n; ++i) {
			landscape[i + 2][TYPE] = stones[i][TYPE];
			landscape[i + 2][DISTANCE] = stones[i][DISTANCE];
		}
		Arrays.sort(landscape, new java.util.Comparator<int[]>() {
			@Override
			public int compare(int[] arg0, int[] arg1) {
				return arg0[DISTANCE] - arg1[DISTANCE];
			}
		});
		boolean previousSmall = false;
		int maxLeap = -1, previousDistance = 0;
		for (int i = 0; i < n + 2; ++i) {
			int currentLeap = landscape[i][DISTANCE] - previousDistance;
			if (landscape[i][TYPE] == SMALL) {
				if (previousSmall) {
					previousSmall = false;
				} else {
					landscape[i][ACTIVE] = NO;
					previousSmall = true;
					maxLeap = max(currentLeap, maxLeap);
					previousDistance = landscape[i][DISTANCE];
				}
			} else {
				previousSmall = false;
				maxLeap = max(currentLeap, maxLeap);
				previousDistance = landscape[i][DISTANCE];
			}
		}
		previousDistance = d;
		for (int i = n; i >= 0; --i) {
			int currentLeap = previousDistance - landscape[i][DISTANCE];
			if (landscape[i][TYPE] == SMALL) {
				if (landscape[i][ACTIVE] == YES) {
					maxLeap = max(currentLeap, maxLeap);
					previousDistance = landscape[i][DISTANCE];
				}
			} else {
				maxLeap = max(currentLeap, maxLeap);
				previousDistance = landscape[i][DISTANCE];
			}
		}
		return maxLeap;

	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), tc = 1;
		while (testcases-- > 0) {
			String[] info = br.readLine().trim().split(" ");
			int n = Integer.parseInt(info[0].trim()), d = Integer.parseInt(info[1].trim());
			int[][] stones = new int[n][3];
			for (int i = 0; i < n;) {
				String[] line = br.readLine().trim().split(" ");
				for (int j = 0, jLen = line.length; j < jLen; ++j, ++i) {
					String[] stoneInfo = line[j].trim().split("-");
					stones[i][DISTANCE] = Integer.parseInt(stoneInfo[1].trim());
					stones[i][TYPE] = stoneInfo[0].trim().charAt(0) == 'B' ? BIG : SMALL;
				}
			}
			pw.println("Case " + (tc++) + ": " + getMinMaxJump(n, d, stones));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
