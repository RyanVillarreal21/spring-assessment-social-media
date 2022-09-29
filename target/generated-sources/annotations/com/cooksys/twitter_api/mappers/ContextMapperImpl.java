package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.entities.subentities.Context;
import com.cooksys.twitter_api.entities.subentities.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T14:35:20-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class ContextMapperImpl implements ContextMapper {

    @Override
    public ContextDto entityToDto(Context subentity) {
        if ( subentity == null ) {
            return null;
        }

        ContextDto contextDto = new ContextDto();

        contextDto.setTarget( tweetToTweetResponseDto( subentity.getTarget() ) );
        contextDto.setBefore( tweetListToTweetResponseDtoList( subentity.getBefore() ) );
        contextDto.setAfter( tweetListToTweetResponseDtoList( subentity.getAfter() ) );

        return contextDto;
    }

    @Override
    public List<ContextDto> entitiesToDtos(List<Context> subentites) {
        if ( subentites == null ) {
            return null;
        }

        List<ContextDto> list = new ArrayList<ContextDto>( subentites.size() );
        for ( Context context : subentites ) {
            list.add( entityToDto( context ) );
        }

        return list;
    }

    protected ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setEmail( profile.getEmail() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }

    protected UserResponseDto userToUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setProfile( profileToProfileDto( user.getProfile() ) );
        userResponseDto.setJoined( user.getJoined() );

        return userResponseDto;
    }

    protected TweetResponseDto tweetToTweetResponseDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setAuthor( userToUserResponseDto( tweet.getAuthor() ) );
        tweetResponseDto.setPosted( tweet.getPosted() );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setInReplyTo( tweetToTweetResponseDto( tweet.getInReplyTo() ) );
        tweetResponseDto.setRepostOf( tweetToTweetResponseDto( tweet.getRepostOf() ) );

        return tweetResponseDto;
    }

    protected List<TweetResponseDto> tweetListToTweetResponseDtoList(List<Tweet> list) {
        if ( list == null ) {
            return null;
        }

        List<TweetResponseDto> list1 = new ArrayList<TweetResponseDto>( list.size() );
        for ( Tweet tweet : list ) {
            list1.add( tweetToTweetResponseDto( tweet ) );
        }

        return list1;
    }
}
