/**
 * 
 */
package edu.ncsu.csc316.spell_checker.list;

/**
 * The wordlist interface has method add which adds the given word to the list.
 * Addsorted method whic adds the given word to the list in increasing alphabetical order.
 * Size return the number of words in the list. And get gets the word from given index.
 * @author Pratik Khanal
 *
 */
public interface WordList {

	/**
	 * Adds the given word (as a string) to the list
	 * @param word the word to add into the list.
	 */
	public void add(String word);
	/**
	 * Adds the given word (as a string) to the list in increasing
	 *  alphabetical order (case insensitive).
	 * @param word the word to add into the list.
	 */
	public void addSorted(String word);
	/**
	 * This method returns the number of words in the list.
	 * @return size the number of words in the list.
	 */
	public int size();
	/**
	 * This method returns the word at the given index in the WordList.
	 * @param index the index of the word
	 * @return the word from the given index
	 */
	public String get(int index);
}
