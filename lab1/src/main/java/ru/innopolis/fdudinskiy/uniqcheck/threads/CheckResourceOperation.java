package ru.innopolis.fdudinskiy.uniqcheck.threads;

import ru.innopolis.fdudinskiy.uniqcheck.store.WordsStore;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceContent;
import java.util.concurrent.Callable;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class CheckResourceOperation implements Callable {
	private ResourceContent checker;
	private WordsStore store;
	
	public CheckResourceOperation(ResourceContent checker, WordsStore store) {
		this.checker = checker;
		this.store = store;
	}
	
	@Override
	public Boolean call() throws Exception {
		return checker.addNewWordsToStore(store);
	}
	
}
