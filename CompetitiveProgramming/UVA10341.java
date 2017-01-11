import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10341 {
	private static double p, q, r, s, t, u;

	private static double computeFunction(double x) {
		double result = Math.pow(Math.E, -x) * p + Math.sin(x) * q + Math.cos(x) * r + Math.tan(x) * s + t * x * x + u;
		return result;
	}

	private static final String NOSOLUTION = "No solution";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// PrintWriter pw = new PrintWriter(new FileWriter("testop.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] info = line.split(" ");
			p = Integer.parseInt(info[0]);
			q = Integer.parseInt(info[1]);
			r = Integer.parseInt(info[2]);
			s = Integer.parseInt(info[3]);
			t = Integer.parseInt(info[4]);
			u = Integer.parseInt(info[5]);
			double v1 = computeFunction(0), v2 = computeFunction(1);
			if (v1 == 0) {
				pw.printf("%1.4f\n", 0.0);
			} else if (v2 == 0) {
				pw.printf("%1.4f\n", 1.0);
			} else if (v1 > 0 && v2 < 0) {
				double low = 0, high = 1, mid = 0;
				double error = 1e-9, val = 0;
				while (Math.abs((val = computeFunction(mid = ((low + high) / 2d)))) > error) {
					if (val > 0) {
						low = mid;
					} else {
						high = mid;
					}
				}
				pw.printf("%1.4f\n", mid);
			} else {
				pw.println(NOSOLUTION);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
