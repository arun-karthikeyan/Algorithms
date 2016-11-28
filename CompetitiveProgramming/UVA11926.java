import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;

public class UVA11926 {
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
				// return sign*val;
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

	private static final String CONFLICT = "CONFLICT", NOCONFLICT = "NO CONFLICT";
	private static final int TIME = (int) 1e6;
	private static int min(int a, int b){
		return a<b?a:b;
	}
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n=readInt(), m=readInt();

		while (n!=0 || m!=0) {
			BitSet schedule = new BitSet();
			boolean status = true;
			for (int i = 0; i < n; ++i) {
				int from = readInt(), to = readInt();
				if (status) {
					BitSet currentEvent = new BitSet();
					currentEvent.set(from,to);
					if (schedule.intersects(currentEvent)) {
						status = false;
					} else {
						schedule.set(from, to);
					}
				}
			}
			for(int i=0; i<m; ++i){
				int from = readInt(), to=readInt(), interval = readInt();
				if(interval<(to-from)){
					status = false;
				}
				if(status){
					BitSet currentEvent = new BitSet();
					for(int j=from; j<=TIME; j+=interval){
						currentEvent.set(j, min(j+(to-from),TIME+1));
					}
					if(schedule.intersects(currentEvent)){
						status = false;
					}else{
						schedule.or(currentEvent);
					}
				}
			}
			if(status){
				pw.println(NOCONFLICT);
			}else{
				pw.println(CONFLICT);
			}
			n = readInt(); m = readInt();
		}

		pw.flush();
		pw.close();
	}
}
