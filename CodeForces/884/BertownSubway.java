import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BertownSubway {
	
	private static int componentSize(boolean[] visited, int[] paths, int node) {
		if(visited[node]) {
			return 0;
		}
		else {
			visited[node] = true;
			return 1+componentSize(visited, paths, paths[node]);
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		
		
		int n = Integer.parseInt(br.readLine().trim());
		String[] info = br.readLine().trim().split(" ");
		int[] paths = new int[n];
		for(int i=0; i<n; ++i) {
			paths[i] = Integer.parseInt(info[i].trim())-1;
		}
		ArrayList<Integer> componentSizes = new ArrayList<Integer>();
		boolean[] visited = new boolean[n];
		for(int i=0; i<n; ++i) {
			if(!visited[i]) {
				componentSizes.add(componentSize(visited, paths, i));
			}
		}
		long orderedPairs = 0;
		if(componentSizes.size()>1) {
			Collections.sort(componentSizes,Comparator.reverseOrder());
			orderedPairs = componentSizes.get(0)+componentSizes.get(1);
			orderedPairs*=orderedPairs;
			for(int i=2, iLen = componentSizes.size(); i<iLen; ++i) {
				long currentCount = componentSizes.get(i);
				orderedPairs = orderedPairs + (currentCount*currentCount);
			}
			
		}else {
			orderedPairs = componentSizes.get(0);
			orderedPairs*=orderedPairs;
		}
		pw.println(orderedPairs);
		br.close();
		pw.flush();
		pw.close();
	}
}
