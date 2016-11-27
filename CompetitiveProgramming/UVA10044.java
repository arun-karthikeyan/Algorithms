import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class UVA10044 {
	private static final String SCENARIO = "Scenario ";
	private static final String INFINITY = "infinity";
	private static final String ERDOS = "Erdos, P.";
	private static final int NAME = 0, DISTANCE = 1, INF = Integer.MAX_VALUE;

	private static ArrayList<String> nameEduthuKuduBa(String nameString) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0, comma = 0, iLen = nameString.length(); i < iLen && nameString.charAt(i)!=':'; ++i) {
			char currentChar = nameString.charAt(i);
			if (currentChar == ',') {
				comma++;
			}
			if (comma == 2) {
				++i;
				if (sb.length() > 0) {
					names.add(sb.toString());
				}
				comma = 0;
				sb = new StringBuilder();
			} else {
				sb.append(currentChar);
			}
		}
		if (sb.length() > 0) {
			names.add(sb.toString());
		}
		return names;

	}

	public static void main(String[] args) throws Exception {
//		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = 0, scenario = 0;
		testcases = Integer.parseInt(br.readLine());

		while (testcases-- > 0) {
			pw.println(SCENARIO + (++scenario));
			StringTokenizer st = new StringTokenizer(br.readLine());
			int p = Integer.parseInt(st.nextToken()), n = Integer.parseInt(st.nextToken());
			String[] publications = new String[p];

			for (int i = 0; i < p; ++i) {
				publications[i] = br.readLine();
			}
			String[] names = new String[n];
			int uniqueNames = 0;
			ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
			HashMap<String, Integer> namesToVertices = new HashMap<String, Integer>(n);
			for (int i = 0; i < n; ++i) {
				names[i] = br.readLine();
				if (!namesToVertices.containsKey(names[i])) {
					namesToVertices.put(names[i], uniqueNames++);
					adjList.add(new ArrayList<Integer>());
				}
			}
			// construct adjacency list
			for (int i = 0; i < p; ++i) {
				ArrayList<String> adjacentVertices = nameEduthuKuduBa(publications[i]);
				for (int j = 0, jLen = adjacentVertices.size(); j < jLen; ++j) {
					String name1 = adjacentVertices.get(j);
					if (!namesToVertices.containsKey(name1)) {
						namesToVertices.put(name1, namesToVertices.size());
						adjList.add(new ArrayList<Integer>());
					}
					int jNameIdx = namesToVertices.get(name1);
					for (int k = 0, kLen = adjacentVertices.size(); k < kLen; ++k) {
						String name2 = adjacentVertices.get(k);
						if (!namesToVertices.containsKey(name2)) {
							namesToVertices.put(name2, namesToVertices.size());
							adjList.add(new ArrayList<Integer>());
						}
						int kNameIdx = namesToVertices.get(name2);
						if (jNameIdx != kNameIdx) {
							adjList.get(jNameIdx).add(kNameIdx);
						}
					}
				}

			}

			// breast first search
			if (namesToVertices.containsKey(ERDOS)) {
				// VARLA VARLA VA
				LinkedList<int[]> kyu = new LinkedList<int[]>();
				kyu.add(new int[] { namesToVertices.get(ERDOS), 0 });
				int[] distances = new int[uniqueNames];
				Arrays.fill(distances, INF);
				boolean[] visitedList = new boolean[namesToVertices.size()];
				while (!kyu.isEmpty()) {
					int[] currentGuy = kyu.remove();
					if (!visitedList[currentGuy[NAME]]) {
						visitedList[currentGuy[NAME]] = true;
						if (currentGuy[NAME] < uniqueNames) {
							distances[currentGuy[NAME]] = currentGuy[DISTANCE];
						}
						for (int j = 0, jLen = adjList.get(currentGuy[NAME]).size(); j < jLen; ++j) {
							kyu.add(new int[] { adjList.get(currentGuy[NAME]).get(j), currentGuy[DISTANCE] + 1 });
						}
					}
				}
				for (int j = 0; j < n; ++j) {
					if (distances[namesToVertices.get(names[j])] != INF) {
						pw.println(names[j] + " " + distances[namesToVertices.get(names[j])]);
					} else {
						pw.println(names[j] + " " + INFINITY);
					}
				}
			} else {
				// ERDOS illa ba
				for (int j = 0, jLen = names.length; j < jLen; ++j) {
					pw.println(names[j] + " " + INFINITY);
				}
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
