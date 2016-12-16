import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class UVA11995 {
	private static final int INSERT = 1, REMOVE = 2;
	private static final String IMPOSSIBLE = "impossible";
	private static final String PQ = "priority queue";
	private static final String NOTSURE = "not sure";
	private static final String STACK = "stack";
	private static final String Q = "queue";

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String testcases;
		while ((testcases = br.readLine()) != null) {
			int tcs = 0;
			try {
				tcs = Integer.parseInt(testcases.trim());
			} catch (Exception e) {

			}
			boolean isStack = true, isQueue = true, isPriorityQueue = true;
			Stack<Integer> s = new Stack<Integer>();
			Queue<Integer> q = new LinkedList<Integer>();
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new java.util.Comparator<Integer>() {

				@Override
				public int compare(Integer arg0, Integer arg1) {
					// TODO Auto-generated method stub
					return arg1 - arg0;
				}

			});

			while (tcs-- > 0) {
				int command = 0, value = 0;
				try {
					String[] ip = br.readLine().trim().split(" ");
					command = Integer.parseInt(ip[0].trim());
					value = Integer.parseInt(ip[1].trim());
				} catch (Exception e) {

				}
				if (command == INSERT) {
					if (isStack) {
						s.push(value);
					}
					if (isQueue) {
						q.add(value);
					}
					if (isPriorityQueue) {
						pq.add(value);
					}
				} else if (command == REMOVE) {
					if (isStack) {
						if (!s.isEmpty()) {
							int sValue = s.pop();
							isStack = sValue == value;
						} else {
							isStack = false;
						}
					}
					if (isQueue) {
						if (!q.isEmpty()) {
							int qValue = q.remove();
							isQueue = qValue == value;
						} else {
							isQueue = false;
						}
					}
					if (isPriorityQueue) {
						if (!pq.isEmpty()) {
							int pqValue = pq.remove();
							isPriorityQueue = pqValue == value;
						} else {
							isPriorityQueue = false;
						}
					}

				}
			}
			if ((isStack && isQueue) || (isStack && isPriorityQueue) || (isQueue && isPriorityQueue)) {
				pw.println(NOTSURE);
			} else if (isStack) {
				pw.println(STACK);
			} else if (isQueue) {
				pw.println(Q);
			} else if (isPriorityQueue) {
				pw.println(PQ);
			} else {
				pw.println(IMPOSSIBLE);
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
