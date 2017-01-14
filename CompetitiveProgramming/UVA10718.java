import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10718 {
	private static long getOKBits(long n) {
		for (long val = 1; val < n; val *= 2) {
			n |= val;
		}
		return n;
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			String[] info = line.split(" ");
			long n = Long.parseLong(info[0]), l = Long.parseLong(info[1]), u = Long.parseLong(info[2]);
			long ok2 = getOKBits(u);
			long bitsToChange = (ok2 & (((1l << 32) - 1) & ~n));
			String bitsToChangeStr = Long.toBinaryString(bitsToChange);
			long m = l;
			for (int i = 0, iLen = bitsToChangeStr.length(); i < iLen; ++i) {
				if (bitsToChangeStr.charAt(i) == '1') {
					long bitToSet = (1l << (iLen - i - 1));
					long temp = m | bitToSet;
					if (temp <= u) {
						m = temp;
					} else {
						temp = (temp & (~(bitToSet - 1)));
						if (temp <= u) {
							m = temp;
						}
					}
				} else {
					long bitToUnSet = (1l << (iLen - i - 1));
					long temp = (m & (~bitToUnSet));
					if (temp >= l) {
						m = temp;
					}
				}
			}
			pw.println(m);

		}

		br.close();
		pw.flush();
		pw.close();
	}

}
