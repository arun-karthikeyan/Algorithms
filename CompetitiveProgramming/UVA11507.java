

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA11507 {
	private static final HashMap<String, Integer> map;
	private static final HashMap<Integer, String> mapRev;
	private static final int[][] pointer = { { 5, 4, 0, 0, -1, -1, 0 },
			{ 4, 5, 1, 1, -1, -1, 1 }, { 2, 2, 5, 4, -1, -1, 2 },
			{ 3, 3, 4, 5, -1, -1, 3 }, { 0, 1, 2, 3, -1, -1, 4 },
			{ 1, 0, 3, 2, -1, -1, 5 }, { -1, -1, -1, -1, -1, -1, -1 } };
	static {
		map = new HashMap<String, Integer>();
		map.put("+y", 0);
		map.put("-y", 1);
		map.put("+z", 2);
		map.put("-z", 3);
		map.put("+x", 4);
		map.put("-x", 5);
		map.put("No", 6);

		mapRev = new HashMap<Integer, String>();
		mapRev.put(0, "+y");
		mapRev.put(1, "-y");
		mapRev.put(2, "+z");
		mapRev.put(3, "-z");
		mapRev.put(4, "+x");
		mapRev.put(5, "-x");
		mapRev.put(6, "No");
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int len = 0;
		while ((len = Integer.parseInt(br.readLine().trim())) != 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int currentPosition = 4;
			for (int i = 1; i < len; ++i) {
				currentPosition = pointer[currentPosition][map.get(st.nextToken())];
			}
			pw.println(mapRev.get(currentPosition));
		}

		pw.flush();
		pw.close();
		br.close();
	}
}
