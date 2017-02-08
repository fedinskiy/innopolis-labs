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
		WordsStore store;
		ResourceChecker[] checkers= new ResourceChecker[args.length];
		int storeSize=0;
		
		if (args.length < 1) {
			System.out.println("Не указано ни одного пути к файлу!");
			return;
		}
		for(int i=0; i<args.length;++i){
			String path = args[i];
			System.out.println("Проверка ресурса "+path);
			ResourceChecker checker = null;
			try {
				checker = ResourceChecker.createChecker(path);
				checkers[i]=checker;
				storeSize+=checker.getSize();//TODO сделать это потокобезпасным!
			} catch (WrongResourceException | IllegalSymbolsException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		store = new WordsStore(storeSize);
		for (ResourceChecker checker : checkers) {
			try {
				if(null!=checker) {
					checker.checkForRepeats(store);
				}
			} catch (WordAlreаdyAddedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
