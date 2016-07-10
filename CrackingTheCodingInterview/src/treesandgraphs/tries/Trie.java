package treesandgraphs.tries;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie {
	TrieNode root;
	
	public Trie(String[] words){
		this.root = new TrieNode();
		root.setTerminates(true);
		root.setWordCount(words.length);
		for(String word : words){
			root.addWord(word);
		}
	}
	
	public Trie(ArrayList<String> words){
		this.root = new TrieNode();
		root.setTerminates(true);
		root.setWordCount(words.size());
		for(String word : words){
			root.addWord(word);
		}
	}
	
	public boolean contains(String prefix, boolean exact){
		TrieNode lastNode = root;
		for(int i=0, iLen = prefix.length(); i<iLen; ++i){
			lastNode = lastNode.getChild().get(prefix.charAt(i));
			if(lastNode==null){
				return false;
			}
		}
		return !exact || lastNode.terminates();
	}
	
	public int countWords(String prefix){
		TrieNode lastNode = root;
		int count = 0;
		for(int i=0, iLen=prefix.length();i<iLen; ++i){
			lastNode = lastNode.getChild().get(prefix.charAt(i));
			if(lastNode==null){
				return count;
			}
		}
		return lastNode.getWordCount();
	}
	
	public static void main(String[] args) throws Exception{
		Trie t = new Trie(new String[]{"many","my","lie","a"});
		System.out.println("Tree contains the prefix 'man' : "+t.contains("man", false));
		System.out.println("Tree contains the string 'man' : "+t.contains("man", true));
		System.out.println("No of words starting with 'm' : "+t.countWords("m"));
		System.out.println("Tree contains word 'a' : "+t.contains("a", true));
		System.out.println("Total no of words in the tree : "+t.countWords(""));
	}
}

class TrieNode{
	Character character;
	HashMap<Character, TrieNode> children;
	Boolean terminates;
	Integer wordCount;
	
	public TrieNode(){
		this.children = new HashMap<Character, TrieNode>();
		this.terminates = false;
		this.character = null;
		this.wordCount = 0;
	}
	
	public TrieNode(Character c){
		this();
		this.character = c;
	}
	
	public void addWord(String word){
		if(word==null || word.isEmpty()){
			return;
		}
		
		Character firstChar = word.charAt(0);
		
		if(!children.containsKey(firstChar)){
			children.put(firstChar, new TrieNode(firstChar));
		}
		
		TrieNode child = children.get(firstChar);
		
		if(word.length()>1){
			child.addWord(word.substring(1));
		}else{
			child.setTerminates(true);
		}
		child.wordCount++;
	}
	
	public Character getChar(){
		return this.character;
	}
	
	
	public HashMap<Character, TrieNode> getChild(){
		return this.children;
	}
	
	public void setTerminates(boolean value){
		this.terminates = value;
	}
	
	public boolean terminates(){
		return this.terminates;
	}
	
	public int getWordCount(){
		return this.wordCount;
	}
	
	public void setWordCount(int wordCount){
		this.wordCount = wordCount;
	}
}
