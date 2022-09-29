package com.cooksys.twitter_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDto { //ErrorDto is used to hold the Error info in the application
	
	private String message;
	
}
