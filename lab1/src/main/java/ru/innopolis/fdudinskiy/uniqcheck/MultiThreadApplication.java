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
	//private ResourceContent[] checkers;
	
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
		ResourceContent[] resources;
		
		app = new MultiThreadApplication(args.length);
		resources = app.getResourceContent(args);
		if ( null==resources||resources.length==0) {
			app.stopService();
			return;
		}
		
		if (app.putToStore(resources)) {
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
	
	private ResourceContent[] getResourceContent(String[] args) {
		ArrayList<CreateResourceOperation> creatingTasks;
		ArrayList<Future<ResourceContent>> creatingResults;
		ResourceContent[] checkers;
		
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
			CreateResourceOperation operation = new CreateResourceOperation
					(path);
			creatingTasks.add(operation);
			creatingResults.add(service.submit(operation));
		}
		
		for (int i = 0; i < resourceQuantity; ++i) {
			try {
				checkers[i] = creatingResults.get(i).get();
			} catch (Exception e) {
				stopService();
				System.out.println(e.getLocalizedMessage());
				return null;
			}
		}
		
		return checkers;
	}
	
	private boolean putToStore(ResourceContent[] resources) {
		boolean operationSuccess = true;
		final ArrayList<Future<Boolean>> checkingResults;
		final CheckResourceOperation[] checkerTasks;
		final WordsStore store;
		int totalNumberOfWords=0;
				
		checkingResults = new ArrayList<>(resourceQuantity);
		checkerTasks = new CheckResourceOperation[resourceQuantity];
		
		for (int i = 0; i < resourceQuantity; ++i) {
			totalNumberOfWords = resources[i].getSize();
		}
		System.out.println("Total number of words is " + totalNumberOfWords);
		
		store = new WordsStore(totalNumberOfWords);
		
		for (int i = 0; i < resourceQuantity; ++i) {
			CheckResourceOperation task= new CheckResourceOperation
					(resources[i], store);
			checkerTasks[i] = task;
			checkingResults.add(i, service.submit(task));
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
