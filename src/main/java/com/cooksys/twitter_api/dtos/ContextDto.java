package com.cooksys.twitter_api.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto { //ContextDto is used to hold the Context info in the application

	private TweetResponseDto target;
	
	private List<TweetResponseDto> before;
	
	private List<TweetResponseDto> after;
	
}
