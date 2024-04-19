package com.macromill.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String cause;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;
}
