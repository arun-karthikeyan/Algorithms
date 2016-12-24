import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class UVA599 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {

			ArrayList<String> edgeInfo = new ArrayList<String>();
			String str;
			while ((str = br.readLine()).charAt(0) != '*') {
				edgeInfo.add(str);
			}
			String[] vertices = br.readLine().split(",");
			HashMap<Character, Integer> vertexMap = new HashMap<Character, Integer>();
			@SuppressWarnings("unchecked")
			ArrayList<Integer>[] adjList = new ArrayList[vertices.length];
			for (int i = 0, iLen = vertices.length; i < iLen; ++i) {
				vertexMap.put(vertices[i].charAt(0), i);
				adjList[i] = new ArrayList<Integer>();
			}
			for (int i = 0, iLen = edgeInfo.size(); i < iLen; ++i) {
				String currentEdge = edgeInfo.get(i);
				int from = vertexMap.get(currentEdge.charAt(1));
				int to = vertexMap.get(currentEdge.charAt(3));
				adjList[from].add(to);
				adjList[to].add(from);
			}
			int components = 0, acorns = 0;
			boolean[] visited = new boolean[vertices.length];
			for(int i=0, iLen = vertices.length; i<iLen; ++i){
				if(adjList[i].size()==0){
					acorns++;
					visited[i] = true;
				}else if(!visited[i]){
					components++;
					Stack<Integer> dfs = new Stack<Integer>();
					dfs.push(i);
					while(!dfs.isEmpty()){
						int currentVertex = dfs.pop();
						if(!visited[currentVertex]){
							visited[currentVertex] = true;
							for(int j=0, jLen = adjList[currentVertex].size(); j<jLen; ++j){
								dfs.push(adjList[currentVertex].get(j));
							}
						}
					}
				}
			}
			pw.println("There are "+components+" tree(s) and "+acorns+" acorn(s).");
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
