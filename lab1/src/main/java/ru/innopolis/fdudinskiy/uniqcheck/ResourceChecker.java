package ru.innopolis.fdudinskiy.uniqcheck;

import java.io.File;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class ResourceChecker {
	private File resource;
	public ResourceChecker(String path){
		if (null==path){
			throw new IllegalArgumentException("Не передан путь!");
		}
		resource = new File(path);
		if (!resource.exists()){
			throw new IllegalArgumentException("Файл "+path+" не существует");
		}
		if (!resource.isFile()){
			throw new IllegalArgumentException("По пути "+path+" расположена папка!");
		}
		if (!resource.canRead()) {
			throw new IllegalArgumentException("Невозможно прочесть файл " + path + " !");
		}
	}

	public String checkForRepeats(WordsStore store){
		String wordForCheck=""; //TODO написать получение
		try {
			store.addNewWord(wordForCheck);
		} catch (IllegalArgumentException ex){
			return "Слово "+wordForCheck+"встречается дважды";
		}
		return "";
	}
}
