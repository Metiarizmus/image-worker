package net.javaguides.springboot.exceptions;

public class UsernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
