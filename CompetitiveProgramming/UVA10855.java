import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10855 {
	private static void rotate90(char[][] matrix, int size){
		for(int i=1; i<size; ++i){
			for(int j=0; j<i; ++j){
				char temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		for(int i=0; i<size; ++i){
			for(int j=0, jSize = size/2; j<jSize; ++j){
				char temp = matrix[i][j];
				matrix[i][j] = matrix[i][size-j-1];
				matrix[i][size-j-1] = temp;
			}
		}
	}
	
	private static int occurrences(char[][] bigMatrix, char[][] smallMatrix, int bSize, int sSize){
		int count = 0;
		for(int i=0, iSize = bSize-sSize; i<=iSize; ++i){
			for(int j=0; j<=iSize; ++j){
				boolean flag = true;
				for(int k=0; k<sSize && flag; ++k){
					for(int l=0; l<sSize && flag; ++l){
						if(smallMatrix[k][l]!=bigMatrix[i+k][j+l]){
							flag = false;
						}
					}
				}
				if(flag){
					count++;
				}
			}
		}
		return count;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	StringTokenizer st = new StringTokenizer(br.readLine());
	int bigSize = Integer.parseInt(st.nextToken()), smallSize = Integer.parseInt(st.nextToken());
	while(bigSize!=0 || smallSize!=0){
		char[][] bigMatrix = new char[bigSize][bigSize];
		char[][] smallMatrix = new char[smallSize][smallSize];
		
		for(int i=0; i<bigSize; ++i){
			String currentRow = br.readLine();
			for(int j=0; j<bigSize; ++j){
				bigMatrix[i][j] = currentRow.charAt(j);
			}
		}
		
		for(int i=0; i<smallSize; ++i){
			String currentRow = br.readLine();
			for(int j=0; j<smallSize; ++j){
				smallMatrix[i][j] = currentRow.charAt(j);
			}
		}
		
		pw.print(occurrences(bigMatrix, smallMatrix, bigSize, smallSize)+" ");
		rotate90(smallMatrix,smallSize);
		pw.print(occurrences(bigMatrix, smallMatrix, bigSize, smallSize)+" ");
		rotate90(smallMatrix,smallSize);
		pw.print(occurrences(bigMatrix, smallMatrix, bigSize, smallSize)+" ");
		rotate90(smallMatrix,smallSize);
		pw.println(occurrences(bigMatrix, smallMatrix, bigSize, smallSize));
		
		st = new StringTokenizer(br.readLine());
		bigSize = Integer.parseInt(st.nextToken());
		smallSize = Integer.parseInt(st.nextToken());
	}

	br.close();
	pw.flush();
	pw.close();
}
}
