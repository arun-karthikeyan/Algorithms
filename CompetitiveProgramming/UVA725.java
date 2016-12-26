import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA725 {
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

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
//		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("testop.txt"))));

		int n, start = 0;
		long startTime = System.currentTimeMillis();
		while ((n = readInt()) != 0) {
			if((start++)!=0){
				pw.println();
			}
			boolean noSolution = true;
			for(int fghij=1234; fghij<=(98765/n); fghij++){
				int abcde = fghij*n;
				int used = fghij<10000?1:0;
				int temp = abcde;
				while(temp>0){
					used|=(1<<(temp%10));
					temp/=10;
				}
				temp = fghij;
				while(temp>0){
					used|=(1<<(temp%10));
					temp/=10;
				}
				if(used==((1<<10)-1)){
					pw.printf("%05d / %05d = %d\n", abcde,fghij,n);
					noSolution = false;
				}
			}
			if(noSolution){
				pw.println("There are no solutions for "+n+".");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Time : "+((endTime-startTime)/1000d)+" seconds");

		pw.flush();
		pw.close();
	}
}
