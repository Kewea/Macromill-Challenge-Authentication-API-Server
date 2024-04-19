package com.macromill.user.mapper;


import com.macromill.user.Entity.User;
import com.macromill.user.dto.UserRequestDto;
import com.macromill.user.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "userId", source = "username")
    UserResponseDto.UserDto toUserResponseDto(User user);

    @Mapping(target = "username", source = "userId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nickname", source = "nickname", defaultExpression = "java(mapNickName(userRequestDto))")
    @Mapping(target = "comment", source = "comment", defaultExpression = "java(mapComment(userRequestDto))")
    User toUser(UserRequestDto userRequestDto);

    default String mapNickName(UserRequestDto userRequestDto) {
        return userRequestDto.getNickname() == null ? userRequestDto.getUserId() : userRequestDto.getNickname();
    }

    default String mapComment(UserRequestDto userRequestDto) {
        return userRequestDto.getComment() == null ? "I'm happy." : userRequestDto.getComment();
    }
}
