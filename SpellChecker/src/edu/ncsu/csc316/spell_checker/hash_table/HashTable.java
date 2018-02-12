/**
 * 
 */
package edu.ncsu.csc316.spell_checker.hash_table;


import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;

/**
 * The HashTable class makes the hash table
 * @author Pratik Khanal
 *
 */
public class HashTable {

	/** The capacity of hashTable */
	private int hashCapacity = 32693;
	/** Each entry is made up of arrayBasedList*/
	private ArrayBasedList[] hash;
	/** The size of hash*/
	private int size;
	/** Total number of words in the hash table */
	private int numWords = 0;
	/** Total number of lookups */
	private int lookups = 0;
	/** Total number of probes */
	private int probe = 0;
	
	/**
	 * The hash table constructor that initializes the hash table with value of null.
	 */
	public HashTable(){
		hash = new ArrayBasedList[hashCapacity];
		for(int i = 0; i < hashCapacity; i++){
			hash[i] = null;
		}
	}
	
	/**
	 * The insert method inserts the string to calculated index
	 * @param word the word to add to the hash table
	 */
	public void insert (String word){
		if(word == null){
			return;
		}
		int idx = (int) this.function(word);
		if (hash[idx] == null){
			this.hash[idx] = new ArrayBasedList(word);
			this.hash[idx].hlist.add(word);
			this.size++;
			numWords++;
		} else {
			hash[idx].hlist.add(word);
			this.size++;
			numWords++;
		}
	}
	
	/**
	 * The lookup method look into the hash table to find the word at calculated index
	 * @param word the word to find.
	 * @return true if the word is found, false if not found.
	 */
	public boolean lookup (String word){
		lookups++;
		int idx = this.function(word);
		if(hash[idx] == null){
			return false;
		} else {
			for(int i = 0; i < hash[idx].hlist.size(); i++){
				probe++;
				if(hash[idx].hlist.get(i).equals(word)){
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * The function generates the hash table index
	 * Used the Cyclic Shift hash code from the web-site below.
	 * https://www.cpp.edu/~ftang/courses/CS240/lectures/hashing.htm 
	 * @param word the word from which unique index is created
	 * @return the index 
	 */
	public int function(String word){
		int hashCode = 0;
		int i = 0;
		while(i < word.length()){
			hashCode = (hashCode << 13) | (hashCode >>> 27); 
			hashCode += (int) word.charAt(i);
			i++;
		}
		return Math.abs(hashCode % hashCapacity);
		
	}
	
	/**
	 * The size function returns the size of hash
	 * @return size the size of hash
	 */
	public int size(){
		return this.size;
	}
	
	/**
	 * The get lookups returns the number of lookups.
	 * @return number of lookups
	 */
	public int getLookups(){
		return this.lookups;
	}
	
	/**
	 * The get probe returns the number of probes
	 * @return the number of probes
	 */
	public int getProbe(){
		return this.probe;
	}
	
	/**
	 * The Number of words in the hash
	 * @return the number of words in the hash.
	 */
	public int getNumWords(){
		return this.numWords;
	}
}
