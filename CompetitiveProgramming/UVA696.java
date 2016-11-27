import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA696 {
public static void main(String[] args) throws Exception{
//	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	
	StringTokenizer st = new StringTokenizer(br.readLine());
	int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
	while(n!=0 || m!=0){
		if(n==1 || m==1){
			pw.println((n*m)+" knights may be placed on a "+n+" row "+m+" column board.");
		}else if((n==2) || (m==2)){
			int greater = n>m?n:m;
			int knights = (greater/4)*4+(((greater%4)>=2)?4:((greater%4==1)?2:0));
			pw.println(knights+" knights may be placed on a "+n+" row "+m+" column board.");
		}else{
//			int knights = (m*(n/2))+((n%2)*((m+(m%2))/2));
			int knights = (((m+1)/2)*((n+1)/2))+((m/2)*(n/2));
			pw.println((knights)+" knights may be placed on a "+n+" row "+m+" column board.");
		}
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
	}
	
	pw.flush();
	pw.close();
	br.close();
}
}
