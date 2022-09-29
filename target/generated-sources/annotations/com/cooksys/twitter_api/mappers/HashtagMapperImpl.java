package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.entities.Hashtag;
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
public class HashtagMapperImpl implements HashtagMapper {

    @Override
    public HashtagDto entityToDto(Hashtag entity) {
        if ( entity == null ) {
            return null;
        }

        HashtagDto hashtagDto = new HashtagDto();

        hashtagDto.setLabel( entity.getLabel() );
        hashtagDto.setFirstUsed( entity.getFirstUsed() );
        hashtagDto.setLastUsed( entity.getLastUsed() );

        return hashtagDto;
    }

    @Override
    public List<HashtagDto> entitiesToDtos(List<Hashtag> entities) {
        if ( entities == null ) {
            return null;
        }

        List<HashtagDto> list = new ArrayList<HashtagDto>( entities.size() );
        for ( Hashtag hashtag : entities ) {
            list.add( entityToDto( hashtag ) );
        }

        return list;
    }
}
