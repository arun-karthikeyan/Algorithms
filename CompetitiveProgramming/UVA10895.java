import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UVA10895 {
	private static final int ID = 0, VAL = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String dimensions;
		while ((dimensions = br.readLine()) != null) {
			String[] d = dimensions.split(" ");
			int n = Integer.parseInt(d[0]), m = Integer.parseInt(d[1]);
			@SuppressWarnings("unchecked")
			ArrayList<int[]>[] adjList = new ArrayList[m];
			for (int i = 0; i < m; ++i) {
				adjList[i] = new ArrayList<int[]>();
			}
			for (int i = 0; i < n; ++i) {
				String[] idx = br.readLine().split(" ");
				String[] val = br.readLine().split(" ");
				int len = Integer.parseInt(idx[0]);
				for (int j = 0; j < len; ++j) {
					adjList[Integer.parseInt(idx[j+1])-1].add(new int[]{i+1,Integer.parseInt(val[j])});
				}
			}
			pw.println(m+" "+n);
			for(int i=0, iLen = adjList.length; i<iLen; ++i){
				int size = adjList[i].size();
				pw.print(size);
				for(int j=0; j<size; ++j){
					pw.print(" "+adjList[i].get(j)[ID]);
				}
				pw.println();
				if(size>0){
					pw.print(adjList[i].get(0)[VAL]);
				}
				for(int j=1; j<size; ++j){
					pw.print(" "+adjList[i].get(j)[VAL]);
				}
				pw.println();
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
