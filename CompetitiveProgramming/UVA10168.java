import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA10168 {
	private static int[] primesList;
	private static final int MAX = (int) 1e7;
	private static final String IMPOSSIBLE = "Impossible.";
	private static void performSeive() {
		primesList = new int[664579];
		boolean[] seive = new boolean[MAX + 1];
		for (int i = 2, k=0; i <= MAX; ++i) {
			if (!seive[i]) {
				primesList[k++] = i;
				for (int j = 2 * i; j <= MAX; j += i) {
					if (!seive[j]) {
						seive[j] = true;
					}
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
//		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String num;
		long start = System.currentTimeMillis();
		performSeive();
		
		while ((num = br.readLine()) != null) {
			int number = Integer.parseInt(num);
			if (number < 8) {
				pw.println(IMPOSSIBLE);
			} else {
				String preString;
				if (number % 2 == 0) {
					preString = "2 2 ";
					number -= 4;
				} else {
					preString = "2 3 ";
					number -= 5;
				}
				for (int i = 0, iLen = primesList.length; i < iLen; ++i) {
					int firstNum = primesList[i];
					int secondNum = Math.abs(number - firstNum);
					if (Arrays.binarySearch(primesList, secondNum)>=0) {
						pw.println(preString + firstNum + " " + secondNum);
						break;
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		
		br.close();
		pw.println(end-start);
		pw.flush();
		pw.close();
	}
}
