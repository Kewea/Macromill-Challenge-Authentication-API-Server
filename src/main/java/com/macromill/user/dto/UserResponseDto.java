package com.macromill.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserDto user;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<UserDto> recipe;

    @Data
    public static class UserDto {
        @JsonProperty("user_id")
        String userId;
        String nickname;
        String comment;
    }
}
