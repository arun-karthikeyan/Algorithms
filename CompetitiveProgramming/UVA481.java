import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class UVA481 {
	private static int binarySearchInsertPos(ArrayList<Integer> array, int key) {
		int low = 0, high = array.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1, val = array.get(mid);
			if (key < val) {
				high = mid - 1;
			} else if (key > val) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	private static int VALUE = 0, IDX = 1;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		ArrayList<Integer> array = new ArrayList<Integer>();
		ArrayList<Integer> lis = new ArrayList<Integer>();
		ArrayList<ArrayList<int[]>> track = new ArrayList<ArrayList<int[]>>();
		String line;
		while ((line = br.readLine()) != null) {
			array.add(Integer.parseInt(line));
		}
		for (int i = 0, iLen = array.size(); i < iLen; ++i) {
			int val = array.get(i);
			int insertPos = binarySearchInsertPos(lis, val);
			if (insertPos == lis.size()) {
				lis.add(val);
				ArrayList<int[]> traceMap = new ArrayList<int[]>();
				traceMap.add(new int[] { val, i });
				track.add(traceMap);
			} else {
				lis.set(insertPos, val);
				track.get(insertPos).add(new int[] { val, i });
			}
		}
		int lisSize = track.size(), previousIdx = array.size();
		pw.println(lisSize + "\n-");
		Stack<Integer> lisStack = new Stack<Integer>();
		for (int i = lisSize - 1; i >= 0; --i) {
			ArrayList<int[]> currentList = track.get(i);
			for (int j = currentList.size() - 1; j >= 0; --j) {
				int[] currentElement = currentList.get(j);
				if (currentElement[IDX] < previousIdx) {
					lisStack.add(currentElement[VALUE]);
					previousIdx = currentElement[IDX];
					break;
				}
			}
		}
		while (!lisStack.isEmpty()) {
			pw.println(lisStack.pop());
		}
		br.close();
		pw.flush();
		pw.close();
	}
}
