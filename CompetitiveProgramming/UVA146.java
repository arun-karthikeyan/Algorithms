import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA146 {
	private static final String NO = "No Successor";
	private static String getStringFromCharArray(char[] array){
		StringBuilder sb = new StringBuilder();
		for(int i=0, iLen = array.length; i<iLen; ++i){
			sb.append(array[i]);
		}
		return sb.toString();
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		String str;

		while (!"#".equals(str = br.readLine())) {
			char[] permute = str.toCharArray();
			boolean swap = false;
			int swapIdx = -1;
			for (int i = permute.length - 1; i >= 0 && !swap; --i) {
				for (int j = i - 1; j >= 0 && !swap; --j) {
					if (permute[i] > permute[j]) {
						swap = true;
						char temp = permute[i];
						permute[i] = permute[j];
						permute[j] = temp;
						swapIdx = j;
					}
				}
			}
			if (swap) {
				if ((swapIdx + 1) != permute.length) {
					Arrays.sort(permute, swapIdx + 1, permute.length);
				}
				pw.println(getStringFromCharArray(permute));
			} else {
				pw.println(NO);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
