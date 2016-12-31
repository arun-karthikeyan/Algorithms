import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
/**
 * 10483 - The Sum Equals the Product
 * @author arun
 *
 */
public class UVA10483 {
	static class Result {
		int sum;
		int a;
		int b;
		int c;

		public Result(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.sum = a + b + c;
		}
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String[] range = br.readLine().split(" ");
		int start = (int) (Double.parseDouble(range[0]) * 100);
		int end = (int) (Double.parseDouble(range[1]) * 100);
		ArrayList<Result> resultSet = new ArrayList<Result>();
		for (int aStart = 1, a = aStart, aLen = end, breakPoint = 10000 * end; a < aLen; ++a) {
			if (a * a * a > breakPoint) {
				break;
			}
			for (int b = a, bLen = aLen - a; b < bLen; ++b) {
				if (a * b * b > breakPoint) {
					break;
				}
				int p1 = a + b, p2 = a * b, num = 10000 * p1, den = p2 - 10000;
				if (den <= 0 || den > num || num % den != 0)
					continue;
				int c = num / den;
				int sum = p1 + c, prod = p2 * c;
				if (a * b * c > breakPoint || c < b || sum < start || sum > end || sum * 10000 != prod)
					continue;
				resultSet.add(new Result(a, b, c));
			}
		}
		Collections.sort(resultSet, new java.util.Comparator<Result>() {

			@Override
			public int compare(Result arg0, Result arg1) {
				// TODO Auto-generated method stub
				return arg0.sum - arg1.sum;
			}

		});
		for (int i = 0, iLen = resultSet.size(); i < iLen; ++i) {
			Result currentResult = resultSet.get(i);
			int a = currentResult.a, b = currentResult.b, c = currentResult.c, sum = a + b + c;
			pw.printf("%d.%02d = %d.%02d + %d.%02d + %d.%02d = %d.%02d * %d.%02d * %d.%02d\n", sum / 100, sum % 100,
					a / 100, a % 100, b / 100, b % 100, c / 100, c % 100, a / 100, a % 100, b / 100, b % 100, c / 100,
					c % 100);
		}
		br.close();
		pw.flush();
		pw.close();
	}
}
