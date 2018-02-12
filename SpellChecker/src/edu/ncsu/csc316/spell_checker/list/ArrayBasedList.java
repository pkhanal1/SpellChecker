/**
 * 
 */
package edu.ncsu.csc316.spell_checker.list;

import java.util.Arrays;

/**
 * The array based list creates the array list.
 * @author Pratik Khanal
 *
 */
public class ArrayBasedList implements WordList {

	/** The String list */
	private String[] list;
	/** The ArrayBasedList object for hash table */
	public ArrayBasedList hlist;
	/** The size of array */
	private int size;
	/** The default capacity of array */
	private static final int DEFAULT_CAPACITY = 10;
	
	/**
	 * The constructor that initializes the array list.
	 */
	public ArrayBasedList() {
		list = new String[DEFAULT_CAPACITY];
	}
	
	/**
	 * The constructor that checks the list size. 
	 * @param capacity the capacity of the array list.
	 */
	public ArrayBasedList(int capacity){
		if (capacity < 0){
			throw new IllegalArgumentException();
		}
		String[] a =  new String[DEFAULT_CAPACITY];
		this.list = a;
	}
	
	/**
	 * The constructor for the hash table array list.
	 * @param word the word to be added in the list.
	 */
	public ArrayBasedList(String word){
		this.hlist = new ArrayBasedList();
		size = 0;
	}
	
	/**
	 * The private method to ensure capacity of list.
	 * @param capacity capacity of list.
	 */
	private void ensureCapacity(int capacity){
		while(capacity >= list.length){
			list = Arrays.copyOf(list, 2 * list.length);
		}
	}
	
	/**
	 * The toString method returns the string representation of the list.
	 * @return the string representation of the list.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("WordList[");
		for(int i = 0; i < size(); i++ ){
			if(i == size() - 1){
				s.append(this.list[i] + "]");
			}
			s.append(this.list[i] + ", ");
		}
		return s.toString();
	}

	/**
	 * The add method adds the words to the end of the list.
	 * @param word the word to be added in the list.
	 */
	@Override
	public void add(String word) {
		ensureCapacity(this.size);
		if(word == null){
			throw new NullPointerException();
		}
		list[size] = word;
		size++;
		
	}

	/**
	 * Add sorted method adds the words in sorted manner.
	 * @param word the word to be added in the list.
	 */
	@Override
	public void addSorted(String word) {
		ensureCapacity(this.size);
		if(word == null)
			throw new NullPointerException();
		int j = 0;
		for(int i = 0; i < this.size; i++){
			if(list[i].equals(word)){
				return;
			}
		}
		for(int i = 0; i < this.size; i++){
			if(list[i].toLowerCase().compareTo(word.toLowerCase()) > 0){
				break;
			}
			j++;
		}
		for(int i = this.size; i > j; i--){
			list[i] = list[i - 1];
		}
		list[j] = word;
		size++;
	}


	/**
	 * The size of the list.
	 * @return size of the list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * The get function gets returns the word from the given index.
	 * @param index the index for the word to return.
	 * @return the word at the given index.
	 */
	@Override
	public String get(int index) {
		if (index < 0 || index > size){
			throw new ArrayIndexOutOfBoundsException(); 
		}
		return list[index];
	}

	
}
