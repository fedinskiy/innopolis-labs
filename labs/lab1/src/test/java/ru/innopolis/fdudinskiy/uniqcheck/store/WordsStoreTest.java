package ru.innopolis.fdudinskiy.uniqcheck.store;

import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by fedinskiy on 13.02.17.
 */
class WordsStoreTest {
	
	@Test
	void addNewWordPositive() throws WordAlreаdyAddedException {
		final String firstWord = "Word";
		final String secondWord = "Word2";
		addWordsPositive(firstWord, secondWord);
	}
	@Test
	void addNewCyrillicWordPositive() throws WordAlreаdyAddedException {
		final String firstWord = "Словораз";
		final String secondWord = "Словодва";
		addWordsPositive(firstWord, secondWord);
	}
	
	private void addWordsPositive(String firstWord, String secondWord) throws WordAlreаdyAddedException {
		WordsStore wordsStore = new WordsStore(2);
		assertTrue(wordsStore.addNewWord(firstWord));
		assertTrue(wordsStore.addNewWord(secondWord));
	}
	
	@Test
	void resize() throws WordAlreаdyAddedException {
		final String firstWord = "Word";
		final String secondWord = "Word2";
		WordsStore wordsStore = new WordsStore(1);
		assertTrue(wordsStore.addNewWord(firstWord));
		assertTrue(wordsStore.addNewWord(secondWord));
	}
	
	@Test
	void addNewWordNegative() throws WordAlreаdyAddedException {
		final String firstWord = "Word";
		final String secondWord = firstWord;
		addWordsNegative(firstWord, secondWord);
	}
	
	@Test
	void addNewWordNegativeSummCyrillic() throws WordAlreаdyAddedException {
		String firstHalf = "Перваяполовинатогожеслова";
		String secondHalf = "втораяполовинаслова";
		String firstWord = new String(firstHalf).concat(secondHalf);
		String secondWord = new String(firstHalf).concat(secondHalf);
		addWordsNegative(firstWord, secondWord);
	}
	
	@Test
	void addNewWordNegativeSumm() throws WordAlreаdyAddedException {
		String firstHalf = "firstHalfOfTheSameWord";
		String secondHalf = "SecondHalfOfTheSameWord";
		String firstWord = new String(firstHalf).concat(secondHalf);
		String secondWord = new String(firstHalf).concat(secondHalf);
		addWordsNegative(firstWord, secondWord);
	}
	
	private void addWordsNegative(String firstWord, String secondWord) throws WordAlreаdyAddedException {
		WordsStore wordsStore = new WordsStore(2);
		wordsStore.addNewWord(firstWord);
		
		assertThrows(WordAlreаdyAddedException.class, () -> {
			wordsStore.addNewWord(secondWord);
		});
		assertTrue(wordsStore.isHasDoubles());
	}
}