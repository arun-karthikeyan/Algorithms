import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10443 {
	private static char[][] grid;
	private static int r, c;

	private static boolean wins(char a, char b) {
		return ((a == 'R' && b == 'P') || (a == 'P' && b == 'S') || (a == 'S' && b == 'R'));
	}

	private static char getWinner(int i, int j, int i1, int j1, int i2, int j2, int i3, int j3, int i4, int j4) {
		char origChar = grid[i][j];
		if (inBounds(i1, j1) && wins(origChar, grid[i1][j1])) {
			return grid[i1][j1];
		} else if (inBounds(i2, j2) && wins(origChar, grid[i2][j2])) {
			return grid[i2][j2];
		} else if (inBounds(i3, j3) && wins(origChar, grid[i3][j3])) {
			return grid[i3][j3];
		} else if (inBounds(i4, j4) && wins(origChar, grid[i4][j4])) {
			return grid[i4][j4];
		}
		return origChar;
	}

	private static boolean inBounds(int i, int j) {
		return i >= 0 && i < r && j >= 0 && j < c;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			int n = Integer.parseInt(st.nextToken());
			grid = new char[r][c];
			for (int i = 0; i < r; ++i) {
				String row = br.readLine();
				for (int j = 0; j < c; ++j) {
					grid[i][j] = row.charAt(j);
				}
			}

			while (n-- > 0) {
				char[][] temp = new char[r][c];
				for (int i = 0; i < r; ++i) {
					for (int j = 0; j < c; ++j) {
						temp[i][j] = getWinner(i,j,i+1,j,i-1,j,i,j+1,i,j-1);
					}
				}
				grid = temp;
			}
			
			for(int i=0; i<r; ++i){
				for(int j=0; j<c; ++j){
					pw.print(grid[i][j]);
				}
				pw.println();
			}
			if(testcases!=0){
				pw.println();
			}
		}
		br.close();
		pw.flush();
		pw.close();
	}
}
