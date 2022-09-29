package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto { //UserRequestDto is used as the input for User info in the application

	private CredentialsDto credentials;
	
	private ProfileDto profile;
	
}
