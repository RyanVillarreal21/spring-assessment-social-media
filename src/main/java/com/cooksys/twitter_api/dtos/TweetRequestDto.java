package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto { //TweetRequestDto is used as the input for Tweets in the application
	
	private String content;
	private CredentialsDto credentials;
	
	
	
}
