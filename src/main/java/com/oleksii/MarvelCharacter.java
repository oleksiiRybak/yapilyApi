package com.oleksii;

import java.util.Objects;

public class MarvelCharacter implements Character {
	
	Integer id = 0; 
	String description = ""; 
	String name = ""; 	
	Thumbnail thumbnail;
	

	public MarvelCharacter(Integer id, String description, String name, String thumbnailPath, String thumbnailExtention) {
		
			this.id = Objects.requireNonNull(id);
			this.description = Objects.requireNonNull(description);
			this.name = Objects.requireNonNull(name);
			Objects.requireNonNull(thumbnailPath);
			Objects.requireNonNull(thumbnailExtention);			
		
			constructThumbnail(Objects.requireNonNull(thumbnailPath), Objects.requireNonNull(thumbnailExtention));
	}

	private void constructThumbnail(String thumbnailPath, String thumbnailExtention) {
		thumbnail = new Thumbnail(thumbnailPath, thumbnailExtention);
		
	}
	
	public Integer id() {
		return id;
	}
	
	public String description() {
		return description;
	}
	
	public String name() {
		return name;
	}
	
	public Thumbnail thumbnail() {
		return this.thumbnail;
	}
	
}
