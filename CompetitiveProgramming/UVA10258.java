import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class UVA10258 {
	private static final int MAXCONTESTANTS = 100;
	private static final int MAXPROBLEMS = 9;
	private static final char CORRECT = 'C', INCORRECT = 'I';
	private static final int DEFAULT_TIME = Integer.MAX_VALUE;
	static class ContestantInfo implements Comparable<ContestantInfo>{
		int id;
		int problemsSolved;
		int totalTime;
		@Override
		public int compareTo(ContestantInfo o) {
			// TODO Auto-generated method stub
			if(this.problemsSolved==o.problemsSolved){
				if(this.totalTime==o.totalTime){
					return this.id-o.id;
				}else{
					return this.totalTime-o.totalTime;
				}
			}else{
				return o.problemsSolved-this.problemsSolved;
			}
		}
		public ContestantInfo(int id, int problemsSolved, int totalTime){
			this.id = id;
			this.problemsSolved = problemsSolved;
			this.totalTime = totalTime;
		}
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	br.readLine();
	while (testcases-- > 0) {
		int[][] scoreBoard = new int[MAXCONTESTANTS][MAXPROBLEMS];
		for(int i=0; i<MAXCONTESTANTS; ++i){
			Arrays.fill(scoreBoard[i], DEFAULT_TIME);
		}
		boolean[][] metaProbInfo = new boolean[MAXCONTESTANTS][MAXPROBLEMS];
		boolean[] metaContestantInfo = new boolean[MAXCONTESTANTS];
		ArrayList<ContestantInfo> finalScoreBoard = new ArrayList<ContestantInfo>();
		String submission;
		
		while(((submission=br.readLine())!=null) && submission.length()>0){
		StringTokenizer st = new StringTokenizer(submission);
		int contestant = Integer.parseInt(st.nextToken())-1;
		int prob = Integer.parseInt(st.nextToken())-1;
		int time = Integer.parseInt(st.nextToken());
		char decision = st.nextToken().charAt(0);
		if(!metaContestantInfo[contestant]){
			metaContestantInfo[contestant] = true;
			finalScoreBoard.add(new ContestantInfo(contestant+1, 0, 0));
		}
		
		if(scoreBoard[contestant][prob]==DEFAULT_TIME){
			scoreBoard[contestant][prob] = 0;
		}
		
		switch(decision){
		case CORRECT:
			if(!metaProbInfo[contestant][prob]){
			scoreBoard[contestant][prob]+=time;
			metaProbInfo[contestant][prob] = true;
			}
			break;
		case INCORRECT:
			if(!metaProbInfo[contestant][prob]){
				scoreBoard[contestant][prob]+=20;
			}
			break;
		}
		
		}
		for(int i=0, iLen = finalScoreBoard.size(); i<iLen; ++i){
			ContestantInfo currentContestant = finalScoreBoard.get(i);
			int totalTime = 0, problemsSolved = 0;
			for(int j=0; j<MAXPROBLEMS; ++j){
				if(metaProbInfo[currentContestant.id-1][j]){
					problemsSolved++;
					totalTime+=scoreBoard[currentContestant.id-1][j];
				}
			}
			currentContestant.totalTime = totalTime;
			currentContestant.problemsSolved = problemsSolved;
		}
			Collections.sort(finalScoreBoard);
			for(int i=0, iLen = finalScoreBoard.size(); i<iLen; ++i){
				ContestantInfo currentContestant = finalScoreBoard.get(i);
				pw.println(currentContestant.id+" "+currentContestant.problemsSolved+" "+currentContestant.totalTime);
			}
			if(testcases!=0){
				pw.println();
			}
		
	}

	br.close();
	pw.flush();
	pw.close();
}
}
