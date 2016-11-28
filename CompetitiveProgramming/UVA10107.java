import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.PriorityQueue;

public class UVA10107 {

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String no;
		PriorityQueue<Integer> q1 = new PriorityQueue<Integer>(Collections.reverseOrder());
		PriorityQueue<Integer> q2 = new PriorityQueue<Integer>();
		int currentNo;
		if ((no = br.readLine()) != null) {
			currentNo = Integer.parseInt(no.trim());
			q1.add(currentNo);
			pw.println(currentNo);
		}
		if ((no = br.readLine()) != null) {
			currentNo = Integer.parseInt(no.trim());
			if (currentNo < q1.peek()) {
				int temp = q1.remove();
				q2.add(temp);
				q1.add(currentNo);
			} else {
				q2.add(currentNo);
			}
			int n1 = q1.peek(), n2 = q2.peek();
			pw.println(((n1/2+n2/2)+(((n1%2)+(n2%2))/2)));
		}

		while ((no = br.readLine()) != null) {
			currentNo = Integer.parseInt(no.trim());
			// make sure q1 always has the extra
			if (currentNo < q1.peek()) {
				// currentNo has to go to q1
				if (q1.size() > q2.size()) {
					// have to move one element from q1 to q2 and then insert
					// currentNo in q1
					int temp = q1.remove();
					q2.add(temp);
					q1.add(currentNo);
				} else {
					// directly add to q1
					q1.add(currentNo);
				}
			} else {
				// currentNo has to go to q2
				if (q1.size() == q2.size()) {
					// have to move one element from q2 to q1 and then insert
					// currentNo in q2
					if (currentNo > q2.peek()) {
						int temp = q2.remove();
						q1.add(temp);
						q2.add(currentNo);
					} else {
						q1.add(currentNo);
					}
				} else {
					// directly add to q2
					q2.add(currentNo);
				}
			}

			if (q1.size() > q2.size()) {
				pw.println(q1.peek());
			} else {
				int n1 = q1.peek(), n2 = q2.peek();
				pw.println(((n1/2+n2/2)+(((n1%2)+(n2%2))/2)));
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}

}
