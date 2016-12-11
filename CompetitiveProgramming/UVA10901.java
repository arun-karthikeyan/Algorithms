import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class UVA10901 {
	private static final int LEFT = 0;
	private static final HashMap<String, Integer> sideIdx;
	static{
		sideIdx = new HashMap<String, Integer>();
		sideIdx.put("left", 0);
		sideIdx.put("right", 1);
	}
	private static final int TIME = 0, ORDER = 1;
	private static final int[] getDropTime(int[] result, int timeElapsed, int t){
		result[TIME] = timeElapsed + t;
		return result;
	}
	private static final int updateResult(int unloaded, Queue<int[]> ferrySide, int n, int timeElapsed, int[][] result, int t){
		int ferryLoad = 0;
		while(ferryLoad<n && !ferrySide.isEmpty() &&  ferrySide.peek()[TIME]<=timeElapsed){
		result[unloaded++] = getDropTime(ferrySide.remove(), timeElapsed, t);
		ferryLoad++;
	}
	return unloaded;
	}
@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception {
//	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	
	while (testcases-- > 0) {
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken()), t = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
		Queue<int[]>[] sides = new LinkedList[2];
		sides[0] = new LinkedList<int[]>();
		sides[1] = new LinkedList<int[]>();
		int[][] result = new int[m][2];
		for(int i=0; i<m; ++i){
			st = new StringTokenizer(br.readLine());
			int time = Integer.parseInt(st.nextToken());
			String side = st.nextToken();
			sides[sideIdx.get(side)].add(new int[]{time,i});
		}
		
		int ferryLocation = LEFT, timeElapsed = 0, unloaded = 0;
		
		while(unloaded<m){
			int oppLocation = (ferryLocation+1)%2;
			
				if(!sides[ferryLocation].isEmpty() && sides[ferryLocation].peek()[TIME]<=timeElapsed){
					unloaded = updateResult(unloaded, sides[ferryLocation], n, timeElapsed, result, t);
					ferryLocation = oppLocation;
				}else if(!sides[oppLocation].isEmpty() && sides[oppLocation].peek()[TIME]<=timeElapsed){
					timeElapsed+=t;
					unloaded = updateResult(unloaded, sides[oppLocation], n, timeElapsed, result, t);
				}else if(sides[oppLocation].isEmpty() || (!sides[ferryLocation].isEmpty() && sides[ferryLocation].peek()[TIME]<=sides[oppLocation].peek()[TIME])){
					timeElapsed = sides[ferryLocation].peek()[TIME];
					unloaded = updateResult(unloaded, sides[ferryLocation], n, timeElapsed, result, t);
					ferryLocation = oppLocation;
				}else{
					timeElapsed = sides[oppLocation].peek()[TIME] + t;
					unloaded = updateResult(unloaded, sides[oppLocation], n, timeElapsed, result, t);
				}
				timeElapsed+=t;
		}
		
		Arrays.sort(result, new java.util.Comparator<int[]>(){
			@Override
			public int compare(int[] arg0, int[] arg1) {
				return arg0[ORDER]-arg1[ORDER];
			}
		});
		for(int i=0; i<m; ++i){
			pw.println(result[i][TIME]);
		}
		if(testcases!=0){
			pw.println();
		}
	}

	br.close();
	pw.flush();
	pw.close();
}
}
