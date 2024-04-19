package com.macromill.user.controller;

import com.macromill.user.dto.ErrorMessageDto;
import com.macromill.user.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ErrorMessageDto.builder().cause(String.join(", ", errorList)).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateUserException.class, BadRequestException.class})
    public ResponseEntity<ErrorMessageDto> handleBadRequestException(BaseException ex) {
        return new ResponseEntity<>(ex.getErrorMessageDto(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<ErrorMessageDto> handleAuthorizationException(BaseException ex) {
        return new ResponseEntity<>(ex.getErrorMessageDto(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({PermissionException.class})
    public ResponseEntity<ErrorMessageDto> handlePermissionException(BaseException ex) {
        return new ResponseEntity<>(ex.getErrorMessageDto(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessageDto> handleNotFoundException(BaseException ex) {
        return new ResponseEntity<>(ex.getErrorMessageDto(), HttpStatus.NOT_FOUND);
    }
}
