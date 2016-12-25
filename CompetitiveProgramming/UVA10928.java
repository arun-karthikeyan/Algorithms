import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10928 {

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			int min = 1001;
			int p = Integer.parseInt(br.readLine());
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < p; ++i) {
				int neighbors = br.readLine().split(" ").length;
				if (neighbors<min) {
					min = neighbors;
					result = new StringBuilder();
					result.append(" " + (i + 1));
				}else if(neighbors==min){
					result.append(" " + (i + 1));
				}
			}
			pw.println(result.substring(1));
			if (testcases != 0) {
				br.readLine();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
