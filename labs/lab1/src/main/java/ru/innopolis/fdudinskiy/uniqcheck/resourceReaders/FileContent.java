package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.ResourceTooLargeException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;
import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.multythread.ChunkReader;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class FileContent extends ResourceContent {
	/**
	 * @param resource — файл, содержимое которого будет прочитано
	 * @throws WrongResourceException
	 * @throws IOException
	 * @throws IllegalSymbolsException
	 */
	public FileContent(File resource)
			throws WrongResourceException,
			IOException, IllegalSymbolsException {
		super(resource.getAbsolutePath());
		final String DEFAULT_ENCODING = "UTF-8";
		
		String filePath = resource.getAbsolutePath();
		
		if (!resource.exists()) {
			throw new WrongResourceException("Файл " + filePath
					+ " не существует");
		}
		
		if (!resource.isFile()) {
			throw new WrongResourceException("По пути " + filePath
					+ " расположена папка!");
		}
		
		if (!resource.canRead()) {
			throw new WrongResourceException("Невозможно прочесть файл "
					+ filePath + " !");
		}
		
		try {
			super.prepareStore(resource.length());
		} catch (ResourceTooLargeException ex) {
			throw new WrongResourceException("Файл " + filePath
					+ " слишком велик!", ex);
		}
		long length = resource.length();
		System.out.println(length);
		long chunkSize = length / 4;
		System.out.println(chunkSize);
		switch ((int) (chunkSize % 4)) {
			case 1:
				++chunkSize;
			case 2:
				++chunkSize;
			case 3:
				++chunkSize;
		}
		System.out.println(chunkSize);
		long currentPosition = 0;
		int part = 0;
		
		String offcuts = "";
		ChunkReader chunkReader=null;
		MappedByteBuffer[] map = new MappedByteBuffer[3];
		final FileChannel fileChannel = new RandomAccessFile(resource, "r").getChannel();
		MappedByteBuffer r;
		while (currentPosition <= length) {
			chunkSize=((currentPosition + chunkSize+1)>resource.length())
					?resource.length()-currentPosition
					:chunkSize;
			System.out.println("Run "+(part+1));
			if(part>2) {
				System.out.println("ACHTUNG!");
				System.out.println("chnkSize " + chunkSize);
				System.out.println("cposition1 " + currentPosition);
				r=null;
			}else{
				System.out.println("chunkSize " + chunkSize);
				System.out.println("currentposition1 " + currentPosition);
				r = fileChannel.map(FileChannel.MapMode.READ_ONLY,
						currentPosition,
						chunkSize);
				map[part] = r;
				currentPosition = (currentPosition + chunkSize+1);
				chunkReader = new ChunkReader(map[part], null);
				chunkReader = chunkReader.readContent();
			}
			
			
			
			System.out.println("currentposition2 " + currentPosition);
			offcuts = offcuts.concat(chunkReader.getOffcuts());
			part++;
		}
		
		
		System.out.println(offcuts);
		try (
				FileInputStream fis = new FileInputStream(resource);
				InputStreamReader isr = new InputStreamReader(fis, DEFAULT_ENCODING);
				BufferedReader in = new BufferedReader(isr)) {
			this.read(in);
			in.close();
		}
	}
	
	
}
