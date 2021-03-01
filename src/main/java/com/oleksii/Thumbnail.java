package com.oleksii;

public class Thumbnail {
	
	String thumbnailPath = "";
	String thumbnailExtention = "";
	
	public Thumbnail(String thumbnailPath, String thumbnailExtention) {
		this.thumbnailExtention = thumbnailExtention;
		this.thumbnailPath = thumbnailPath;
	}
	
	String path() {
		return this.thumbnailPath;
	}
	
	String extention() {
		return this.thumbnailExtention;
	}

}
