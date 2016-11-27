import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UVA11581 {
	private static int getAdjacentSumMod2(int[][] g, int i, int j){
		int total = 0;
		if((i-1)>=0){
			total+=g[i-1][j];
		}
		if((j-1)>=0){
			total+=g[i][j-1];
		}
		if((i+1)<3){
			total+=g[i+1][j];
		}
		if((j+1)<3){
			total+=g[i][j+1];
		}
		
		return total%2;
	}
	private static int[][] getNextFg(int[][] fg){
		int[][] nextFg = new int[3][3];
		for(int i=0; i<3; ++i){
			for(int j=0; j<3; ++j){
				nextFg[i][j] = getAdjacentSumMod2(fg, i, j);
			}
		}
		return nextFg;
	}
	
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	while (testcases-- > 0) {
		br.readLine();
		int[][] g = new int[3][3];
		for(int i=0; i<3; ++i){
			String currentRow = br.readLine().trim();
			for(int j=0; j<3; ++j){
				g[i][j] = currentRow.charAt(j)-'0';
			}
		}
		int maxIdx = -1;
		while(true){
			int[][] nextFg = getNextFg(g);
			if(Arrays.deepEquals(g, nextFg)){
				break;
			}
			maxIdx++;
			g = nextFg;
		}
		pw.println(maxIdx);
	}

	br.close();
	pw.flush();
	pw.close();
}
}
