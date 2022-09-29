package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto { //ProfileDto is used to hold the Profile info in the application

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
}
