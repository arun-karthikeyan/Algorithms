import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA637 {
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
	stream = System.in;
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int test;

	while ((test=readInt())!=0) {
		pw.println("Printing order for "+test+" pages:");
		int totalSheets = (test+3)/4;
		int printOrderLen = totalSheets*4;
		String[] printOrder = new String[printOrderLen];
		int i = 0;
		while(i<test){
			printOrder[i] = (++i)+"";
		}
		while(i<printOrder.length){
			printOrder[i++] = "Blank";
		}
		for(i=0; i<printOrderLen/2; ++i){
			if(!printOrder[i].equals(printOrder[printOrderLen-i-1])){
				pw.print("Sheet "+((i/2)+1)+", ");
			if((i&1)==1){
				pw.println("back : "+printOrder[i]+", "+printOrder[printOrderLen-i-1]);
			}else{
				pw.println("front: "+printOrder[printOrderLen-i-1]+", "+printOrder[i]);
			}
			}
		}
	}

	pw.flush();
	pw.close();
}
}
