import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA556 {
	//if out of bounds, try right, if right not possible take left
	private static boolean[][] maze;
	private static int r, c;
	private static int[][] visitedSquares;
	private static final int UP=0, RIGHT=1,LEFT=2, DOWN=3;
	private static boolean inBounds(int m, int n){
		return m>=0 && m<r && n>=0 && n<c && maze[m][n];
	}
	
	private static boolean exitCondition(int m, int n){
		 return m==(r-1) && n==0 && visitedSquares[m][n]!=0;
	}
	
	private static void visitSquares(int m, int n, int direction){
		if(exitCondition(m, n)){
			return;
		}
		visitedSquares[m][n]++;
		switch(direction){
		case UP:
			if(inBounds(m,n+1)){
				visitSquares(m,n+1,RIGHT);
			}else if(inBounds(m-1,n)){
				visitSquares(m-1,n,UP);
			}else if(inBounds(m,n-1)){
				visitSquares(m,n-1,LEFT);
			}else{
				visitSquares(m+1,n,DOWN);
			}
			break;
		case DOWN:
			if(inBounds(m,n-1)){
				visitSquares(m,n-1,LEFT);
			}else if(inBounds(m+1,n)){
				visitSquares(m+1,n,DOWN);
			}else if(inBounds(m,n+1)){
				visitSquares(m,n+1,RIGHT);
			}else{
				visitSquares(m-1,n,UP);
			}
			break;
		case LEFT:
			if(inBounds(m-1,n)){
				visitSquares(m-1,n,UP);
			}else if(inBounds(m,n-1)){
				visitSquares(m,n-1,LEFT);
			}else if(inBounds(m+1,n)){
				visitSquares(m+1,n,DOWN);
			}else{
				visitSquares(m,n+1,RIGHT);
			}
			break;
		case RIGHT:
			if(inBounds(m+1,n)){
				visitSquares(m+1,n,DOWN);
			}else if(inBounds(m,n+1)){
				visitSquares(m,n+1,RIGHT);
			}else if(inBounds(m-1,n)){
				visitSquares(m-1,n,UP);
			}else{
				visitSquares(m,n-1,LEFT);
			}
			break;
		}
	}
	private static int[] getCount(){
		int[] counts = new int[5];
		for(int i=0;i<r;++i){
			for(int j=0;j<c;++j){
				if(!maze[i][j]){
					continue;
				}
				switch(visitedSquares[i][j]){
				case 0:
					counts[0]++;
					break;
				case 1:
					counts[1]++;
					break;
				case 2:
					counts[2]++;
					break;
				case 3:
					counts[3]++;
					break;
				case 4:
					counts[4]++;
					break;
				}
			}
		}
		return counts;
	}
	//define functions goUp goDown goLeft goRight
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	StringTokenizer st = new StringTokenizer(br.readLine());
	r = Integer.parseInt(st.nextToken());
	c = Integer.parseInt(st.nextToken());
	while(r!=0 || c!=0){
		visitedSquares = new int[r][c];
		maze = new boolean[r][c];
		
		for(int i=0; i<r; ++i){
			String row = br.readLine();
			for(int j=0; j<c; ++j){
				maze[i][j] = row.charAt(j)=='0';
			}
		}
		visitSquares(r-1, 0, RIGHT);
		int[] counts = getCount();
		pw.printf("%3d%3d%3d%3d%3d\n", counts[0],counts[1],counts[2],counts[3],counts[4]);
		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
	}
	br.close();
	pw.flush();
	pw.close();
}
}
