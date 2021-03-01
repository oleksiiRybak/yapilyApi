package com.oleksii;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client; 
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.node.ObjectNode;
//Other imports are removed for brevity 
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class MarvelController {
	
	Connector marvelConnector = new MarvelConnector();
		
	@GetMapping(path="/characters", produces="application/json")	
	List<String> allMarvelCharacters() 
				 throws UnsupportedEncodingException, NoSuchAlgorithmException, 
				 JsonMappingException, JsonProcessingException {			
		
		List<String> recordingArr = marvelConnector.fetchAllRecords();
				
	  return recordingArr;
	}
		
	 @GetMapping(path="/characters/{id}", produces="application/json")
	 String one(@PathVariable String id) { 
		 Character marvelCharacter = marvelConnector.fetchRecordById(id);
		 Gson gson = new Gson();		 
		 String marvelCharacterJson = gson.toJson(marvelCharacter);

		 return marvelCharacterJson; 
	 }
	 
}
