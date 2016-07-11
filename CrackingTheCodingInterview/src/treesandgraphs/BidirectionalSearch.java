package treesandgraphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

//bi-directional search in an undirected graph
public class BidirectionalSearch {
	private static final int START = 0, END = 1, NOTINITIALIZED=-1;
	
	class Visited{
		int source;
		LinkedList<Integer> path;
		public Visited(){
			this.source = BidirectionalSearch.NOTINITIALIZED; 
		}
	}
	
	class SearchNode{
		int src;
		LinkedList<Integer> path;
		public SearchNode(int src, int startNode){
			this.src = src;
			this.path = new LinkedList<Integer>();
			this.path.add(startNode);
		}
		public SearchNode(SearchNode parent, int currentNode){
			this.src = parent.getSrc();
			this.path = new LinkedList<Integer>(parent.getPath());
			if(parent.getSrc()==START){
				this.path.addLast(currentNode);
			}else{
				this.path.addFirst(currentNode);
			}
		}
		public LinkedList<Integer> getPath(){
			return this.path;
		}
		public int getSrc(){
			return this.src;
		}
		public int getCurrentNode(){
			if(this.src==START){
				return path.peekLast();
			}
			return path.peekFirst();
		}
		
		public String toString(){
			return getCurrentNode()+"";
		}
	}
	
	
	public LinkedList<Integer> bidirecSearch(ArrayList<Integer>[] adjList,int src, int dest){
		
		LinkedList<SearchNode> bfs = new LinkedList<SearchNode>();
		//add both src and dest
		bfs.add(new SearchNode(BidirectionalSearch.START,src));
		bfs.add(new SearchNode(BidirectionalSearch.END,dest));
		
		//visited array
		Visited[] visited = new Visited[adjList.length];
		
		while(!bfs.isEmpty()){
			SearchNode currentNode = bfs.remove();
			if(visited[currentNode.getCurrentNode()]==null){
				//mark currentNode as visited
				visited[currentNode.getCurrentNode()] = new Visited();
				visited[currentNode.getCurrentNode()].source = currentNode.src;
				visited[currentNode.getCurrentNode()].path = new LinkedList<Integer>(currentNode.getPath());
				Iterator<Integer> iter = adjList[currentNode.getCurrentNode()].iterator();
				while(iter.hasNext()){
					bfs.add(new SearchNode(currentNode,iter.next()));
				}
			}else{
				if(currentNode.getSrc()!=visited[currentNode.getCurrentNode()].source){
					//found the match;
					LinkedList<Integer> startToMid = null;
					LinkedList<Integer> midToEnd = null;
					if(currentNode.getSrc()==START){
						startToMid = currentNode.path;
						midToEnd = visited[currentNode.getCurrentNode()].path;
					}else{
						midToEnd = currentNode.path;
						startToMid = visited[currentNode.getCurrentNode()].path;
					}
					midToEnd.remove();
					startToMid.addAll(midToEnd);
					return startToMid;
				}
			}
			
		}
		
		
		return null;
	}
	public static void main(String[] args) throws Exception{
		
		int n = 23;
		//adjacency list
		ArrayList<Integer>[] adjList = new ArrayList[n+1];
		//1 based
		for(int i=1;i<=n;++i){
			adjList[i] = new ArrayList<Integer>();
		}
		
		adjList[1].add(6);
		adjList[1].add(7);
		adjList[1].add(8);
		adjList[1].add(9);
		
		adjList[2].add(1);
		adjList[2].add(10);
		adjList[2].add(3);
		adjList[2].add(16);
		
		adjList[3].add(2);
		adjList[3].add(20);
		adjList[3].add(19);
		adjList[3].add(4);
		
		adjList[4].add(3);
		adjList[4].add(21);
		adjList[4].add(5);
		
		adjList[5].add(4);
		adjList[5].add(17);
		adjList[5].add(18);
		adjList[5].add(23);
		adjList[5].add(22);
		
		adjList[6].add(1);
		adjList[6].add(14);
		adjList[6].add(13);
		
		adjList[7].add(15);
		adjList[7].add(1);
		
		adjList[8].add(1);
		
		adjList[9].add(1);
		adjList[9].add(12);
		
		adjList[10].add(2);
		adjList[10].add(11);
		
		adjList[11].add(10);
		adjList[11].add(20);
		
		adjList[12].add(9);
		
		adjList[13].add(6);
		
		adjList[14].add(6);
		
		adjList[15].add(7);
		adjList[15].add(23);
		
		adjList[16].add(2);
		adjList[16].add(19);
		
		adjList[17].add(5);
		
		adjList[18].add(5);
		
		adjList[19].add(3);
		adjList[19].add(16);
		
		adjList[20].add(3);
		adjList[20].add(11);
		
		adjList[21].add(4);
		
		adjList[22].add(5);
		
		adjList[23].add(5);
		adjList[23].add(15);
		
		System.out.println(new BidirectionalSearch().bidirecSearch(adjList, 1, 23));
	}
}
