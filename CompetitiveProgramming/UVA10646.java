import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10646 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	for(int t=1;t<=testcases;++t){
		pw.println("Case "+t+": "+(br.readLine().trim().substring(96, 98)));
	}

	pw.flush();
	pw.close();
	br.close();
}
}
