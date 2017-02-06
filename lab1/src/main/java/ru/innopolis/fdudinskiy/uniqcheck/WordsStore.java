package ru.innopolis.fdudinskiy.uniqcheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class WordsStore {
	private List<String> words=new ArrayList<String>();

	public void addNewWord(String word) throws IllegalArgumentException{
		if (words.contains(word)) {
			throw new IllegalArgumentException("Такое слово уже существует!");
		}else{
			addWord(word);
		}
	}

	private void addWord(String word) {
		words.add(word);
	}
}
