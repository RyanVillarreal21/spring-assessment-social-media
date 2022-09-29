package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CredentialsDto { //CredentialsDto is used to hold the Credentials info in the application

	private String username;
	
	private String password;
	
}
