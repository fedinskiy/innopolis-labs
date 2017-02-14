package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fedinskiy on 14.02.17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FileInputStream.class)
class FileContentTest {
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
		 final String DEFAULT_ENCODING = "UTF-8";
		//BufferedReader(new InputStreamReader( new FileInputStream
		 FileInputStream mockFileStream=mock(FileInputStream.class);
		 InputStreamReader mockReader=mock(InputStreamReader.class);
		 BufferedReader mockBuffered=mock(BufferedReader.class);
		 when(mockBuffered.readLine()).thenReturn("строка").thenReturn("строка вторая").thenReturn(null);
		
		
		 PowerMockito.whenNew(FileInputStream.class).withArguments(mockFile).thenReturn(mockFileStream);
		 PowerMockito.whenNew(InputStreamReader.class).withArguments(mockFileStream,DEFAULT_ENCODING).thenReturn(mockReader);
		 PowerMockito.whenNew(BufferedReader.class).withArguments(mockReader).thenReturn(mockBuffered);
		 assertNotNull(Whitebox.invokeConstructor(FileContent.class,mockFile));
	}
	

	/**
	 * Тест, проверяющий правильную обработку файлов большого размера.
	 * Вызывает у меня ошибку превышения доступной памяти, поэтому Disabled до тех пор,
	 * пока его не смогут прогнать на машине с 8+ гигами оперативы
	 */
	@Disabled
	@Test
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