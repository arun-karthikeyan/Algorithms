import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12356 {
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

private static final int L = 0, R = 1, INVALID = -1;
private static final boolean ALIVE = false, DEAD = true;

private static int getParent(int[][] soldiers, boolean[] status, int idx, int dir){
	if(soldiers[idx][dir]==INVALID || status[soldiers[idx][dir]]==ALIVE){
		return soldiers[idx][dir];
	}
	return (soldiers[idx][dir] = getParent(soldiers,status,soldiers[idx][dir],dir));
}

public static void main(String[] args) throws Exception {
	if(args.length>0 && "fileip".equals(args[0]))
	{
		stream = new FileInputStream(new File("testip.txt"));
	}else{
		stream = System.in;
	}
	
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int s,b;
	
	while ((s=readInt())!=0 && (b=readInt())!=0) {

		boolean[] status = new boolean[s];
		int[][] soldiers = new int[s][2];
		soldiers[0][L] = INVALID;
		soldiers[s-1][R] = INVALID;
		
		for(int i=1; i<s; ++i){
			soldiers[i][L] = i-1;
		}
		for(int i=s-2; i>=0; --i){
			soldiers[i][R] = i+1;
		}
		
		for(int i=0; i<b; ++i){
			int l = readInt()-1, r = readInt()-1;
			
			int left = getParent(soldiers, status, l, L);
			if(left==INVALID){
				pw.print("* ");
			}else{
				pw.print((left+1)+" ");
			}
			
			int right = getParent(soldiers, status, r, R);
			if(right==INVALID){
				pw.println("*");
			}else{
				pw.println((right+1));
			}
			
			for(int j=l; j<=r; ++j){
				status[j] = DEAD;
				soldiers[j][L] = left;
				soldiers[j][R] = right;
			}
		}
		pw.println("-");
		
	}

	pw.flush();
	pw.close();
}
}
