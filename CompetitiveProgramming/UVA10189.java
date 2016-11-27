import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10189 {
	private static boolean inBounds(int n, int m, int i, int j){
		return i>=0 && i<n && j>=0 && j<m;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	StringTokenizer st = new StringTokenizer(br.readLine());
	int n = Integer.parseInt(st.nextToken());
	int m = Integer.parseInt(st.nextToken());
	int field = 1;
	while (n!=0 || m!=0) {
		if(field!=1){
			pw.println();
		}
		char[][] mines = new char[n][m];
		for(int i=0; i<n; ++i){
			mines[i] = br.readLine().toCharArray();
		}
		for(int i=0; i<n; ++i){
			for(int j=0; j<m; ++j){
				if(mines[i][j]=='*'){
					if(inBounds(n, m, i-1, j-1) && mines[i-1][j-1]!='*'){
						if(mines[i-1][j-1]=='.'){
							mines[i-1][j-1]='1';
						}else{
							mines[i-1][j-1]++;
						}
					}
					if(inBounds(n, m, i-1, j) && mines[i-1][j]!='*'){
						if(mines[i-1][j]=='.'){
							mines[i-1][j]='1';
						}else{
							mines[i-1][j]++;
						}
					}
					if(inBounds(n, m, i-1, j+1) && mines[i-1][j+1]!='*'){
						if(mines[i-1][j+1]=='.'){
							mines[i-1][j+1]='1';
						}else{
							mines[i-1][j+1]++;
						}
					}
					if(inBounds(n, m, i, j-1) && mines[i][j-1]!='*'){
						if(mines[i][j-1]=='.'){
							mines[i][j-1]='1';
						}else{
							mines[i][j-1]++;
						}
					}
					if(inBounds(n, m, i, j+1) && mines[i][j+1]!='*'){
						if(mines[i][j+1]=='.'){
							mines[i][j+1]='1';
						}else{
							mines[i][j+1]++;
						}
					}
					if(inBounds(n, m, i+1, j-1) && mines[i+1][j-1]!='*'){
						if(mines[i+1][j-1]=='.'){
							mines[i+1][j-1]='1';
						}else{
							mines[i+1][j-1]++;
						}
					}
					if(inBounds(n, m, i+1, j) && mines[i+1][j]!='*'){
						if(mines[i+1][j]=='.'){
							mines[i+1][j]='1';
						}else{
							mines[i+1][j]++;
						}
					}
					if(inBounds(n, m, i+1, j+1) && mines[i+1][j+1]!='*'){
						if(mines[i+1][j+1]=='.'){
							mines[i+1][j+1]='1';
						}else{
							mines[i+1][j+1]++;
						}
					}
				}
			}
		}
		pw.println("Field #"+field+":");
		for(int i=0;i<n; ++i){
			for(int j=0;j<m;++j){
				if(mines[i][j]!='.')
				{
					pw.print(mines[i][j]);
				}
				else{
					pw.print('0');
				}
			}
			pw.println();
		}
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		field++;
	}

	br.close();
	pw.flush();
	pw.close();
}
}
