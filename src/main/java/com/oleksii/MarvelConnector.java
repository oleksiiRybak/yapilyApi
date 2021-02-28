package com.oleksii;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.*;

public class MarvelConnector implements Connector {
	
	public MarvelConnector() {
		
	}
	
	public List<String> fetchAllRecords() {
		String BASE_URI = prepareAllCharactersApiUrl(0, ""); 
		String jsonString = fetchResult(BASE_URI);
		int totalCharacters = 300; //findTotalCharacters(jsonString);
		
		List<String> recordingArr = new ArrayList<>();
		
		for(int offset = 0; offset < totalCharacters; offset+=100) {
			String idBASE_URI = prepareAllCharactersApiUrl(offset, ""); 
			Response idResp = ClientBuilder.newClient().target(idBASE_URI).request(MediaType.APPLICATION_JSON).get();
			String jString = "";
			if (idResp.getStatus() == 200) {
				jString = idResp.readEntity(String.class);
				ObjectMapper objectMapper = new ObjectMapper();
				
				JsonNode rNode;
				try {
					rNode = objectMapper.readTree(jsonString);
				} catch (JsonMappingException e) {
					rNode = objectMapper.createObjectNode();
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					rNode = objectMapper.createObjectNode();
					e.printStackTrace();
				} 
				JsonParser.traverseAndFind(recordingArr, "id", rNode, 1);
			}
		}	
		
		return recordingArr;
	}

	private static String fetchResult(String BASE_URI) {
		Response resp = ClientBuilder.newClient().target(BASE_URI).request(MediaType.APPLICATION_JSON).get();
		String jsonString = "";
		if (resp.getStatus() == 200) {
			jsonString = resp.readEntity(String.class);
		}
		return jsonString;
	}
	
	void callMarvelApiService(String BASE_URI) {
		String jsonString = fetchResult(BASE_URI);
	}

	private static int findTotalCharacters(String jsonString) {
		
		List<String> total = new ArrayList<>();
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
		JsonParser.traverseAndFind(total, "total", rNode, 1);
		
		if(!total.isEmpty()) {	
			String totti = total.get(0);
			return Integer.parseInt(total.get(0));
		} else {
			return 0;
		}		
		
	}

	private static String prepareAllCharactersApiUrl(int offset, String id) {
		//Client client = ClientBuilder.newClient(); 
		String timespamp = "1";
		
		String apiPublicKey = "cd3d7c861e2b8cbaf32911efd20d4c10";
		String apiPrivateKey = "36a10c7cfa84b27cc49d314aaf3f69720af07269";
		
		String md5StrInput = timespamp + apiPrivateKey + apiPublicKey;
		String md5Hash = getMd5(md5StrInput);
		
		if(!id.isEmpty()) {
			id = "/" + id;
		}
						
		return  "http://gateway.marvel.com/v1/public/characters" + id + "?" + "offset=" + offset 
							+ "&limit=100" + "&ts=" + timespamp + "&apikey=" + apiPublicKey + "&hash=" + md5Hash;		
	}
	
	public static String getMd5(String input) { 
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            BigInteger no = new BigInteger(1, messageDigest); 
  
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        } catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }

	public Character fetchRecordById(String id) {
		String BASE_URI = prepareAllCharactersApiUrl(0, id); 
		String jsonString = fetchResult(BASE_URI); 	
				
		JSONObject jsonObject = new JSONObject(jsonString);
		
		List recordingArr = new ArrayList<>();
		Map charactersStorage = new HashMap<>();
		handleValue(charactersStorage, jsonObject);
		
		return character(charactersStorage);
	}
	
	private static Character character(Map charactersStorage) {
		return new MarvelCharacter((Integer)charactersStorage.get("id"), 
															   (String)charactersStorage.get("description"), 
															   (String)charactersStorage.get("name"), 
															   (String)charactersStorage.get("thumbnailPath"), 
															   (String)charactersStorage.get("thumbnailExtension"));		
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
	    		//Object thumbnailDetails = jsonObject.get("thumbnail");	    		
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
