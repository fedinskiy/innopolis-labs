package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import org.junit.jupiter.api.Test;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by fedinskiy on 14.02.17.
 */
class ResourceContentProtectedTest extends ResourceContent{
	public ResourceContentProtectedTest(){
		super("");
		
	}
	
	@Test
	void read() throws IOException, IllegalSymbolsException {
		BufferedReader reader = mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("строка").thenReturn("строка вторая").thenReturn(null);
		super.read(reader);
		assertEquals(3,this.getSize());
	}
	@Test
	void readNegative() throws IOException {
		BufferedReader reader = mock(BufferedReader.class);
		when(reader.readLine()).thenReturn("word1").thenReturn(null);
		assertThrows(IllegalSymbolsException.class,()->super.read(reader));
	}
}