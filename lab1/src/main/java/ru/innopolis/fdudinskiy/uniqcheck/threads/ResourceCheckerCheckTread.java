package ru.innopolis.fdudinskiy.uniqcheck.threads;

import ru.innopolis.fdudinskiy.uniqcheck.WordsStore;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceChecker;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class ResourceCheckerCheckTread extends Thread{
	private ResourceChecker checker;
	private WordsStore store;
	private WordAlreаdyAddedException processingException =null;
	
	public ResourceCheckerCheckTread(ResourceChecker checker, WordsStore store) {
		this.checker = checker;
		this.store = store;
	}
	
	@Override
	public void run() {
		try {
			checker.checkForRepeats(store);
		} catch (WordAlreаdyAddedException e) {
			processingException=e;
		}
	}
	
	public ResourceChecker getChecker() throws Exception {
		if(null!=processingException){
			throw processingException;
		}
		return checker;
	}
}
