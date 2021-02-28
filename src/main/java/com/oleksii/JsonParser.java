package com.oleksii;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JsonParser {
		
	public static void traverseAndFind(List<String> recordingArr, String searchedField, JsonNode node, int level) {
	      if (node.getNodeType() == JsonNodeType.ARRAY) {
	          traverseArray(recordingArr, searchedField, node, level);
	      } else if (node.getNodeType() == JsonNodeType.OBJECT) {
	          traverseObject(recordingArr, searchedField, node, level);
	      } else {
	         throw new RuntimeException("Not yet implemented");
	      }
	  }

	  private static void traverseObject(List<String> recordingArr, String searchedField, JsonNode node, int level) {
	      node.fieldNames().forEachRemaining((String fieldName) -> {
	    	  JsonNode childNode;
	    	  if(searchedField.equals(fieldName)) {
	    		  childNode = node.get(fieldName);
	    		  recordingArr.add(childNode.asText());
	    	  } else {
	    		  childNode = node.get(fieldName);
	    	  }
	          	          
	          if (traversable(childNode)) {
	        	  traverseAndFind(recordingArr, searchedField, childNode, level + 1);
	          }
	      });
	  }

	  private static void traverseArray(List<String> recordingArr, String searchedField, JsonNode node, int level) {
	      for (JsonNode jsonArrayNode : node) {	          
	          if (traversable(jsonArrayNode)) {
	        	  traverseAndFind(recordingArr, searchedField, jsonArrayNode, level + 1);
	          }
	      }
	  }

	  private static boolean traversable(JsonNode node) {
	      return node.getNodeType() == JsonNodeType.OBJECT ||
	              node.getNodeType() == JsonNodeType.ARRAY;
	  }
	
}
