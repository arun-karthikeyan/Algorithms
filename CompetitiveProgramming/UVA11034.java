import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class UVA11034 {
	private static final int LEFT = 0;
	private static final HashMap<String, Integer> sideIdx;
	static{
		sideIdx = new HashMap<String, Integer>();
		sideIdx.put("left", 0);
		sideIdx.put("right", 1);
	}
@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	while (testcases-- > 0) {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int l = Integer.parseInt(st.nextToken())*100, m = Integer.parseInt(st.nextToken());
		Queue<Integer>[] sides = new LinkedList[2];
		sides[0] = new LinkedList<Integer>();
		sides[1] = new LinkedList<Integer>();
		for(int i=0; i<m; ++i){
			st = new StringTokenizer(br.readLine());
			int len = Integer.parseInt(st.nextToken());
			String side = st.nextToken();
			sides[sideIdx.get(side)].add(len);
		}
		int ferryLocation = LEFT, unloaded = 0, trips = 0;
		while(unloaded<m){
			int loadFactor = 0;
			while(!sides[ferryLocation].isEmpty() && ((loadFactor+sides[ferryLocation].peek())<=l)){
				loadFactor += sides[ferryLocation].peek();
				sides[ferryLocation].remove();
				unloaded++;
			}
			trips++;
			ferryLocation = (ferryLocation+1)%2;
		}
		pw.println(trips);
	}

	br.close();
	pw.flush();
	pw.close();
}
}
