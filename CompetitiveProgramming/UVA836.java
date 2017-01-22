import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA836 {
	private static int[][] makeCumlativeArray(ArrayList<String> matrix, int n) {
		int[][] array = new int[n][n];
		for (int i = 0; i < n; ++i) {
			String row = matrix.get(i);
			for (int j = 0; j < n; ++j) {
				int val = row.charAt(j) - '0';
				array[i][j] = val;
				if (i > 0)
					array[i][j] += array[i - 1][j];
				if (j > 0)
					array[i][j] += array[i][j - 1];
				if (i > 0 && j > 0)
					array[i][j] -= array[i - 1][j - 1];
			}
		}
		return array;
	}

	private static int getMax(int[][] array, int n) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				for (int k = i; k < n; ++k) {
					for (int l = j; l < n; ++l) {
						int sum = array[k][l];
						if (i > 0)
							sum -= array[i - 1][l];
						if (j > 0)
							sum -= array[k][j - 1];
						if (i > 0 && j > 0)
							sum += array[i - 1][j - 1];
						if (sum == ((k - i + 1) * (l - j + 1))) {
							max = max(max, sum);
						}
					}
				}
			}
		}
		return max == Integer.MIN_VALUE ? 0 : max;
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			String row;
			ArrayList<String> info = new ArrayList<String>();
			while ((row = br.readLine()) != null && row.length() > 0) {
				info.add(row);
			}
			int n = info.size();
			int[][] vals = new int[n][n];
			for (int i = 0; i < n; ++i) {
				row = info.get(i);
				for (int j = 0; j < n; ++j) {
					vals[i][j] = row.charAt(j) - '0';
				}
			}
			pw.println(getMax(makeCumlativeArray(info, n),n));
			if (testcases != 0) {
				pw.println();
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
