package com.project.websocket.chat.entities;

public class UserImage {

	private int userId;
	
	private String imagePath;

	public UserImage(int userId, String imagePath) {
		this.userId = userId;
		this.imagePath = imagePath;
	};
	
	public UserImage() {}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	};	
}
