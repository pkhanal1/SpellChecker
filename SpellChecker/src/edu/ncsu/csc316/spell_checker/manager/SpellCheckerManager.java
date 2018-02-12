/**
 * 
 */
package edu.ncsu.csc316.spell_checker.manager;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.spell_checker.hash_table.HashTable;
import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;
import edu.ncsu.csc316.spell_checker.list.WordList;

/**
 * The SpellCheckerManager class takes in file name of file and makes a hash table from the dictionary.
 * Than the class takes in the file which needs to be spell checked. And returns a list of incorrect word
 * in a wordList.
 * @author Pratik Khanal
 *
 */
public class SpellCheckerManager {

	/** The hashtable */
	private HashTable hashTable;
	/** The list for incorrect words in the given text */
	public ArrayBasedList incorrect;
	private static WordList table = new ArrayBasedList();
	private static WordList spcheck = new ArrayBasedList();
	
	public static void main(String[] args){
		System.out.println("Dictionary file name.");
		Scanner s = new Scanner(System.in);
		String dname = s.next();
		System.out.println("Sell check file name.");
		String fname = s.next();
		
		try{
			Scanner scan = new Scanner(new FileInputStream(dname));
			Scanner scan2 = new Scanner(new FileInputStream(fname));
			while(scan.hasNext()){
				table.add(scan.next());
			}
			while(scan2.hasNext()){
				spcheck.add(scan2.next());
			}
		}catch (FileNotFoundException e){
			System.out.println("File not found.");
		}
		
		SpellCheckerManager m = new SpellCheckerManager(table);
		m.spellCheck(spcheck);
		System.out.println("Lookups:                " + m.hashTable.getLookups());
		System.out.println("Number of words id dic: " + m.hashTable.getNumWords());
		System.out.println("Number of probe:        " + m.hashTable.getProbe());
		System.out.println("Num of word to check:	" + m.spcheck.size());
		System.out.println("Num of missspelled: 	" + m.incorrect.size());
		System.out.println("Avg probe per word:     " + (m.hashTable.getProbe() * 1.0) / (m.spcheck.size() * 1.0));
		System.out.println("Avg prob per lookup:    " + (m.hashTable.getProbe() * 1.0) / (m.hashTable.getLookups() * 1.0));
	}
	
	/**
	 * The Spell Checker manager constructor takes in the dictionary file name.
	 * Than adds the words to the dictionary (hash table).
	 * @param dictionaryFileName the name of the dictionary file.
	 */
	public SpellCheckerManager(String dictionaryFileName) {
		hashTable = new HashTable();
		Scanner scan = null;
		try {
			scan = new Scanner(new File(dictionaryFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Problem readin the file.");
		}
		
		while(scan.hasNext()){
			hashTable.insert(scan.next());
		}
	}
	
	/**
	 * This method accept a WordList of words in a dictionary. This constructor gives an 
	 * alternative to passing-in a fileName parameter. 
	 * @param dictionary the wordlist file that contains the words for dictionary.
	 */
	public SpellCheckerManager(WordList dictionary) {
		hashTable = new HashTable();
		int i = 0;
		while(i < dictionary.size()){
			hashTable.insert(dictionary.get(i));
			i++;
		}
	}
	
	/**
	 * This method accepts the words to be spell checked.
	 * @param inputText the Wordlist file that contains the words to be spelled checked.
	 * @return incorrect the WordList that contains potentially misspelled words.
	 */
	public WordList spellCheck(WordList inputText){
		incorrect = new ArrayBasedList();
		boolean exits = false;
		for(int i = 0; i < inputText.size(); i++){
			//wordsTocheck++;
			exits = hashTable.lookup(inputText.get(i));
			String unchanged = inputText.get(i);
			String c = inputText.get(i);
			if(exits){
				continue;
			} else {
				exits = checkSpelling(unchanged);
			}
			if(!exits){
				incorrect.addSorted(c);
			}
		}
		return incorrect;
		
	}
	
	/**
	 * This method takes in a String word and check if the given index lies in the dictionary or not.
	 * @param s the word to be spelled checked by removing various suffixes.
	 * @return true if the word is in the list, else false.
	 */
	public boolean checkSpelling(String s){
		
		StringBuilder ns = new StringBuilder(s);
		boolean b = false;
		String prev = "";
		if(Character.isUpperCase(s.charAt(0))){
			ns.setCharAt(0, Character.toLowerCase(ns.charAt(0)));
			s = ns.toString();
			b = hashTable.lookup(s);
		}
		while(checkEndings(s) && !b){
			if(!b && s.endsWith("'s")){
				ns.delete(s.length() - 2, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
			} else if(!b && s.endsWith("s")){
				if(!s.endsWith("es") && prev.equals("b"))
					return b;
				ns.delete(s.length() - 1, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
				prev = "s";
				if(!b && s.endsWith("e")){
					ns.delete(s.length() - 1, ns.length());
					b = hashTable.lookup(ns.toString());
					s = ns.toString();
				}
			} else if(!b && s.endsWith("r")){
				if(!s.endsWith("er") && prev.equals("r"))
					return b;
				ns.delete(s.length() - 1, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
				prev = "r";
				if(!b && s.endsWith("e")){
					ns.delete(s.length() - 1, ns.length());
					b = hashTable.lookup(ns.toString());
					s = ns.toString();
				}
			} else  if(!b && s.endsWith("d") ){
				if(!s.endsWith("ed") && prev.equals("d"))
					return b;
				ns.delete(s.length() - 1, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
				prev = "d";
				if(!b && s.endsWith("e")){
					ns.delete(s.length() - 1, ns.length());
					b = hashTable.lookup(ns.toString());
					s = ns.toString();
				}
			} else  if(!b && s.endsWith("ing")){
				ns.delete(s.length() - 3, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
				if(!b ){
					ns.append("e");
					b = hashTable.lookup(ns.toString());
					s = ns.toString();
					if(!b){
						ns.delete(ns.length() - 1, ns.length());
						s = ns.toString();
					}
				}
			} else if(!b && s.endsWith("ly")){
				ns.delete(s.length() - 2, ns.length());
				b = hashTable.lookup(ns.toString());
				s = ns.toString();
			}
		}
		
		return b;
		
	}
	
	/**
	 * This method takes in an string and checks if the ending has certain suffixes.
	 * @param s passed string which may contain listed suffixes.
	 * @return true if the passed string contains suffixes, else returns false.
	 */
	public boolean checkEndings(String s){
		
		if(s.endsWith("'s") || s.endsWith("s") || s.endsWith("es") || s.endsWith("r") || s.endsWith("er") ||
				s.endsWith("d") || s.endsWith("ed") || s.endsWith("ing") || s.endsWith("ly")){
			return true;
		}
		
		return false;
		
	}
}
