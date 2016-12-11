import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class UVA462 {
	private static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13, DECKSIZE = 13, SUITSIZE = 4, SPADE = 0, CLUB = 3, DIAMOND = 2, HEART = 1;
	private static final String BID = "BID", NOTRUMP = "NO-TRUMP", PASS = "PASS";
	private static int getRankIndex(char c){
		switch(c){
		case 'A':
			return ACE;
		case 'K':
			return KING;
		case 'Q':
			return QUEEN;
		case 'J':
			return JACK;
		case 'T':
			return 10;
			default:
				return c-'0';
		}
	}
	private static int getSuitIndex(char c){
		switch(c){
		case 'S':
			return SPADE;
		case 'D':
			return DIAMOND;
		case 'H':
			return HEART;
		case 'C':
			return CLUB;
			default: 
				throw new InputMismatchException();
		}
	}
	private static char getRevSuitIndex(int index){
		switch(index){
		case SPADE:
			return 'S';
		case DIAMOND:
			return 'D';
		case CLUB:
			return 'C';
		case HEART:
			return 'H';
			default:
				throw new InputMismatchException();
		}
	}
	private static int ruleOne(int rank){
		switch(rank){
		case ACE:
			return 4;
		case KING:
			return 3;
		case QUEEN:
			return 2;
		case JACK:
			return 1;
			default:
				return 0;
		}
	}
	private static int subtractRules(boolean[][] hand,int[] suitCount){
		int subtractRulePts = 0;
		for(int i=0;i<SUITSIZE;++i){
			if(hand[i][KING] && (suitCount[i]==1)){
				subtractRulePts--;
			}
			if(hand[i][QUEEN] && (suitCount[i]<3)){
				subtractRulePts--;
			}
			if(hand[i][JACK] && (suitCount[i]<4)){
				subtractRulePts--;
			}
		}
		return subtractRulePts;
	}
	private static int addRules(int[] suitCount){
		int addRulePts = 0;
		for(int i=0;i<SUITSIZE;++i){
			if(suitCount[i]==2){
				addRulePts++;
			}
			else
				if(suitCount[i]<=1){
					addRulePts+=2;
				}
		}
		return addRulePts;
	}
	private static boolean allSuitsStopped(boolean[][] hand, int[] suitCount){
		for(int i=0;i<SUITSIZE;++i){
			if(!hand[i][ACE]
					&& !(hand[i][KING] && (suitCount[i]>1))
					&& !(hand[i][QUEEN] && (suitCount[i]>2))){
				return false;
			}
		}
		return true;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	String cards = "";
//	int testcases = 2;
//	while(testcases-->0){
	while((cards = br.readLine())!=null){
//		cards = br.readLine();
		boolean[][] hand = new boolean[SUITSIZE][DECKSIZE+1];
		int[] suitCount = new int[SUITSIZE];
		StringTokenizer st = new StringTokenizer(cards);
		int totalPoints = 0;
		for(int i=0;i<DECKSIZE; ++i){
		String currentCard = st.nextToken();
		int rank = getRankIndex(currentCard.charAt(0)), suit = getSuitIndex(currentCard.charAt(1));
		totalPoints+=ruleOne(rank);
		suitCount[suit]++;
		hand[suit][rank] = true;
		}
		
		totalPoints+=subtractRules(hand, suitCount);
		if((totalPoints>=16) && allSuitsStopped(hand, suitCount)){
			pw.println(BID+" "+NOTRUMP);
		}
		else
		{
			totalPoints+=addRules(suitCount);
			if(totalPoints>=14){
				StringBuilder bidSuit = null;
				int maxCount = 0;
				for(int i=0;i<SUITSIZE;++i){
					if(suitCount[i]>maxCount)
					{
						maxCount = suitCount[i];
						bidSuit = new StringBuilder();
						bidSuit.append(" ").append(getRevSuitIndex(i));
					}
				}
				pw.println(BID+bidSuit.toString());
			}
			else
			{
				pw.println(PASS);
			}
		}
		
		
	}

	pw.flush();
	pw.close();
	br.close();
}
}
