package com.macromill.user.service;

import com.macromill.user.Entity.User;
import com.macromill.user.Utils;
import com.macromill.user.constant.Message;
import com.macromill.user.dto.UserRequestDto;
import com.macromill.user.dto.UserResponseDto;
import com.macromill.user.exception.*;
import com.macromill.user.mapper.UserMapper;
import com.macromill.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Create new user
     *
     * @param userRequestDto
     * @return
     */
    public UserResponseDto postUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUserId())) {
            log.error("User exist already. Failed to save new user.");
            throw new DuplicateUserException(userRequestDto.getUserId());
        }

        User user = userRepository.save(userMapper.toUser(userRequestDto));
        UserResponseDto.UserDto userDto = userMapper.toUserResponseDto(user);
        log.info("User has been saved successfully");
        return UserResponseDto.builder().message(Message.SUCCESS_MESSAGE_CREATE_USER).user(userDto).build();
    }

    /**
     * Get user info
     *
     * @param userName
     * @param credentialsPair
     * @return
     */
    public UserResponseDto getUser(String userName, ImmutablePair<String, String> credentialsPair) {
        User user = userRepository.findByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            log.error("User not found");
            throw new UserNotFoundException();
        }

        if (!Utils.isAuthorized(user, credentialsPair)) {
            log.error("user_id or password does not match Authorization header");
            throw new PermissionException(Message.FAIL_MESSAGE_USER_NO_PERMISSION.replace("ACTION", "getting info"));
        }

        UserResponseDto.UserDto userDto = userMapper.toUserResponseDto(user);
        log.info("Successfully get user");
        return UserResponseDto.builder()
                .message(Message.SUCCESS_GET_USER + userName)
                .user(userDto)
                .build();
    }

    /**
     * Update user info
     *
     * @param userName
     * @param userRequestDto
     * @param credentialsPair
     * @return
     */
    public UserResponseDto patchUser(String userName, UserRequestDto userRequestDto, ImmutablePair<String, String> credentialsPair) {
        if (!isPatchUseridOrPassword(userRequestDto)) {
            log.error("User tries to update user_id or password");
            throw new BadRequestException(Message.FAIL_MESSAGE_USER_UPDATE, Message.FAIL_CAUSE_USER_UPDATE);
        }

        User user = userRepository.findByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            log.error("User not found");
            throw new UserNotFoundException();
        }

        if (!Utils.isAuthorized(user, credentialsPair)) {
            log.error("user_id or password does not match Authorization header");
            throw new PermissionException(Message.FAIL_MESSAGE_USER_NO_PERMISSION.replace("ACTION", "update"));
        }

        user.setComment(Optional.ofNullable(userRequestDto.getComment()).isPresent() ? userRequestDto.getComment() : user.getComment());
        user.setNickname(Optional.ofNullable(userRequestDto.getNickname()).isPresent() ? userRequestDto.getNickname() : user.getNickname());

        // Save user
        userRepository.save(user);

        // Prepare response
        UserResponseDto.UserDto userDto = userMapper.toUserResponseDto(user);
        log.info("User has been updated successfully");
        return UserResponseDto.builder()
                .message(Message.SUCCESS_MESSAGE_UPDATE_USER)
                .recipe(Collections.singletonList(userDto))
                .build();
    }

    public UserResponseDto deleteUser(ImmutablePair<String, String> credentialsPair) {
        User user = userRepository.findByUsername(credentialsPair.getLeft());
        if (ObjectUtils.isEmpty(user)) {
            log.error("User not found");
            throw new UserNotFoundException();
        }

        if (!Utils.isAuthorized(user, credentialsPair)) {
            log.error("user_id or password does not match Authorization header");
            throw new AuthorizationException();
        }

        userRepository.deleteById(user.getId());
        return UserResponseDto.builder().message(Message.SUCCESS_MESSAGE_DELETE_USER).build();
    }

    private boolean isPatchUseridOrPassword(UserRequestDto userRequestDto) {
        return StringUtils.isEmpty(userRequestDto.getUserId()) && StringUtils.isEmpty(userRequestDto.getPassword());
    }
}
