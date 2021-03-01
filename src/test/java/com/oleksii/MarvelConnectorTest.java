package com.oleksii;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarvelConnectorTest {

	MarvelConnector marvelConnector;	
	
	@BeforeEach
	void setUp() throws Exception {
		marvelConnector = new MarvelConnector();
	}
	

	@AfterEach
	void tearDown() throws Exception {
		marvelConnector = null;
	}
	
	@Test
	void testCharacter() {
		
		int id = 123;
		String description = "Simple description";
		String name = "Santa Claus";
		String thumbnailPath = "http://localhost";
		String thumbnailExtension = "png";
		
		Map charactersStorage = buildCharacterStorage(id, description, thumbnailPath, thumbnailExtension);
	
	    MarvelCharacter character = (MarvelCharacter) marvelConnector.character(charactersStorage);	
	    Thumbnail thumbnail = character.thumbnail();		    
	    
	    assertEquals(id, character.id());
	    assertEquals(description, character.description());
	    assertEquals(name, character.name());		    
	    assertEquals(thumbnailPath, thumbnail.path());
	    assertEquals(thumbnailExtension, thumbnail.extention());
	}
	
	@Test
	void testCharacterWithExpectedNullPointerException() {	
		
		int id = 123;
		String description = "Simple description";
		String name = "Santa Claus";
		String thumbnailPath = null;
		String thumbnailExtension = "png";
		
		Map charactersStorage = buildCharacterStorage(id, description, thumbnailPath, thumbnailExtension);
			 
		  Assertions.assertThrows(NullPointerException.class, () -> {
			  MarvelCharacter character = (MarvelCharacter) marvelConnector.character(charactersStorage);	
		  });		 
		
	}

	@Test
	void testFindTotalCharacters() {
		int expectedTotal = 1020;
			
		assertEquals(expectedTotal, marvelConnector.findTotalCharacters(createJsonWithTotal(expectedTotal)));
	}
	
	@Test
	void testFindTotalCharactersWithZeroValue() {
		int expectedTotal = 0;
		MarvelConnector marvelConnector = new MarvelConnector();		
		assertEquals(expectedTotal, marvelConnector.findTotalCharacters(createJsonWithTotal(expectedTotal)));
	}
	
	protected Map buildCharacterStorage(int id, String description, String thumbnailPath, String thumbnailExtension) {
		Map charactersStorage = new HashMap<>();
		charactersStorage.put("id", id); 
	    charactersStorage.put("description", description); 
	    charactersStorage.put("name", "Santa Claus"); 
	    charactersStorage.put("thumbnailPath", thumbnailPath);
	    charactersStorage.put("thumbnailExtension", thumbnailExtension);
		return charactersStorage;
	}	
	
	private String createJsonWithTotal(int total) {
		return "{\"id\":1010718,\"desc\":\"\",\"name\":\"Asgardian\","
				+ "\"thumbnail\":{\"thumbnailPath\":\"http://localhost\",\"thumbnailExtention\":\"jpg\"}, "
				+ "\"total\":" + total + "}";
	}

}
