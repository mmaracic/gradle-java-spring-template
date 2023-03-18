package hr.mmaracic.website.mapper;

import hr.mmaracic.api.model.UserDto;
import hr.mmaracic.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    UserDto mapToDto(User user);
}
