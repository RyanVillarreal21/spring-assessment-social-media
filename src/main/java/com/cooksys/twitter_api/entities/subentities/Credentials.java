package com.cooksys.twitter_api.entities.subentities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
}
