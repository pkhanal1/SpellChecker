package edu.ncsu.csc316.spell_checker.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc316.spell_checker.hash_table.HashTable;
import edu.ncsu.csc316.spell_checker.list.ArrayBasedList;
import edu.ncsu.csc316.spell_checker.list.WordList;

/**
 * The SpellCheckerManagerTest class test the spell checker manager.
 * @author Pratik khanal
 *
 */
public class SpellCheckerManagerTest {

	/** Array list */
	private ArrayBasedList a = new ArrayBasedList();
	/** Hash table */
	private HashTable h = new HashTable();
	/** Word list */
	private WordList  b = (WordList) new ArrayBasedList();
	
	/**
	 * Checks the SpellCheckerManagerString() constructor.
	 */
	@Test
	public void testSpellCheckerManagerString() {
		a.addSorted("a");
	
		a.addSorted("and");
		a.addSorted("However");
		a.addSorted("those");
		a.addSorted("boss");
		a.addSorted("xyz");
		a.add("z");
		a.addSorted("y");
		a.addSorted("x");
		a.addSorted("p");
		a.add("s");
		a.add("i");
		a.toString();
		a.addSorted("y");
		assertTrue(a.get(0).equals("a"));
		
		//a.add
		for(int i = 0; i < a.size(); i++){
			System.out.println(a.get(i));
		}
		System.out.println("done");
	}

	/**
	 * Checks the SpellCheckerManagerString() constructor.
	 */
	@Test
	public void testSpellCheckerManagerWordList() {
		h.insert("add");
		h.insert("dda");
		h.lookup("add");
		assertTrue(h.lookup("add"));
		
		StringBuilder s = new StringBuilder("buhaha");
		s.delete(s.length() - 2, s.length());
		s.append("i");
		System.out.println(s.toString());
		String app = "dadad'aa";
		if(app.endsWith("'a"))
			System.out.println("true");
		
		System.out.println(h.function("add"));
		System.out.println(h.function("dda"));
		System.out.println(h.function("dad"));
		System.out.println(h.function("adda"));
	}

	/**
	 * Checks the SpellChecker method.
	 */
	@Test
	public void testSpellCheck() {
		SpellCheckerManager s = new SpellCheckerManager("test-files/dictionary-large.txt");
		Scanner scan = null;
		try{
			scan = new Scanner(new File("test-files/matters.txt"));
			while(scan.hasNext()){
				b.add(scan.next());
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		s = new SpellCheckerManager(b);
		s.spellCheck(b);
		for(int i = 0; i < s.incorrect.size(); i++){
			System.out.println(s.incorrect.get(i));
		}
		//h.function("matt");
		//System.out.println(s.incorrect.get(0));
//		System.out.println(s.incorrect.get(1));
//		System.out.println(s.incorrect.get(2));
//		System.out.println(s.incorrect.get(3));
		assertTrue((s.incorrect.get(0) == null));
		assertTrue(s.checkEndings("books"));
		assertTrue(s.checkEndings("baked"));
		assertTrue(s.checkEndings("cooker"));
		assertTrue(s.checkEndings("cooker's"));
		assertTrue(s.checkEndings("Absolutely"));
		assertTrue(s.checkEndings("shedding"));
		assertTrue(s.checkEndings("exercises"));
		//assertTrue(s.checkEndings("bake"));
		
		assertFalse(s.checkSpelling("books"));
		assertFalse(s.checkSpelling("baked"));
		assertFalse(s.checkSpelling("cooker"));
		assertTrue(s.checkSpelling("cooker's"));
		assertFalse(s.checkSpelling("Absolutely"));
		assertFalse(s.checkSpelling("shedding"));
		assertFalse(s.checkSpelling("exercises"));
		assertFalse(s.checkSpelling("bake"));
		
		
	}

}
