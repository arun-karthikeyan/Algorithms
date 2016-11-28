import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UVA10004 {
private static int totalchars = 0, offset = 0;
private static InputStream stream;
private static byte[] buffer = new byte[1024];

private static int readByte() {
	if (totalchars < 0)
		return 0;
	if (offset >= totalchars) {
		offset = 0;
		try {
			totalchars = stream.read(buffer);
		} catch (IOException e) {
			return 0;
		}
		if (totalchars <= 0)
			return -1;
	}
	return buffer[offset++];
}

private static int readInt() {
	int number = readByte();

	while (eolchar(number))
		number = readByte();

	int sign = 1;
	int val = 0;

	if (number == '-') {
		sign = -1;
		number = readByte();
	}

	do {
		if ((number < '0') || (number > '9'))
			return 0;
		val *= 10;
		val += (number - '0');
		number = readByte();
	} while (!eolchar(number));

	return sign * val;
}

private static long readLong() {
	int number = readByte();

	while (eolchar(number))
		number = readByte();

	int sign = 1;
	long val = 0;

	if (number == '-') {
		sign = -1;
		number = readByte();
	}

	do {
		if ((number < '0') || (number > '9')) {
			//return sign*val;
			return 0;
		}
		val *= 10;
		val += (number - '0');
		number = readByte();
	} while (!eolchar(number));

	return sign * val;
}

private static boolean eolchar(int c) {
	return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
}
private static final String YES = "BICOLORABLE.";
private static final String NO = "NOT BICOLORABLE.";
private static final int C1=0, C2=1;
public static void main(String[] args) throws Exception {
	if (args.length > 0 && "fileip".equals(args[0])) {
		stream = new FileInputStream(new File("testip.txt"));
	} else {
		stream = System.in;
	}

	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int n;

	while ((n=readInt())!=0) {
		int edges = readInt();
		ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>(n);
		for(int i=0; i<n; ++i){
			adjList.add(new ArrayList<Integer>());
		}
		for(int i=0; i<edges; ++i){
			int from = readInt(), to = readInt();
			adjList.get(from).add(to);
			adjList.get(to).add(from);
		}
		HashMap<Integer, Integer> nodeVsColor = new HashMap<Integer, Integer>();
		boolean[] visited = new boolean[n];
		LinkedList<Integer> bfs = new LinkedList<Integer>();
		bfs.add(0);
		nodeVsColor.put(0, C1);
		boolean possible = true;
		while(!bfs.isEmpty() && possible){
			int currentNode = bfs.remove();
			int currentColor = nodeVsColor.get(currentNode);
			int adjColor = (currentColor+1)%2;
			if(!visited[currentNode]){
				visited[currentNode] = true;
				for(int i=0, iLen = adjList.get(currentNode).size(); i<iLen; ++i){
					int currentAdj = adjList.get(currentNode).get(i);
					if(nodeVsColor.containsKey(currentAdj)){
						int currentAdjColor = nodeVsColor.get(currentAdj);
						if(currentAdjColor!=adjColor){
							possible = false;
							break;
						}
					}else{
						nodeVsColor.put(currentAdj, adjColor);
					}
					bfs.add(currentAdj);
				}
			}
		}
		if(possible){
			pw.println(YES);
		}else{
			pw.println(NO);
		}
		
	}

	pw.flush();
	pw.close();
}
}
