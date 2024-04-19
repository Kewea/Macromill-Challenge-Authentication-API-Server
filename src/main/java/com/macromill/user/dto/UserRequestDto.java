package com.macromill.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.macromill.user.validation.CreateUser;
import com.macromill.user.validation.UpdateUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotNull(groups = CreateUser.class)
    @Size(min = 6, max = 20, message = "Invalid length for user_id", groups = CreateUser.class)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid format for user_id", groups = CreateUser.class)
    @JsonProperty("user_id")
    String userId;

    @NotNull(groups = CreateUser.class)
    @Size(min = 8, max = 20, message = "Invalid length for password", groups = CreateUser.class)
    @Pattern(regexp = "^[!-~]*$", message = "Invalid format for password", groups = CreateUser.class)
    String password;

    @Size(max = 30, message = "Invalid length for nickname", groups = UpdateUser.class)
    @Pattern(regexp = "^[\\s\\S]*$", message = "Invalid format for nickname", groups = UpdateUser.class)
    String nickname;

    @Size(max = 100, message = "Invalid length for comment", groups = UpdateUser.class)
    @Pattern(regexp = "^[\\s\\S]*$", message = "Invalid format for comment", groups = UpdateUser.class)
    String comment;
}
