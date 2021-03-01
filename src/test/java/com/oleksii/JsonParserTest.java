package com.oleksii;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

class JsonParserTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testTraverseAndFind() {
		List<String> IDs = new ArrayList<>();
		JsonNode rNode = JsonParser.convertJsonStringToJsonRootNode(createJsonWithId()); 
		JsonParser.traverseAndFind(IDs, "id", rNode, 1);
		
		assertEquals(Integer.parseInt(IDs.get(0)), 100);		
	}
	
	private String createJsonWithId() {
		return "{\"id\":100,\"desc\":\"\",\"name\":\"Asgardian\","
				+ "\"thumbnailPath\":\"http://localhost\",\"thumbnailExtention\":\"jpg\", \"total\": 1493}"
				+ "{\"id\":101,\"desc\":\"\",\"name\":\"New Moon\","
				+ "\"thumbnailPath\":\"http://localhost\",\"thumbnailExtention\":\"png\", \"total\": 1500}";
	}
	
	

}
