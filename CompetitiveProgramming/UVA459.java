import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Stack;

public class UVA459 {
	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		br.readLine();
		while (testcases-- > 0) {
			String line;
			line = br.readLine().trim();
			int n = line.charAt(0) - 'A' + 1;
			boolean[][] adjMat = new boolean[n][n];
			while ((line = br.readLine()) != null && line.length() > 0) {
				int from = line.charAt(0) - 'A', to = line.charAt(1) - 'A';
				adjMat[from][to] = true;
				adjMat[to][from] = true;
			}
			int count = 0;
			boolean[] visited = new boolean[n];
			for (int i = 0; i < n; ++i) {
				if (!visited[i]) {
					count++;
					Stack<Integer> dfs = new Stack<Integer>();
					dfs.push(i);
					while (!dfs.isEmpty()) {
						int currentNode = dfs.pop();
						if (!visited[currentNode]) {
							for (int j = 0; j < n; ++j) {
								if (adjMat[currentNode][j]) {
									dfs.add(j);
								}
							}
							visited[currentNode] = true;
						}
					}
				}
			}
			pw.println(count);
			if (testcases != 0) {
				pw.println();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
