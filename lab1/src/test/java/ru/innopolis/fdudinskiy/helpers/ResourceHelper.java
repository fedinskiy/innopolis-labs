package ru.innopolis.fdudinskiy.helpers;

/**
 * Created by fedinskiy on 13.02.17.
 */
public class ResourceHelper {
	private final String INVALID_TEXT_FN="invalidtext";
	private final String REPETABLE_TEXT_FN="cicero";
	private final String VALID_TEXT_FN="unrepetable";
	private final String VALID2_TEXT_FN="заяц";
	private final String HTTP_PREFIX="https://raw.githubusercontent"
			+".com/jaowl/innopolis-labs/master/lab1/src/main/resources";
	private final String PATH_PREFIX="src/main/resources/";
	
	public String getResource(ResourceType type, FileType fileType){
		String prefix=null;
		String path = null;
		switch (type) {
			case URL:
				prefix=HTTP_PREFIX;
				break;
			case FILE:
				prefix=PATH_PREFIX;
				break;
		}
		switch (fileType) {
			case VALID:
				path=VALID_TEXT_FN;
				break;
			case VALID2:
				path=VALID2_TEXT_FN;
				break;
			case REPETABLE:
				path=REPETABLE_TEXT_FN;
				break;
			case INVALID:
				path=INVALID_TEXT_FN;
				break;
		}
		
		return prefix+path;
	}
	
	public static enum ResourceType {
		URL, FILE
	}
	public static enum FileType {
		VALID, REPETABLE, INVALID, VALID2
	}
}
