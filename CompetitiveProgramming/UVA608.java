	import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
	
	public class UVA608 {
		private static final String UP="up", DOWN = "down", EVEN = "even";
		private static HashSet<Character> getUnion(ArrayList<String> list){
			HashSet<Character> union = new HashSet<Character>();
			for(int i=0, iLen = list.size(); i<iLen; ++i){
				String currentString = list.get(i);
				for(int j=0, jLen = currentString.length(); j<jLen; ++j){
					union.add(currentString.charAt(j));
				}
			}
			return union;
		}
		private static HashSet<Character> getUnion(HashSet<Character> a, HashSet<Character> b){
			HashSet<Character> setUnion = new HashSet<Character>(a);
			Iterator<Character> setBIterator = b.iterator();
			while(setBIterator.hasNext()){
				setUnion.add(setBIterator.next());
			}
			return setUnion;
		}
		@SuppressWarnings("unchecked")
		private static HashSet<Character> getIntersection(ArrayList<String> list){
			HashSet<Character> intersect = new HashSet<Character>();
			int listSize = list.size();
			HashSet<Character>[] unions = new HashSet[listSize];
			for(int i=0; i<listSize; ++i){
				unions[i] = new HashSet<Character>();
			}
			for(int i=0; i<listSize; ++i){
				String currentString = list.get(i);
				for(int j=0, jLen = currentString.length(); j<jLen; ++j){
					unions[i].add(currentString.charAt(j));
				}
			}
			if(listSize>0){
				Iterator<Character> firstOne = unions[0].iterator();
				while(firstOne.hasNext()){
					Character currentChar = firstOne.next();
					boolean pass = true;
					for(int i=1; i<listSize; ++i){
						if(!unions[i].contains(currentChar)){
							pass = false;
							break;
						}
					}
					if(pass){
						intersect.add(currentChar);
					}
				}
			}
			return intersect;
		}
		private static HashSet<Character> getSetDifference(HashSet<Character> a, HashSet<Character> b){
			HashSet<Character> diff = new HashSet<Character>(a);
			Iterator<Character> setBIterator = b.iterator();
			while(setBIterator.hasNext()){
				diff.remove(setBIterator.next());
			}
			return diff;
		}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//				BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			ArrayList<String> light = new ArrayList<String>();
			ArrayList<String> heavy = new ArrayList<String>();
			ArrayList<String> equal = new ArrayList<String>();
			
			for(int i=0; i<3; ++i){
				StringTokenizer st  = new StringTokenizer(br.readLine());
				String left = st.nextToken(), right = st.nextToken(), verdict = st.nextToken();
				switch(verdict){
				case UP:
					light.add(right);
					heavy.add(left);
					break;
				case DOWN:
					light.add(left);
					heavy.add(right);
					break;
				case EVEN:
					equal.add(left);
					equal.add(right);
					break;
				}
			}
			
			HashSet<Character> unionEqual = getUnion(equal);
			HashSet<Character> unionLight = getUnion(light);
			HashSet<Character> unionHeavy = getUnion(heavy);
			
			HashSet<Character> lightIntersect = getIntersection(light);
			HashSet<Character> heavyIntersect = getIntersection(heavy);
			
			HashSet<Character> lightResult = getSetDifference(lightIntersect, getUnion(unionHeavy, unionEqual));
			if(lightResult.size()>0){
				pw.println(lightResult.iterator().next()+" is the counterfeit coin and it is light.");
			}else{
				HashSet<Character> heavyResult = getSetDifference(heavyIntersect, getUnion(unionLight, unionEqual));
				pw.println(heavyResult.iterator().next()+" is the counterfeit coin and it is heavy.");
			}
			
		}
	
		br.close();
		pw.flush();
		pw.close();
	}
	}
