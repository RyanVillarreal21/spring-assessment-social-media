package com.cooksys.twitter_api.exceptions;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8011499701505794149L;

	private String message;
}
