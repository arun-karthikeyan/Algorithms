import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Cube Painting
 * 
 * @author arun
 *
 */
public class UVA253 {
	private static boolean setEquals(char[] s1, char[] s2) {
		return s1[0] == s2[0] && s1[1] == s2[1];
	}

	private static char[] sortSubset(char a, char b) {
		if (a > b) {
			return new char[] { b, a };
		} else {
			return new char[] { a, b };
		}
	}

	private static char[][] getSortedSubset(String val) {
		return new char[][] { sortSubset(val.charAt(0), val.charAt(5)), sortSubset(val.charAt(1), val.charAt(4)),
				sortSubset(val.charAt(2), val.charAt(3)) };
	}

	private static final String TRUE = "TRUE", FALSE = "FALSE";

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String cube;
		while ((cube = br.readLine()) != null) {
			char[][] cube1 = getSortedSubset(cube.substring(6));
			char[][] cube2 = getSortedSubset(cube.substring(0, 6));
			boolean result = false;
			for (int i = 0; i < 3 && !result; ++i) {
				for (int j = 0; j < 3 && !result; ++j) {
					if (i == j)
						continue;
					for (int k = 0; k < 3; ++k) {
						if (k == i || k == j)
							continue;
						result = setEquals(cube1[0], cube2[i]) && setEquals(cube1[1], cube2[j])
								&& setEquals(cube1[2], cube2[k]);
					}
				}
			}
			if (result) {
				pw.println(TRUE);
			} else {
				pw.println(FALSE);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}

}
