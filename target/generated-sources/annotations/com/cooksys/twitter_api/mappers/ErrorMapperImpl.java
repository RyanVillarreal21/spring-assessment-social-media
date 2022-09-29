package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.ErrorDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-05T14:35:20-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class ErrorMapperImpl implements ErrorMapper {

    @Override
    public ErrorDto entityToDto(Error entity) {
        if ( entity == null ) {
            return null;
        }

        ErrorDto errorDto = new ErrorDto();

        errorDto.setMessage( entity.getMessage() );

        return errorDto;
    }
}
