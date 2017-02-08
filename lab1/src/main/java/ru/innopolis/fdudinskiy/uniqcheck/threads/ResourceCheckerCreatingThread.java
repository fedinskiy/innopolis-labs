package ru.innopolis.fdudinskiy.uniqcheck.threads;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceChecker;

import java.io.IOException;

import static ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceChecker.*;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class ResourceCheckerCreatingThread extends Thread {
	private String path;
	private ResourceChecker checker;
	private Exception processingException =null;
	
	public ResourceCheckerCreatingThread(String path) {
		super();
		this.path = path;
	}
	
	@Override
	public void run() {
		System.out.println("Работает поток для пути "+path);
		try {
			checker= createChecker(path);//TODO прерывать все потоки
		} catch (WrongResourceException | IllegalSymbolsException | IOException e) {
			processingException = e;
		}
	}
	
	public ResourceChecker getChecker() throws Exception {
		if(null!=processingException){
			throw processingException;
		}
		return checker;
	}
}
