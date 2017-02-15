package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.helpers.TestBase;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.FileType.VALID;
import static ru.innopolis.fdudinskiy.helpers.ResourceHelper.ResourceType.FILE;

/**
 * Created by fedinskiy on 14.02.17.
 */

class FileContentTest extends TestBase{
	File mockFile;
	 
	@BeforeEach
	void createMock(){
		mockFile=null;
		mockFile=mock(File.class);
		when(mockFile.getAbsolutePath()).thenReturn("");
		when(mockFile.getPath()).thenReturn("");
		when(mockFile.exists()).thenReturn(true);
		when(mockFile.isFile()).thenReturn(true);
		when(mockFile.canRead()).thenReturn(true);
		when(mockFile.length()).thenReturn((long) 10);
	 }
	 
		 @Test
		void createFileResource() throws Exception {
			assertNotNull(new FileContent(new File(resources().getResource(FILE, VALID))));
		}
	
	
	@Disabled
	@Test
	/**
	 * Тест, проверяющий правильную обработку файлов большого размера.
	 * Вызывает у меня ошибку превышения доступной памяти, поэтому Disabled до тех пор,
	 * пока его не смогут прогнать на машине с 8+ гигами оперативы
	 */
	void createFileResourceLargeFile() throws WrongResourceException, IllegalSymbolsException, IOException {
		long bigButOkay= (((long)Integer.MAX_VALUE)*2)-1;
		when(mockFile.length()).thenReturn(bigButOkay);
		assertNotNull(new FileContent(mockFile));
	}
	
	@Test
	void createFileResourceNegativeNotExists(){
		when(mockFile.exists()).thenReturn(false);
		assertThrows(WrongResourceException.class,()->new FileContent(mockFile));
	 }
	 
	@Test
	void createFileResourceNegativeNotFile(){
		when(mockFile.isFile()).thenReturn(false);
		assertThrows(WrongResourceException.class,()->new FileContent(mockFile));
	}
	
	@Test
	void createFileResourceNegativeUnreadable(){
		when(mockFile.canRead()).thenReturn(false);
		assertThrows(WrongResourceException.class,()->new FileContent(mockFile));
	}
	
	@Test
	void createFileResourceNegativeTooLarge(){
		long biggerThanOkay= (((long)Integer.MAX_VALUE)*2);
		when(mockFile.length()).thenReturn(biggerThanOkay);
		assertThrows(WrongResourceException.class,()->new FileContent(mockFile));
	}
	
}