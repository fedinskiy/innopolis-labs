package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceChecker;
import ru.innopolis.fdudinskiy.uniqcheck.threads.ResourceCheckerCheckTread;
import ru.innopolis.fdudinskiy.uniqcheck.threads.ResourceCheckerCreatingThread;

import java.io.IOException;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class MultiThreadApplication {
	
	private ResourceChecker[] checkers;
	private final int resourceQuantity;
	private WordsStore store;
	
	public MultiThreadApplication(int resourceQuantity) {
		this.resourceQuantity=resourceQuantity;
	}
	
	public static void main(String[] args) throws WrongResourceException, IllegalSymbolsException, IOException {
		MultiThreadApplication app = new MultiThreadApplication(args.length);
		app.initStore(args);
		app.checkStore();
	}
	
	private void checkStore() {
			ResourceCheckerCheckTread[] checkerThreads = new
					ResourceCheckerCheckTread[resourceQuantity];
		for(int i = 0; i< resourceQuantity; ++i) {
			checkerThreads[i] = new ResourceCheckerCheckTread(checkers[i],store);
			checkerThreads[i].start();
		}
		
		for (int i = 0; i< resourceQuantity; ++i){
			try {
				checkerThreads[i].join();
				checkers[i]=checkerThreads[i].getChecker();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initStore(String[] args) {
		checkers = new ResourceChecker[resourceQuantity];
		ResourceCheckerCreatingThread[] creatingThreads = new
				ResourceCheckerCreatingThread[resourceQuantity];
		int storeSize=0;
		
		if (resourceQuantity < 1) {
			System.out.println("Не указано ни одного пути к файлу!");
			return;
		}
		
		for(int i = 0; i< resourceQuantity; ++i) {
			String path = args[i];
			System.out.println("Проверка ресурса " + path);
			creatingThreads[i] = new ResourceCheckerCreatingThread(path);
			creatingThreads[i].start();
		}
		
		for (int i = 0; i< resourceQuantity; ++i){
			try {
				creatingThreads[i].join();
				checkers[i]=creatingThreads[i].getChecker();
				storeSize+= checkers[i].getSize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Size is "+storeSize);
		store=new WordsStore(storeSize);
	}
}
