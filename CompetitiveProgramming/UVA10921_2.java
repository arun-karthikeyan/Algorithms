import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10921_2 {
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
private static final String LINE = "Line", COLUMN = "column";

public static void main(String[] args) throws Exception {
	if (args.length > 0 && "fileip".equals(args[0])) {
		stream = new FileInputStream(new File("testip.txt"));
	} else {
		stream = System.in;
	}

	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int size;
	long p;

	while ((size = readInt()) != 0 && (p = readLong()) != 0) {
		if(p==1){
			int result = (size+1)/2;
			pw.println(LINE + " = " + result + ", " + COLUMN + " = " + result + ".");
		}else{
			int biturn = (int)Math.ceil(Math.sqrt(p));
			long start = (biturn-1)*(biturn-1)+1;
			long end = biturn*biturn;
			long midElement = (start+end)/2;
			int mid = (size+1)/2;
			int row, col;
			if(p>midElement){
				
				if((biturn%2)==0){
				//left down	II
					col = mid-(biturn/2);
					row = mid+(biturn/2) - (int)(p-midElement);
					pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				}else{
				//right up II
					col = mid+(biturn/2);
					row = mid-(biturn/2) + (int)(p-midElement);
					pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				}
			}else if(p<midElement){
				
				if((biturn%2)==0){
				//left down I
				row = mid+(biturn/2);
				col = mid-(biturn/2) + (int)(midElement-p);
				pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				
				}else{
				//right up I
				row = mid-(biturn/2);
				col = mid+(biturn/2) - (int)(midElement-p);
				pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				}
			}else{
				if((biturn%2)==0){
					//left down
					row = mid+(biturn/2);
					col = mid-(biturn/2);
					pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				}else{
					//right up
					row = mid-(biturn/2);
					col = mid+(biturn/2);
					pw.println(LINE + " = " + row + ", " + COLUMN + " = " + col + ".");
				}
			}
		}
	}

	pw.flush();
	pw.close();
}
}
