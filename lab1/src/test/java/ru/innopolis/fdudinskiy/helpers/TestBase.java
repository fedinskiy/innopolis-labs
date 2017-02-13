package ru.innopolis.fdudinskiy.helpers;

/**
 * Created by fedinskiy on 13.02.17.
 */
public class TestBase {
	private final ResourceHelper resourceHelper;
	public TestBase(){
		resourceHelper =new ResourceHelper();
	}
	
	public ResourceHelper resources(){
		return resourceHelper;
	}
	
	
}
