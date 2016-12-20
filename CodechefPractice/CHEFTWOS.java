import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CHEFTWOS {
	private static final long MOD = ((long) 1e9) + 7;
	private static final char ONE = '1';
	private static char[] a, b;
	
	private static long pow(int a, int b){
			if (b == 0) return 1;
			long t = pow(a, b>>1);
			long c = (t * t) % MOD;
			if (b % 2 == 1)
				c = (c * a) % MOD;
			return c;
	}
	private static boolean canSwapEfficiently(int start, int end, int aprev2, int bprev2){
		
		while(start<end){
			boolean aFails = false, bFails = false;
			int newAprev2 = aprev2, newBprev2 = bprev2;
			
			if(a[start]==ONE){
				if(aprev2==1){
					aFails = true;
				}else if(aprev2==2){
					newAprev2=0;
				}
			}else{
				if(aprev2==1 || aprev2==0){
					newAprev2++;
				}else if(aprev2==2){
					aFails = true;
				}
			}
			
			if(b[start]==ONE){
				if(bprev2==1){
					bFails = true;
				}else if(bprev2==2){
					newBprev2=0;
				}
			}else{
				if(bprev2==1 || bprev2==0){
					newBprev2++;
				}else if(bprev2==2){
					bFails = true;
				}
			}
			
			if(aFails || bFails){
				char temp = a[start];
				a[start] = b[start];
				b[start] = temp;
				
				aFails = bFails = false;
				newAprev2 = aprev2; newBprev2 = bprev2;
				
				if(a[start]==ONE){
					if(aprev2==1){
						aFails = true;
					}else if(aprev2==2){
						newAprev2=0;
					}
				}else{
					if(aprev2==1 || aprev2==0){
						newAprev2++;
					}else if(aprev2==2){
						aFails = true;
					}
				}
				
				if(b[start]==ONE){
					if(bprev2==1){
						bFails = true;
					}else if(bprev2==2){
						newBprev2=0;
					}
				}else{
					if(bprev2==1 || bprev2==0){
						newBprev2++;
					}else if(bprev2==2){
						bFails = true;
					}
				}
				
				if(aFails || bFails){
					return false;
				}
				
			}
			start++;
			aprev2 = newAprev2;
			bprev2 = newBprev2;
		}
		
		return aprev2==0 && bprev2==0;
	}

	private static int countMatchingIndices() {
		int count = 0;
		for (int i = 0, iLen = a.length - 1; i < iLen; ++i) {
			if (a[i] == b[i]) {
				count++;
			}
		}
		return count + 1;
	}

	private static int countPartitions() {
		int partitions = 0;
		int length = a.length - 1;
		int i = 0;
		while (i < length) {
			while (i < length && a[i] == b[i]) {
				i++;
			}
			if(i>=length){
				continue;
			}
			while (i < length && (a[i] != ONE || b[i] != ONE)) {
				i++;
			}
			partitions++;
		}
		return partitions;
	}

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int testcases = Integer.parseInt(br.readLine());

		while (testcases-- > 0) {
			a = br.readLine().toCharArray();
			b = br.readLine().toCharArray();
			if (canSwapEfficiently(0, a.length-1, 0, 0)) {
				int matchingIndices = countMatchingIndices();
				long result = (pow(2, countPartitions()) * pow(2,matchingIndices)) % MOD;
				pw.println(result);

			} else {
				pw.println("0");
			}

		}
		pw.flush();
		pw.close();
		br.close();

	}
}
