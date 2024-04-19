package com.macromill.user;

import com.macromill.user.Entity.User;
import com.macromill.user.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@UtilityClass
public class Utils {
    public ImmutablePair<String, String> getAuthorizationToken(HttpServletRequest request) {
        String base64Token = Optional.ofNullable(request.getHeader("Authorization")).orElseThrow(AuthorizationException::new);

        if (StringUtils.hasLength(base64Token) && base64Token.startsWith("Basic ")) {
            String token = base64Token.substring(6);
            byte[] decodedBytes = Base64.decodeBase64(token);
            String[] credential = new String(decodedBytes).split(":");
            return new ImmutablePair<String, String>(credential[0], credential[1]);
        } else {
            throw new AuthorizationException();
        }
    }

    public boolean isAuthorized(User user, ImmutablePair<String, String> credentialsPair) {
        return credentialsPair.getLeft().equals(user.getUsername()) && credentialsPair.getRight().equals(user.getPassword());
    }
}
