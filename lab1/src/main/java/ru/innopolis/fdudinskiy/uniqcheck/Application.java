package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceChecker;

import java.io.IOException;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class Application {
	public static void main(String[] args) {
		WordsStore store = new WordsStore();
		if (args.length < 1) {
			System.out.println("Не указано ни одного пути к файлу!");
			return;
		}
		for (String path : args) {
			try {
				ResourceChecker checker = ResourceChecker.createChecker(path);
				checker.checkForRepeats(store);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WordAlreаdyAddedException e) {
				System.out.println(e.getMessage());
			} catch (IllegalSymbolsException e) {
				System.out.println(e.getMessage());
			} catch (WrongResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
