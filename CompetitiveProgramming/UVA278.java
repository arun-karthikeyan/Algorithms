import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA278 {
	private static int countKnights(int n, int m){
		int knights = 0;
		if(n==1 || m==1){
			knights = n*m;
		}else if((n==2) || (m==2)){
			int greater = n>m?n:m;
			knights = (greater/4)*4+(((greater%4)>=2)?4:((greater%4==1)?2:0));
		}else{
			knights = (m*(n/2))+((n%2)*((m+(m%2))/2));
		}
		return knights;
	}
	private static int countRooks(int n, int m){
		return Math.min(n, m);
	}
	
	private static int countKings(int n, int m){
		int kings = ((n/2)+(n%2))*((m/2)+(m%2));
		return kings;
	}
	private static int countQueens(int n, int m){
		if(n==2 && m==2){
			return 1;
		}else{
			return Math.min(n, m);
		}
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		
		int testcases = Integer.parseInt(br.readLine());
		while(testcases-->0){
			StringTokenizer st = new StringTokenizer(br.readLine());
			int ch = st.nextToken().charAt(0);
			int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
			switch(ch){
			case 'r':
				pw.println(countRooks(n, m));
				break;
			case 'k':
				pw.println(countKnights(n,m));
				break;
			case 'K':
				pw.println(countKings(n, m));
				break;
			case 'Q':
				pw.println(countQueens(n, m));
				break;
			}
		}
		
		br.close();
		pw.flush();
		pw.close();
		
	}
}
