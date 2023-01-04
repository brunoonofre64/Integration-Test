package io.brunoonofre64.domain.exception;

import io.brunoonofre64.domain.enums.CodeMessage;

public class LoginNotFoundException extends RuntimeException {

    final CodeMessage message;

    public LoginNotFoundException(CodeMessage message) {
        this.message = message;
    }
}
