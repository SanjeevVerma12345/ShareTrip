package de.sharetrip.user.mapper;

import de.sharetrip.user.domain.User;
import de.sharetrip.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto map(User user);
}
