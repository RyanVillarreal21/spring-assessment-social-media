package com.cooksys.twitter_api.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.subentities.Credentials;
import com.cooksys.twitter_api.entities.subentities.Profile;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;
import com.cooksys.twitter_api.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final TweetMapper tweetMapper;
	private final ValidateService validateService;
	
	//This method is used to try and locate the user's username in the User repository
	private User findUser(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		
		//If the username is not in the repository, the method will throw an exception
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("No user found with the username: " + username);
		}
		
		//Returns the found user
		return optionalUser.get();
	}

	//Compares the inputted user credentials to the stored user credentials in the repository
	private void checkCredentialsDto(String username, UserRequestDto userRequestDto) {
		User verifyUser = findUser(username);
		User verifyCredentials = userMapper.userRequestDtoToEntity(userRequestDto);
		if (!verifyCredentials.getCredentials().equals(verifyUser.getCredentials())) {
			throw new NotAuthorizedException("Invalid credentials: " + userRequestDto);
		}
	}
	
	//Compares the inputted credentials to the stored user credentials in the repository
	private void checkCredentials(Credentials credentials) {
		User user = findUser(credentials.getUsername());
		
		if (!user.getCredentials().equals(credentials)) {
			throw new NotAuthorizedException("Invalid credentials: " + credentials);
		}
	}

	//This method is used to retrieve all users in the User repository
	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}

	//This method is used to retrieve a specific user account using a inputed username
	@Override
	public UserResponseDto getUser(String username) {
		User userToFind = findUser(username);
		return userMapper.entityToDto(userToFind);
	}

	//This method is used to create a User in the application
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		//Converts the RequestDto to an entity
		User userToSave = userMapper.userRequestDtoToEntity(userRequestDto);
		
		//The if statement is used to verify that the profile, credentials, username, password, and email fields are not null
		//If any of the fields are null, a BadRequestException is thrown
		if (userToSave.getProfile() == null || userToSave.getCredentials() == null
				|| userToSave.getCredentials().getUsername() == null
				|| userToSave.getCredentials().getPassword() == null || userToSave.getProfile().getEmail() == null) {
			throw new BadRequestException(
					"Username, password, or email were left blank. Please fill all required fields.");
		}
		
		//Validates that the username exists
		boolean userExist = validateService.usernameExists(userToSave.getCredentials().getUsername());
		
		//Validates that the username is available
		boolean userAvailable = validateService.usernameAvailable(userToSave.getCredentials().getUsername());
		
		//The if statement is used to check that if the userAvailable is false, a BadRequestException is thrown
		//Else if the username exists previously and was deleted, the user will be reactivated
		if (!userAvailable) {
			throw new BadRequestException("User already exists");
		} else if (userExist) {
			User recreateUser = userRepository.findByCredentialsUsername(userToSave.getCredentials().getUsername()).get();
			recreateUser.setDeleted(false);
			return userMapper.entityToDto(userRepository.saveAndFlush(recreateUser));
		}
		
		//Returns newly created/reactivated user
		return userMapper.entityToDto(userRepository.saveAndFlush(userToSave));
	}
	
	//This method is used to delete a user from the User repository
	@Override
	public UserResponseDto deleteUser(String username, Credentials credentials) {
		User userToDelete = findUser(username);
		
		checkCredentials(credentials);

		userToDelete.setDeleted(true);
		return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
	}

	//This method is used to update any of the fields in the profile of the user
	@Override
	public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
		User userToUpdate = findUser(username);
		User updates = userMapper.userRequestDtoToEntity(userRequestDto);

		if (updates.getProfile() == null || updates.getCredentials() == null) {
			throw new BadRequestException("Username, password, or email were left blank. Please fill all required fields.");
		}
		
		checkCredentialsDto(username, userRequestDto);
		
		Profile profile = userToUpdate.getProfile();
		
		if (updates.getProfile().getEmail() != null) {
			profile.setEmail(updates.getProfile().getEmail());
		}
		if (updates.getProfile().getFirstName() != null) {
			profile.setFirstName(updates.getProfile().getFirstName());
		}
		if (updates.getProfile().getLastName() != null) {
			profile.setLastName(updates.getProfile().getLastName());
		}
		if (updates.getProfile().getPhone() != null) {
			profile.setPhone(updates.getProfile().getPhone());
		}
		
		userToUpdate.setProfile(profile);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	//This method is used when a user follows another user and adds them to their following list
	@Override
	public void follow(String username, Credentials credentials) {
		checkCredentials(credentials);
		User userToFollow = findUser(username);
		User follower = findUser(credentials.getUsername());
		
		List<User> following = follower.getFollowing();
		if (following.contains(userToFollow)) {
			throw new BadRequestException("Already following the user!");
		} else {
			following.add(userToFollow);
		}
		
		follower.setFollowing(following);
		
		userRepository.saveAndFlush(follower);
	}

	//This method is used when a user unfollows another user and removes them from their following list
	@Override
	public void unfollow(String username, Credentials credentials) {
		checkCredentials(credentials);
		User userToUnfollow = findUser(username);
		User unfollower = findUser(credentials.getUsername());
		
		List<User> following = unfollower.getFollowing();
		if (following.contains(userToUnfollow)) {
			following.remove(userToUnfollow);
		} else {
			throw new BadRequestException("Not following the user!");
		}
		
		unfollower.setFollowing(following);
		
		userRepository.saveAndFlush(unfollower);
	}

	//This method is used to pull up the user's feed that holds all of their tweets and the tweets 
	//of the people they are following
	@Override
	public List<TweetResponseDto> feed(String username) {
		User user = findUser(username);
		List<Tweet> feed = user.getTweets();
		for (User follower : user.getFollowers()) {
			feed.addAll(follower.getTweets());
		}
		Collections.sort(feed);
		Collections.reverse(feed);
		return tweetMapper.entitiesToDtos(feed);
	}

	//This method is used to retrieve all the tweets the user has posted
	@Override
	public List<TweetResponseDto> tweets(String username) {
		User user = findUser(username);
		List<Tweet> tweets = user.getTweets();
		Collections.sort(tweets);
		Collections.reverse(tweets);
		return tweetMapper.entitiesToDtos(tweets);
	}

	//This method is used to retrieve all of the tweets the user is mentioned in
	@Override
	public List<TweetResponseDto> mentions(String username) {
		User user = findUser(username);
		List<Tweet> mentions = user.getMentions();
		Collections.sort(mentions);
		Collections.reverse(mentions);
		return tweetMapper.entitiesToDtos(mentions);
	}

	//This method is used to retrieve the list of users following the inputted username
	@Override
	public List<UserResponseDto> followers(String username) {
		User user = findUser(username);
		return userMapper.entitiesToDtos(user.getFollowers());
	}

	//This method is used to retrieve the list of users that the inputted username is following
	@Override
	public List<UserResponseDto> following(String username) {
		User user = findUser(username);
		return userMapper.entitiesToDtos(user.getFollowing());
	}

}
