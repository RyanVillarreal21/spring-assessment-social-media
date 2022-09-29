package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto { //TweetResponseDto is used as the output for Tweets in the application
	
	private Long id;
	
	private UserResponseDto author;
	
	private Timestamp posted;
	
	private String content;
	
	private TweetResponseDto inReplyTo;
	
	private TweetResponseDto repostOf;
}
