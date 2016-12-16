import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HelperMethod {
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

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out), true);
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.reverseOrder());
		int N = (int)1e6;
		int MAX = N*100;
		int elements[] = new int[N];
		for(int i=0; i<N; ++i){
//			elements[i] = i;
			elements[i] = (int)(Math.random()*MAX);
		}
		long start = System.currentTimeMillis();
		for(int i=0; i<N; ++i){
			pq.add(elements[i]);
		}
		long end = System.currentTimeMillis();
//		System.out.println("Heap : "+minHeap);
		System.out.println("Heap size : "+pq.size());
		System.out.println("Total time to add : "+((end-start)/1000d));
		start = System.currentTimeMillis();
		while(!pq.isEmpty()){
//			System.out.print(minHeap.remove()+" ");
			pq.remove();
		}
		end = System.currentTimeMillis();
		System.out.println("\nTotal time to remove : "+((end-start)/1000d));


		pw.flush();
		pw.close();
	}
}
