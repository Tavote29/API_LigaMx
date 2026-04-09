package com.fdf.liga_mx.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUserDetails extends User {


    private static final long serialVersionUID = -5335551184248832877L;

    private Long userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
                             Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }



}
