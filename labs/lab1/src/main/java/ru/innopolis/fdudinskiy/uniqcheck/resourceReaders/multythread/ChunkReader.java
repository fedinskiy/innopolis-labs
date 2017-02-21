package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.multythread;

import ru.innopolis.fdudinskiy.uniqcheck.resourceReaders.ResourceContent;

import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by fedinskiy on 15.02.17.
 */
public class ChunkReader implements Callable {
	private String[] offcut;
	private final MappedByteBuffer map;
	private final ResourceContent target;
	
	public ChunkReader(MappedByteBuffer map, ResourceContent target) {
		super();
		offcut = new String[2];
		this.map = map;
		this.target = target;
	}
	
	public String getOffcuts() {
		return offcut[0] + " " + offcut[1];
	}
	
	@Override
	public Object call() throws Exception {
		return readContent();
	}
	
	public ChunkReader readContent() throws CharacterCodingException {
		final CharBuffer buffer;
		final Scanner scanner;
		String line;
		
		map.load();
		//System.out.println(map.capacity()+" !");
		//System.out.println(map.toString());
		buffer = StandardCharsets.UTF_8.newDecoder().decode(map);
		scanner = new Scanner(buffer);
		
		offcut[0] = scanner.nextLine();
		System.out.println(offcut[0]);
		System.out.println("================");
		if (scanner.hasNextLine()) {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if(!scanner.hasNextLine()) {
					offcut[1] = line;
				}else{
					System.out.println(line);
				}
				
				//target.addLine(line);
			}
			System.out.println("================");
			System.out.println(offcut[1]);
		} else {
			offcut[0] = buffer.toString();
		}
		return this;
	}
}
