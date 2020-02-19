package io.yorosoft.ebanking.model.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authority
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authString) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}