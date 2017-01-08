import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA403 {
	private static final HashMap<Character, String[]> C5;
	private static final String C5FONT = "C5";
	private static final char C5BLANK = '.';
	private static final char C1BLANK = ' ';
	private static final String END="\n------------------------------------------------------------\n";
	static {
		C5 = new HashMap<Character, String[]>();
		C5.put('A', new String[]{".***..", "*...*.", "*****.", "*...*.", "*...*."});
		C5.put('B', new String[]{"****..", "*...*.", "****..", "*...*.", "****.."});
		C5.put('C', new String[]{".****.", "*...*.", "*.....", "*.....", ".****."});
		C5.put('D', new String[]{"****..", "*...*.", "*...*.", "*...*.", "****.."});
		C5.put('E', new String[]{"*****.", "*.....", "***...", "*.....", "*****."});
		C5.put('F', new String[]{"*****.", "*.....", "***...", "*.....", "*....."});
		C5.put('G', new String[]{".****.", "*.....", "*..**.", "*...*.", ".***.."});
		C5.put('H', new String[]{"*...*.", "*...*.", "*****.", "*...*.", "*...*."});
		C5.put('I', new String[]{"*****.", "..*...", "..*...", "..*...", "*****."});
		C5.put('J', new String[]{"..***.", "...*..", "...*..", "*..*..", ".**..."});
		C5.put('K', new String[]{"*...*.", "*..*..", "***...", "*..*..", "*...*."});
		C5.put('L', new String[]{"*.....", "*.....", "*.....", "*.....", "*****."});
		C5.put('M', new String[]{"*...*.", "**.**.", "*.*.*.", "*...*.", "*...*."});
		C5.put('N', new String[]{"*...*.", "**..*.", "*.*.*.", "*..**.", "*...*."});
		C5.put('O', new String[]{".***..", "*...*.", "*...*.", "*...*.", ".***.."});
		C5.put('P', new String[]{"****..", "*...*.", "****..", "*.....", "*....."});
		C5.put('Q', new String[]{".***..", "*...*.", "*...*.", "*..**.", ".****."});
		C5.put('R', new String[]{"****..", "*...*.", "****..", "*..*..", "*...*."});
		C5.put('S', new String[]{".****.", "*.....", ".***..", "....*.", "****.."});
		C5.put('T', new String[]{"*****.", "*.*.*.", "..*...", "..*...", ".***.."});
		C5.put('U', new String[]{"*...*.", "*...*.", "*...*.", "*...*.", ".***.."});
		C5.put('V', new String[]{"*...*.", "*...*.", ".*.*..", ".*.*..", "..*..."});
		C5.put('W', new String[]{"*...*.", "*...*.", "*.*.*.", "**.**.", "*...*."});
		C5.put('X', new String[]{"*...*.", ".*.*..", "..*...", ".*.*..", "*...*."});
		C5.put('Y', new String[]{"*...*.", ".*.*..", "..*...", "..*...", "..*..."});
		C5.put('Z', new String[]{"*****.", "...*..", "..*...", ".*....", "*****."});
		C5.put(' ', new String[]{"......", "......", "......", "......", "......"});
	}
	private static void fillGridC5TopLeft(char[][] grid, int top, int left, String s){
		String[] c5String = new String[5];
		Arrays.fill(c5String, "");
		
		for(int i=0, iLen = s.length(); i<iLen; ++i){
			String[] c5Char = C5.get(s.charAt(i));
			for(int j=0;j<5; ++j){
				c5String[j]+=c5Char[j];
			}
		}
		
		for(int i=top, k=0, kLen = c5String.length; i<60 && k<kLen; ++i, ++k){
			for(int j=left, l=0, lLen = c5String[k].length(); j<60 && l<lLen; ++j, ++l){
				char currentChar = c5String[k].charAt(l);
				if(currentChar!=C5BLANK){
					grid[i][j] = currentChar;
				}
			}
		}
	}
	private static void fillGridC5TopRight(char[][] grid, int top, int right, String s){
		String[] c5String = new String[5];
		Arrays.fill(c5String, "");
		
		for(int i=0, iLen = s.length(); i<iLen; ++i){
			String[] c5Char = C5.get(s.charAt(i));
			for(int j=0;j<5; ++j){
				c5String[j]+=c5Char[j];
			}
		}
		
		for(int i=top, k=0, kLen = c5String.length; i<60 && k<kLen; ++i, ++k){
			for(int j=right, l=c5String[k].length()-1; j>=0 && l>=0; --j, --l){
				char currentChar = c5String[k].charAt(l);
				if(currentChar!=C5BLANK){
					grid[i][j] = currentChar;
				}
			}
		}
		
	}
	private static void fillGridC1TopLeft(char[][] grid, int top, int left, String s){
			for(int j=left, k = 0, kLen = s.length(); j<60 && k<kLen; ++j, ++k){
				char currentChar = s.charAt(k);
				if(currentChar!=C1BLANK){
					grid[top][j] = s.charAt(k);
				}
			}
	}
	
	private static void fillGridC1Center(char[][] grid, int top, String s){
		
		int rowLen = s.length();
		int rightHalfStart = rowLen/2;
		int leftHalfEnd = rightHalfStart-1;
		
		for(int j=29, k = leftHalfEnd; j>=0 && k>=0; --j, --k){
			char currentChar = s.charAt(k);
			if(currentChar!=C1BLANK){
				grid[top][j] = s.charAt(k);
			}
		}

		for(int j=30, k = rightHalfStart, kLen = s.length(); j<60 && k<kLen; ++j, ++k){
			char currentChar = s.charAt(k);
			if(currentChar!=C1BLANK){
				grid[top][j] = s.charAt(k);
			}
		}

	}
	
	private static void fillGridC5Center(char[][] grid, int top, String s){
		String[] c5String = new String[5];
		Arrays.fill(c5String, "");
		
		for(int i=0, iLen = s.length(); i<iLen; ++i){
			String[] c5Char = C5.get(s.charAt(i));
			for(int j=0;j<5; ++j){
				c5String[j]+=c5Char[j];
			}
		}
		
		int rowLen = c5String[0].length();
		int rightHalfStart = rowLen/2;
		int leftHalfEnd = rightHalfStart-1;
		
		for(int i=top, k=0, kLen = c5String.length; i<60 && k<kLen; ++i, ++k){
			for(int j=30, l=rightHalfStart, lLen = c5String[k].length(); j<60 && l<lLen; ++j, ++l){
				char currentChar = c5String[k].charAt(l);
				if(currentChar!=C5BLANK){
					grid[i][j] = currentChar;
				}
			}
		}
		
		for(int i=top, k=0, kLen = c5String.length; i<60 && k<kLen; ++i, ++k){
			for(int j=29, l=leftHalfEnd; j>=0 && l>=0; --j, --l){
				char currentChar = c5String[k].charAt(l);
				if(currentChar!=C5BLANK){
					grid[i][j] = currentChar;
				}
			}
		}
	}
	
	private static void fillGridC1TopRight(char[][] grid, int top, int right, String s){
			for(int j=right, k = s.length()-1; j>=0 && k>=0; --j, --k){
				char currentChar = s.charAt(k);
				if(currentChar!=C1BLANK){
					grid[top][j] = s.charAt(k);
				}
			}
	}
	private static String getDelimitedString(String str){
		int idx1 = -1, idx2 = -1;
		for(int i=0, iLen = str.length(); i<iLen; ++i){
			if(str.charAt(i)=='|'){
				if(idx1==-1){
					idx1=i;
				}else
					if(idx2==-1){
						idx2=i;
						break;
					}
			}
		}
		return str.substring(idx1+1, idx2);
	}
	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String command = "";

		while ((command = br.readLine()) != null) {
			char[][] grid = new char[60][60];
			for(int i=0; i<60; ++i){
				Arrays.fill(grid[i], '.');
			}
			
			while (!(command).equals(".EOP")) {
				StringTokenizer st = new StringTokenizer(command);
				String c = st.nextToken();
				String font = st.nextToken();
				int row = Integer.parseInt(st.nextToken());
				switch (c) {
				case ".C": {
					String str = getDelimitedString(command);
					if(font.equals(C5FONT)){
						fillGridC5Center(grid, row-1, str);
					}else{
						fillGridC1Center(grid, row-1, str);
					}
				}
					break;
				case ".P": {
					int col = Integer.parseInt(st.nextToken());
					String str = getDelimitedString(command);
					if(font.equals(C5FONT)){
						fillGridC5TopLeft(grid,row-1,col-1,str);
					}else{
						fillGridC1TopLeft(grid,row-1,col-1,str);
					}
					
				}
					break;
				case ".L": {
					String str = getDelimitedString(command);
					if(font.equals(C5FONT)){
						fillGridC5TopLeft(grid,row-1,0,str);
					}else{
						fillGridC1TopLeft(grid,row-1,0,str);
					}
				}
					break;
				case ".R": {
					String str = getDelimitedString(command);
					if(font.equals(C5FONT)){
						fillGridC5TopRight(grid,row-1,59,str);
					}else{
						fillGridC1TopRight(grid,row-1,59,str);
					}
				}
					break;
				}
				command = br.readLine();
			}
			for(int i=0; i<60; i++){
				for(int j=0; j<60; j++){
					pw.print(grid[i][j]);
				}
				pw.println();
			}
			pw.println(END);
			
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
