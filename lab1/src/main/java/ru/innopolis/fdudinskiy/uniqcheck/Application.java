package ru.innopolis.fdudinskiy.uniqcheck;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class Application {
	public static void main(String[] args) {
		WordsStore store=new WordsStore();
		if(args.length<1){
			//TODO материмся
		}
		ResourceChecker checker = new ResourceChecker(args[0]);

		checker.checkForRepeats(store);
	}
}
