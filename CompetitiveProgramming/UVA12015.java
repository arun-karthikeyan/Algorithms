

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UVA12015 {
	private static final int COUNT = 10;
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	ArrayList<String> urls;
	for(int t=1;t<=testcases;++t){
		StringTokenizer st = new StringTokenizer(br.readLine());
		String url = st.nextToken();
		int maxRelevance = Integer.parseInt(st.nextToken());
		urls = new ArrayList<String>(10);
		urls.add(url);
		for(int i=1;i<COUNT;++i){
			st = new StringTokenizer(br.readLine());
			url = st.nextToken();
			int currentRelevance = Integer.parseInt(st.nextToken());
			if(currentRelevance>maxRelevance){
				maxRelevance = currentRelevance;
				urls = new ArrayList<String>(10);
				urls.add(url);
			}
			else
				if(currentRelevance==maxRelevance)
				{
					urls.add(url);
				}
		}
		pw.println("Case #"+t+":");
		for(int i=0,j=urls.size();i<j;++i)
		{
			pw.println(urls.get(i));
		}
	}
	pw.flush();
	pw.close();
	br.close();
}
}
