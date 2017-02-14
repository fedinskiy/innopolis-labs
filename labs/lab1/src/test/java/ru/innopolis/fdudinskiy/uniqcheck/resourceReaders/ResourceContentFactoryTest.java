package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.helpers.TestBase;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.FILE;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.URL;

/**
 * Created by fedinskiy on 14.02.17.
 */
class ResourceContentFactoryTest extends TestBase{
	@Test
	void createChecker() throws WrongResourceException, IllegalSymbolsException, IOException {
	assertEquals(FileContent.class,
			ResourceContentFactory.createChecker(resources().getResource(FILE,VALID))
					.getClass());
	assertEquals(URLContent.class,
			ResourceContentFactory.createChecker(resources().getResource(URL,VALID))
					.getClass());
	}
	@Test
	void  createCheckerNegative(){
		final String WRONG_RESOURCE_PATH=":::::";
		assertThrows(WrongResourceException.class,()->ResourceContentFactory
				.createChecker(WRONG_RESOURCE_PATH));
	}
	
}