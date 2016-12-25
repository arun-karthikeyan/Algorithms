import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
/**
 * Forests
 * @author arun
 *
 */
public class UVA10227 {
	private static boolean isEqual(boolean[] a, boolean[] b, int t){
		for(int i=0; i<t; ++i){
			if(a[i]!=b[i]){
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			String[] pt = br.readLine().split(" ");
			int p = Integer.parseInt(pt[0]), t = Integer.parseInt(pt[1]);
			String rel;
			@SuppressWarnings("unchecked")
			boolean[][] adjMat = new boolean[p][t];
			while ((rel = br.readLine()) != null && rel.length() > 0) {
				String[] info = rel.split(" ");
				adjMat[Integer.parseInt(info[0]) - 1][Integer.parseInt(info[1]) - 1] = true;
			}
			boolean[] valid = new boolean[p];
			Arrays.fill(valid, true);
			for (int i = 0; i < p; ++i) {
				if (valid[i]) {
					for(int j=i+1; j<p; ++j){
						if(valid[j]){
							if(isEqual(adjMat[i], adjMat[j], t)){
								valid[j] = false;
							}
						}
					}
				}
			}
			int rumorCount = 0;
			for(int i=0; i<p; ++i){
				if(valid[i]){
					rumorCount++;
				}
			}
			pw.println(rumorCount);
			if(testcases!=0){
				pw.println();
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
