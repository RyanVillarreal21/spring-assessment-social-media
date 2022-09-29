package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto { //UserResponseDto is used as the output for User info in the application
	
	private String username;
	
	private ProfileDto profile;
	
	private Timestamp joined;
	
}
