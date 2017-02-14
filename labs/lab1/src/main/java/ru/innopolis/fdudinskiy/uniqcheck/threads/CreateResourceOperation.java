package ru.innopolis.fdudinskiy.uniqcheck.threads;

import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceContent;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceContentFactory;

import java.util.concurrent.Callable;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class CreateResourceOperation implements Callable {
	private String path;
	
	/**
	 *
	 * @param path — путь к ресурсу
	 */
	public CreateResourceOperation(String path) {
		super();
		this.path = path;
	}
	
	/**
	 *
	 * @return ResourceContent — полученное содержимое ресурса
	 * @throws Exception
	 */
	@Override
	public ResourceContent call() throws Exception {
		return ResourceContentFactory.createChecker(path);
	}
}
