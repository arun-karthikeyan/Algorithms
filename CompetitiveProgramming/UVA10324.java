import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10324 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String line = "";
	int testcase = 0;
	while((line=br.readLine())!=null)
	{
		int len = line.length();
		int[] cum = new int[len+1];
		cum[0] = 0;
		for(int i=1;i<=len;++i){
			cum[i] = cum[i-1]+(line.charAt(i-1)-'0');
		}
		int queries = Integer.parseInt(br.readLine());
		pw.println("Case "+(++testcase)+":");
		for(int i=0;i<queries;++i)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken())+1, b = Integer.parseInt(st.nextToken())+1;
			if(a>b)
			{
				int temp = a;
				a = b;
				b = temp;
			}
			int sum = cum[b]-cum[a-1];
			if(sum==0 || sum==(b-a+1))
			{
				pw.println("Yes");
			}
			else
			{
				pw.println("No");
			}
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
