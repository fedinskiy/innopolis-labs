package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.helpers.ResourceHelper;
import ru.innopolis.fdudinskiy.helpers.TestBase;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.URL;

/**
 * Created by fedinskiy on 15.02.17.
 */
class URLContentTest extends TestBase{
	
	@Test
	void createURLResource() throws Exception {
		assertNotNull(new URLContent(new URL(resources().getResource(URL, VALID))));
	}
	
}