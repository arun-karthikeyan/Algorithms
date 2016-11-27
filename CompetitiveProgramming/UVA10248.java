import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10248 {
	private static final char ATTACK = 'Z';
	private static boolean inBounds(int i, int j){
		
		return i>=0 && i<8 && j>=0 && j<8;
	}
	private static void markAttackPositions(char[][] board){
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j){
				int k=-1, l=-1;
				switch(board[i][j]){
				case '\0':
				case ATTACK:
					break;
				case 'P':
					if(inBounds(i-1,j-1) && board[i-1][j-1]=='\0'){
						board[i-1][j-1]=ATTACK;
					}
					if(inBounds(i-1,j+1) && board[i-1][j+1]=='\0'){
						board[i-1][j+1]=ATTACK;
					}
					break;
				case 'p':
					if(inBounds(i+1, j-1) && board[i+1][j-1]=='\0'){
						board[i+1][j-1]=ATTACK;
					}
					if(inBounds(i+1, j+1) && board[i+1][j+1]=='\0'){
						board[i+1][j+1]=ATTACK;
					}
					break;
				case 'n':
				case 'N':
					if(inBounds(i+2, j-1) && board[i+2][j-1]=='\0'){
						board[i+2][j-1]=ATTACK;
					}
					if(inBounds(i+2, j+1) && board[i+2][j+1]=='\0'){
						board[i+2][j+1]=ATTACK;
					}
					if(inBounds(i-2, j-1) && board[i-2][j-1]=='\0'){
						board[i-2][j-1]=ATTACK;
					}
					if(inBounds(i-2, j+1) && board[i-2][j+1]=='\0'){
						board[i-2][j+1]=ATTACK;
					}
					if(inBounds(i+1, j-2) && board[i+1][j-2]=='\0'){
						board[i+1][j-2]=ATTACK;
					}
					if(inBounds(i+1, j+2) && board[i+1][j+2]=='\0'){
						board[i+1][j+2]=ATTACK;
					}
					if(inBounds(i-1, j-2)&& board[i-1][j-2]=='\0'){
						board[i-1][j-2]=ATTACK;
					}
					if(inBounds(i-1, j+2)&& board[i-1][j+2]=='\0'){
						board[i-1][j+2]=ATTACK;
					}
					break;
				case 'B':
				case 'b':
					k=i+1;l=j+1;
					while(k<8 && l<8){
						if(board[k][l]=='\0'){
							board[k][l]=ATTACK;
						}else if(board[k][l]!=ATTACK){
							break;
						}
						++k; ++l;
					}
					
					k=i-1; l=j+1;
					while(k>=0 && l<8){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						--k; ++l;
					}
					
					k=i+1; l=j-1;
					while(k<8 && l>=0){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						k++; --l;
					}
					
					k=i-1; l=j-1;
					while(k>=0 && l>=0){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						--k; --l;
					}
					break;

				case 'Q':
				case 'q':
					k=i+1; l=j+1;
					while(k<8 && l<8){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						++k; ++l;
					}
					
					k=i-1; l=j+1;
					while(k>=0 && l<8){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								k=-1;l=9;
							}
						--k; ++l;
					}

					k=i+1; l=j-1;
					while(k<8 && l>=0){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						++k; --l;
					}
					
					k=i-1; l=j-1;
					while(k>=0 && l>=0){
						if(board[k][l]=='\0')
							board[k][l]=ATTACK;
							else if(board[k][l]!=ATTACK){
								break;
							}
						--k; --l;
					}
					
					for(k=i+1;k<8;++k){
						if(board[k][j]=='\0')
						board[k][j]=ATTACK;
						else if(board[k][j]!=ATTACK){
							break;
						}
					}
					
					for(k=i-1;k>=0;--k){
						if(board[k][j]=='\0')
						board[k][j]=ATTACK;
						else if(board[k][j]!=ATTACK){
							break;
						}
					}
					
					for(k=j+1;k<8;++k){
						if(board[i][k]=='\0')
						board[i][k]=ATTACK;
						else if(board[i][k]!=ATTACK){
							break;
						}
					}
					
					for(k=j-1;k>=0;--k){
						if(board[i][k]=='\0')
						board[i][k]=ATTACK;
						else if(board[i][k]!=ATTACK){
							break;
						}
					}
					break;
				case 'r':
				case 'R':
					for(k=i+1;k<8;++k){
						if(board[k][j]=='\0')
						board[k][j]=ATTACK;
						else if(board[k][j]!=ATTACK){
							break;
						}
					}
					for(k=i-1;k>=0;--k){
						if(board[k][j]=='\0')
						board[k][j]=ATTACK;
						else if(board[k][j]!=ATTACK){
							break;
						}
					}
					
					for(k=j+1;k<8;++k){
						if(board[i][k]=='\0')
						board[i][k]=ATTACK;
						else if(board[i][k]!=ATTACK){
							break;
						}
					}
					
					for(k=j-1;k>=0;--k){
						if(board[i][k]=='\0')
						board[i][k]=ATTACK;
						else if(board[i][k]!=ATTACK){
							break;
						}
					}
					break;
				case 'k':
				case 'K':
					if(inBounds(i-1,j-1) && board[i-1][j-1]=='\0'){
						board[i-1][j-1]=ATTACK;
					}
					if(inBounds(i-1,j+1) && board[i-1][j+1]=='\0'){
						board[i-1][j+1]=ATTACK;
					}
					if(inBounds(i+1, j-1) && board[i+1][j-1]=='\0'){
						board[i+1][j-1]=ATTACK;
					}
					if(inBounds(i+1, j+1) && board[i+1][j+1]=='\0'){
						board[i+1][j+1]=ATTACK;
					}
					if(inBounds(i+1,j) && board[i+1][j]=='\0'){
						board[i+1][j] = ATTACK;
					}
					if(inBounds(i-1,j) && board[i-1][j]=='\0'){
						board[i-1][j] = ATTACK;
					}
					if(inBounds(i,j+1) && board[i][j+1]=='\0'){
						board[i][j+1]=ATTACK;
					}
					if(inBounds(i,j-1) && board[i][j-1]=='\0'){
						board[i][j-1]=ATTACK;
					}
					break;
				}
			}
		}
	}
	private static int getNoAttackPositions(char[][] board){
		int count= 0;
		for(int i=0;i<8;++i){
			for(int j=0;j<8;++j){
				if(board[i][j]=='\0'){
					count++;
				}
			}
		}
		return count;
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String row = "";
		
		while((row=br.readLine())!=null){
			String[] rows = row.split("/");
			char[][] board = new char[8][8];
			for(int i=0; i<8; ++i){
				String currentRow = rows[i];
				int idx = 0;
				for(int j=0, jLen=rows[i].length(); j<jLen; ++j){
					char ch = currentRow.charAt(j);
					if(Character.isDigit(ch)){
						idx = idx + (ch-'0');
					}else{
						board[i][idx]=ch;
						idx++;
					}
				}
			}
			markAttackPositions(board);
			pw.println(getNoAttackPositions(board));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
