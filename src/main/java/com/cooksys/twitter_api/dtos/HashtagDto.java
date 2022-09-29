package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto { //HastagDto is used to hold the Hashtag info in the application
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
}
