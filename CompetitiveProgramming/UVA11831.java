import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Sticker Collector Robot, just plain graph traversal
 * 
 * @author arun
 *
 */
public class UVA11831 {
	private static final char STICKER = '*', PILLAR = '#', NORMAL = '.';
	private static final char NORTH = 'N', SOUTH = 'S', EAST = 'L', WEST = 'O';
	private static final int ROW = 0, COL = 1;
	private static final char RIGHT = 'D', LEFT = 'E';

	private static boolean startPos(char c) {
		return c != NORMAL && c != PILLAR && c != STICKER;
	}

	private static boolean canMove(char c) {
		return c != PILLAR;
	}

	private static int updatePosition(char[][] grid, int[] currentPosition, int currentDirection) {

		switch (currentDirection) {
		case NORTH:
			if (currentPosition[ROW] > 0 && canMove(grid[currentPosition[ROW] - 1][currentPosition[COL]])) {
				currentPosition[ROW]--;
			}
			break;
		case SOUTH:
			if (currentPosition[ROW] + 1 < grid.length
					&& canMove(grid[currentPosition[ROW] + 1][currentPosition[COL]])) {
				currentPosition[ROW]++;
			}
			break;
		case EAST:
			if (currentPosition[COL] + 1 < grid[currentPosition[ROW]].length
					&& canMove(grid[currentPosition[ROW]][currentPosition[COL] + 1])) {
				currentPosition[COL]++;
			}
			break;
		case WEST:
			if (currentPosition[COL] > 0 && canMove(grid[currentPosition[ROW]][currentPosition[COL] - 1])) {
				currentPosition[COL]--;
			}
		}
		if (grid[currentPosition[ROW]][currentPosition[COL]] == STICKER) {
			grid[currentPosition[ROW]][currentPosition[COL]] = NORMAL;
			return 1;
		}
		return 0;
	}

	private static int updateDirection(int currentDirection, int turn) {
		switch (currentDirection) {
		case NORTH:
			switch (turn) {
			case RIGHT:
				return EAST;
			case LEFT:
				return WEST;
			}
			break;
		case SOUTH:
			switch (turn) {
			case RIGHT:
				return WEST;
			case LEFT:
				return EAST;
			}
			break;
		case EAST:
			switch (turn) {
			case RIGHT:
				return SOUTH;
			case LEFT:
				return NORTH;
			}
			break;
		case WEST:
			switch (turn) {
			case RIGHT:
				return NORTH;
			case LEFT:
				return SOUTH;
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		while (true) {
			String[] info = br.readLine().split(" ");
			int n = Integer.parseInt(info[0]), m = Integer.parseInt(info[1]), q = Integer.parseInt(info[2]);
			if (n == 0 && m == 0 && q == 0) {
				break;
			}
			char[][] grid = new char[n][m];
			int currentPos[] = null, d = -1;
			for (int i = 0; i < n; ++i) {
				String row = br.readLine();
				for (int j = 0; j < m; ++j) {
					char c = row.charAt(j);
					grid[i][j] = c;
					if (startPos(c)) {
						currentPos = new int[] { i, j };
						d = c;
					}
				}
			}
			String ins = br.readLine();
			int stickers = 0;
			for (int i = 0; i < q; ++i) {
				char currentInstruction = ins.charAt(i);
				if (currentInstruction == 'F') {
					stickers += updatePosition(grid, currentPos, d);
				} else {
					d = updateDirection(d, currentInstruction);
				}
			}
			pw.println(stickers);

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
