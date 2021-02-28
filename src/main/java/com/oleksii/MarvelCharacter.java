package com.oleksii;

import java.util.Objects;

public class MarvelCharacter implements Character {
	
	Integer id = 0; 
	String desc = ""; 
	String name = ""; 	
	Thumbnail thumbnail;
	

	public MarvelCharacter(Integer id, String desc, String name, String thumbnailPath, String thumbnailExtention) {
		
			this.id = Objects.requireNonNull(id);
			this.desc = Objects.requireNonNull(desc);
			this.name = Objects.requireNonNull(name);
			Objects.requireNonNull(thumbnailPath);
			Objects.requireNonNull(thumbnailExtention);			
		
			constructThumbnail(Objects.requireNonNull(thumbnailPath), Objects.requireNonNull(thumbnailExtention));
	}


	private void constructThumbnail(String thumbnailPath, String thumbnailExtention) {
		thumbnail = new Thumbnail(thumbnailPath, thumbnailExtention);
		
	}
	
}
