package net.javaguides.springboot.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LockedException extends AuthenticationException {
    public LockedException(String mes) {
        super(mes);
    }
}
