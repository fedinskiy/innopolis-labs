package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.helpers.TestBase;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.store.WordsStore;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.FILE;

/**
 * Created by fedinskiy on 13.02.17.
 */
class ResourceContentTest extends TestBase{
	
	
	@Test
	void addNewWordsToStore() throws WrongResourceException, IllegalSymbolsException, IOException, WordAlreаdyAddedException {
		ResourceContent resourceContent = getResourceContentValid();
		WordsStore store=new WordsStore(resourceContent.getSize());
		assertNotNull(resourceContent);
		assertTrue(resourceContent.addNewWordsToStore(store));
		assertThrows(WordAlreаdyAddedException.class,()->{
			resourceContent.addNewWordsToStore(store);
		});
	}
	
	@Test
	private ResourceContent getResourceContentValid() throws WrongResourceException, IOException, IllegalSymbolsException {
		return ResourceContentFactory.createChecker(resources()
					.getResource(FILE,
					VALID));
	}
	
}