package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceContent;
import ru.innopolis.fdudinskiy.uniqcheck.store.WordsStore;
import ru.innopolis.fdudinskiy.uniqcheck.threads.CheckResourceOperation;
import ru.innopolis.fdudinskiy.uniqcheck.threads.CreateResourceOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class MultiThreadApplication {
	private final ExecutorService service;
	private final int resourceQuantity;
	private ResourceContent[] checkers;
	
	/**
	 * @param resourceQuantity — число ресурсов
	 */
	public MultiThreadApplication(int resourceQuantity) {
		this.resourceQuantity = resourceQuantity;
		service = Executors.newFixedThreadPool(resourceQuantity);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		stopService();
	}
	
	public static void main(String[] args) throws WrongResourceException,
			IllegalSymbolsException, IOException {
		
		MultiThreadApplication app;
		WordsStore store;
		
		app = new MultiThreadApplication(args.length);
		store = app.initStore(args);
		
		if (null == store) {
			app.stopService();
			return;
		}
		
		if (app.checkStore(store)) {
			System.out.println("Добавление слов прошло успешно, ресурсы " +
					"содержат только уникальные слова");
		}
		
		app.stopService();
	}
	
	private void stopService() {
		if(null!=service) {
			service.shutdown();
			service.shutdownNow();
		}
	}
	
	private WordsStore initStore(String[] args) {
		int totalNumberOfWords = 0;
		ArrayList<CreateResourceOperation> creatingTasks;
		ArrayList<Future<ResourceContent>> creatingResults;
		
		if (resourceQuantity < 1) {
			System.out.println("Не указано ни одного пути к файлу!");
			return null;
		}
		
		creatingTasks = new ArrayList<>(resourceQuantity);
		creatingResults = new ArrayList<>(resourceQuantity);
		checkers = new ResourceContent[resourceQuantity];
		
		for (int i = 0; i < resourceQuantity; ++i) {
			String path = args[i];
			System.out.println("Проверка ресурса " + path);
			creatingTasks.add(i, new CreateResourceOperation(path));
			creatingResults.add(i, service.submit(creatingTasks.get(i)));
		}
		
		for (int i = 0; i < resourceQuantity; ++i) {
			try {
				checkers[i] = creatingResults.get(i).get();
				totalNumberOfWords += checkers[i].getSize();
			} catch (Exception e) {
				stopService();
				System.out.println(e.getLocalizedMessage());
				return null;
			}
		}
		
		System.out.println("Total number of words is " + totalNumberOfWords);
		
		return new WordsStore(totalNumberOfWords);
	}
	
	private boolean checkStore(WordsStore store) {
		boolean operationSuccess = true;
		final ArrayList<Future<Boolean>> checkingResults;
		final CheckResourceOperation[] checkerTasks;
		
		checkingResults = new ArrayList<>(resourceQuantity);
		checkerTasks = new CheckResourceOperation[resourceQuantity];
		
		for (int i = 0; i < resourceQuantity; ++i) {
			checkerTasks[i] = new CheckResourceOperation(checkers[i], store);
			checkingResults.add(i, service.submit(checkerTasks[i]));
		}
		
		for (int i = 0; i < resourceQuantity; ++i) {
			try {
				operationSuccess = checkingResults.get(i).get()&&operationSuccess;
			} catch (ExecutionException e) {
				stopService();
				System.out.println(e.getMessage());
				return false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return operationSuccess;
	}
}
