package com.oleksii;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.gson.Gson;

public class JsonParser {
	
	protected static JsonNode convertJsonStringToJsonRootNode(String jsonString) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode rNode;
		try {
			rNode = objectMapper.readTree(jsonString);
		} catch (JsonMappingException e) {			
			e.printStackTrace();
			rNode = objectMapper.createObjectNode();
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
			rNode = objectMapper.createObjectNode();
		}
		return rNode;
	}
		
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
	  
	  static void handleValue(Map charactersStorage, Object value) {
		    if (value instanceof JSONObject) {	    	
		        handleJSONObject(charactersStorage, (JSONObject) value);
		    } else if (value instanceof JSONArray) {
		        handleJSONArray(charactersStorage, (JSONArray) value);
		    } else {
		    	System.out.println(value);	    	
		    }
		}
		
		static void handleJSONObject(Map charactersStorage, JSONObject jsonObject) {		
		    jsonObject.keys().forEachRemaining(key -> {
		    	if("thumbnail".equals(key)) {
		    		Map thumbnailDetailsMap = new Gson().fromJson(jsonObject.get(key).toString(), HashMap.class);		    		    		
		    		charactersStorage.put("thumbnailPath", thumbnailDetailsMap.get("path"));
		    		charactersStorage.put("thumbnailExtension", thumbnailDetailsMap.get("extension"));
		    		return;
		    	}	
		    	if("name".equals(key) || "description".equals(key) || "id".equals(key)) {	    		
		    		charactersStorage.put(key, jsonObject.get(key) != null ? jsonObject.get(key) : "");  		
		    			return;    
		    	}	    	
				
		        Object value = jsonObject.get(key);	        
		        handleValue(charactersStorage, value);
		    });
		}
		
		static void handleJSONArray(Map charactersStorage, JSONArray jsonArray) {
		    jsonArray.iterator().forEachRemaining(element -> {
		        handleValue(charactersStorage, element);
		    });
		}
	
}
